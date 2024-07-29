package org.knifez.fridaybootadmin.service.impl;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.common.constants.SecurityConst;
import org.knifez.fridaybootadmin.dto.*;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootadmin.entity.AppUserRole;
import org.knifez.fridaybootadmin.mapper.AppUserMapper;
import org.knifez.fridaybootadmin.service.*;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.knifez.fridaybootcore.utils.AnnotationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
@Slf4j
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

    private final IAppRoleService roleService;
    private final IAppOrganizationUnitService organizationUnitService;

    private final IAppPermissionGrantService permissionService;

    private final IAppUserRoleService userRoleService;

    private final ResourcePatternResolver resourcePatternResolver;

    public AppUserServiceImpl(IAppRoleService roleService, IAppOrganizationUnitService organizationUnitService, IAppPermissionGrantService permissionService, IAppUserRoleService userRoleService, ResourcePatternResolver resourcePatternResolver) {
        this.roleService = roleService;
        this.organizationUnitService = organizationUnitService;
        this.permissionService = permissionService;
        this.userRoleService = userRoleService;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * 分页列表查询
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppUser}>
     */
    @Override
    public PagedResult<AppUser> listByPageQuery(AppUserPagedRequest queryRequest) {
        LambdaQueryWrapper<AppUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(queryRequest.getLocked() != null, AppUser::getLocked, queryRequest.getLocked());
        queryWrapper.like(queryRequest.getUsername() != null, AppUser::getUsername, queryRequest.getUsername());
        var dp = JwtTokenUtils.getDataPermission(queryRequest.getOrganizationIds());
        queryWrapper.in(!dp.isEmpty(), AppUser::getOrganizationId, dp);
        IPage<AppUser> page = new Page<>();
        page.setCurrent(queryRequest.getCurrent());
        page.setSize(queryRequest.getSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PagedResult.builder(page.getRecords(), page.getTotal());
    }


    @Override
    public AppUserInfoDTO findByAccount(String account) {
        AppUserInfoDTO userDTO = new AppUserInfoDTO();
        var user = getUserDtoByAccountOrId(account, 0);
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
            var roles = user.getRoles().stream().map(SelectedRoleItem::getName).toList();
            if (!roles.isEmpty()) {
                userDTO.setUserRoles(roles);
                var permission = permissionService.listByRoles(roles);
                userDTO.setPermissions(permission.getApiPermissions());
                userDTO.setDataPermissions(permission.getDataPermissions());
                if (roles.contains(SecurityConst.ROLE_SUPER_ADMIN)) {
                    try {
                        var authorities = AnnotationUtils.getAuthorityList(resourcePatternResolver,
                                ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "**/controller/**/**.class",
                                SaCheckPermission.class);
                        userDTO.setPermissions(authorities.stream().map(x -> x.getValue().toString()).toList());
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                }
            }
        }
        return userDTO;
    }

    @Override
    public AppUserDTO getUserDtoByAccountOrId(String account, long userId) {
        AppUserDTO userDTO = new AppUserDTO();
        if (userId == 0 && !StringUtils.hasText(account)) {
            return null;
        }
        LambdaQueryWrapper<AppUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(account), AppUser::getAccount, account);
        wrapper.eq(userId > 0, AppUser::getId, userId);
        wrapper.eq(AppUser::getLocked, false);
        var user = baseMapper.selectOne(wrapper, false);
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
            var roles = roleService.listByUserId(user.getId());
            userDTO.setRoles(roles);
            if (user.getOrganizationId() != null && user.getOrganizationId() != 0) {
                userDTO.setOrganizationName(organizationUnitService.getById(userDTO.getOrganizationId()).getName());
            }
        }
        return userDTO;
    }

    /**
     * 账户是否存在
     *
     * @param account 账户
     * @return {@link Boolean}
     */
    @Override
    public Boolean accountExist(String account) {
        var wrapper = new QueryWrapper<AppUser>();
        wrapper.eq("account", account);
        var accountCount = baseMapper.selectCount(wrapper);
        return accountCount > 0;
    }

    @Override
    public Boolean saveWithUserRoles(AppUserModifyDTO user, boolean isNew) {
        var result = false;
        if (StringUtils.hasText(user.getPassword())) {
            String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(password);
        }
        if (isNew) {
            user.setId(null);
            result = save(user);
        } else {
            if (!StringUtils.hasText(user.getPassword())) {
                var model = getById(user.getId());
                user.setPassword(model.getPassword());
            }
            result = updateById(user);
        }
        if (result) {
            result = userRoleService.saveRolesByUserId(user.getId(), user.getRoles());
        }
        return result;
    }

    /**
     * 检查原始密码
     *
     * @param id       id
     * @param password 密码
     * @return {@link Boolean}
     */
    @Override
    public Boolean checkOriginPassword(Integer id, String password) {
        var user = getById(id);
        return BCrypt.checkpw(password, user.getPassword());
    }

    /**
     * 更新密码
     *
     * @param id       id
     * @param password 密码
     * @return {@link Boolean}
     */
    @Override
    public Boolean updatePassword(Integer id, String password) {
        var user = getById(id);
        String pass = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(pass);
        return updateById(user);
    }

    @Override
    public Integer getUserOrgId(String account) {
        LambdaQueryWrapper<AppUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(account), AppUser::getAccount, account);
        wrapper.eq(AppUser::isDeleted, false);
        var user = baseMapper.selectOne(wrapper, false);
        if (user != null && user.getOrganizationId() != null) {
            return Math.toIntExact(user.getOrganizationId());
        }
        return 0;
    }
}
