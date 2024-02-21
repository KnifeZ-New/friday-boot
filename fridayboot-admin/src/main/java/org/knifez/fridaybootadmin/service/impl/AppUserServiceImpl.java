package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.dto.AppUserInfoDTO;
import org.knifez.fridaybootadmin.dto.AppUserModifyDTO;
import org.knifez.fridaybootadmin.dto.AppUserPagedRequest;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootadmin.mapper.AppUserMapper;
import org.knifez.fridaybootadmin.service.*;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */

@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

    private final IAppRoleService roleService;

    private final IAppOrganizationUnitService organizationUnitService;

    private final IAppPermissionGrantService permissionService;

    private final IAppUserRoleService userRoleService;

    public AppUserServiceImpl(IAppRoleService roleService, IAppOrganizationUnitService organizationUnitService, IAppPermissionGrantService permissionService, IAppUserRoleService userRoleService) {
        this.roleService = roleService;
        this.organizationUnitService = organizationUnitService;
        this.permissionService = permissionService;
        this.userRoleService = userRoleService;
    }

    /**
     * 分页列表查询
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppUser}>
     */
    @Override
    public PagedResult<AppUser> listByPageQuery(AppUserPagedRequest queryRequest) {
        QueryWrapper<AppUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(queryRequest.getLocked() != null, "is_locked", queryRequest.getLocked());
        queryWrapper.like(queryRequest.getUsername() != null, "username", queryRequest.getUsername());
        //获取所有子节点
        if (queryRequest.getOrganizationIds() != null && !queryRequest.getOrganizationIds().isEmpty()) {
            queryWrapper.in("organization_id", queryRequest.getOrganizationIds());
        }
        IPage<AppUser> page = new Page<>();
        page.setCurrent(queryRequest.getPage());
        page.setSize(queryRequest.getPageSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PagedResult.builder(page.getRecords(), page.getTotal());
    }


    @Override
    public AppUserInfoDTO findByAccount(String account) {
        AppUserInfoDTO userDTO = new AppUserInfoDTO();
        var user = getUserDtoByAccountOrId(account, 0);
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
            var roles = user.getRoles().stream().map(AppRole::getName).toList();
            if (!roles.isEmpty()) {
                var permission = permissionService.listByRoles(roles);
                userDTO.setPermissions(permission.getWebPermissions());
                userDTO.setApiPermissions(permission.getApiPermissions());
            }
            userDTO.setUserRoles(roles);
        }
        return userDTO;
    }

    @Override
    public AppUserDTO getUserDtoByAccountOrId(String account, long userId) {
        AppUserDTO userDTO = new AppUserDTO();
        var wrapper = new QueryWrapper<AppUser>();
        wrapper.eq(StringUtils.hasText(account), "account", account);
        wrapper.eq(userId > 0, "id", userId);
        var user = baseMapper.selectOne(wrapper);
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
            var roles = roleService.listByUserId(user.getId());
            userDTO.setRoles(roles);
            if (user.getOrganizationId() != null && user.getOrganizationId() > 0) {
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
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        if (isNew) {
            user.setId(null);
            result = save(user);
        } else {
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
    public Boolean checkOriginPassword(Long id, String password) {
        var user = getById(id);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    /**
     * 更新密码
     *
     * @param id       id
     * @param password 密码
     * @return {@link Boolean}
     */
    @Override
    public Boolean updatePassword(Long id, String password) {
        var user = getById(id);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pass = bCryptPasswordEncoder.encode(password);
        user.setPassword(pass);
        return updateById(user);
    }
}
