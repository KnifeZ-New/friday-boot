package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.dto.AppUserModifyDTO;
import org.knifez.fridaybootadmin.dto.AppUserPagedQueryRequest;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootadmin.mapper.AppUserMapper;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.knifez.fridaybootadmin.service.IAppRoleService;
import org.knifez.fridaybootadmin.service.IAppUserRoleService;
import org.knifez.fridaybootadmin.service.IAppUserService;
import org.knifez.fridaybootcore.dto.PageResult;
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

    private final IAppPermissionGrantService permissionService;

    private final IAppUserRoleService userRoleService;

    public AppUserServiceImpl(IAppRoleService roleService, IAppPermissionGrantService permissionService, IAppUserRoleService userRoleService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.userRoleService = userRoleService;
    }

    /**
     * 分页列表查询
     *
     * @param queryRequest 查询请求
     * @return {@link PageResult}<{@link AppUser}>
     */
    @Override
    public PageResult<AppUser> listByPageQuery(AppUserPagedQueryRequest queryRequest) {
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
        return PageResult.builder(page.getRecords(), page.getTotal());
    }

    @Override
    public AppUserDTO findByAccount(String account) {
        AppUserDTO userDTO = new AppUserDTO();
        var wrapper = new QueryWrapper<AppUser>();
        wrapper.eq("account", account);
        var user = baseMapper.selectOne(wrapper);
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
            var roles = roleService.listRoleNameByUserId(user.getId());
            if (!roles.isEmpty()) {
                var permissions = permissionService.listByRoles(roles);
                userDTO.setPermissions(permissions);
            }
            userDTO.setUserRoles(roles);
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
        }
        result = saveOrUpdate(user);
        if (result) {
            result = userRoleService.saveRolesByUserId(user.getId(), user.getRoles());
        }
        return result;
    }
}
