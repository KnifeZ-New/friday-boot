package com.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knifez.fridaybootadmin.dto.AppUserPagedQueryRequest;
import com.knifez.fridaybootadmin.entity.AppUser;
import com.knifez.fridaybootadmin.mapper.AppUserMapper;
import com.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import com.knifez.fridaybootadmin.service.IAppRoleService;
import com.knifez.fridaybootadmin.service.IAppUserRoleService;
import com.knifez.fridaybootadmin.service.IAppUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knifez.fridaybootcore.dto.PageResult;
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
        queryWrapper.eq(queryRequest.getOrganizationId() != null, "organization_id", queryRequest.getOrganizationId());
        IPage<AppUser> page = new Page<>();
        page.setCurrent(queryRequest.getPage());
        page.setSize(queryRequest.getPageSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PageResult.builder(page.getRecords(), page.getTotal());
    }

    @Override
    public AppUser findByAccount(String account) {
        var wrapper = new QueryWrapper<AppUser>();
        wrapper.eq("account", account);
        var user = baseMapper.selectOne(wrapper);
        if (user == null) {
            user = new AppUser();
        } else {
            var roles = roleService.listByUserId(user.getId());
            if (!roles.isEmpty()) {
                var permissions = permissionService.listByRoles(roles);
                user.setPermissions(permissions);
            }
            user.setUserRoles(roles);
        }
        return user;
    }

    @Override
    public Boolean saveWithUserRoles(AppUser user, boolean isUpdate) {
        var result = false;
        if (StringUtils.hasText(user.getPassword())) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        if (isUpdate) {
            result = updateById(user);
        } else {
            result = save(user);
        }
        if (result) {
            result = userRoleService.saveRolesByUserId(user.getId(), user.getRoles());
        }
        return result;
    }
}
