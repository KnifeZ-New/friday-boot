package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppRolePagedRequest;
import org.knifez.fridaybootadmin.entity.AppPermissionGrant;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootadmin.mapper.AppRoleMapper;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.knifez.fridaybootadmin.service.IAppRoleService;
import org.knifez.fridaybootadmin.service.IAppUserRoleService;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
@Service
public class AppRoleServiceImpl extends ServiceImpl<AppRoleMapper, AppRole> implements IAppRoleService {

    private final IAppUserRoleService userRoleService;

    private final IAppPermissionGrantService permissionGrantService;

    public AppRoleServiceImpl(IAppUserRoleService userRoleService, IAppPermissionGrantService permissionGrantService) {
        this.userRoleService = userRoleService;
        this.permissionGrantService = permissionGrantService;
    }

    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    @Override
    public List<String> listRoleNameByUserId(Integer userId) {
        var ids = userRoleService.listRolesByUserId(userId);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return baseMapper.selectBatchIds(ids).stream().map(AppRole::getName).toList();
    }

    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    @Override
    public List<AppRole> listByUserId(Integer userId) {
        var ids = userRoleService.listRolesByUserId(userId);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return baseMapper.selectBatchIds(ids);
    }

    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link List}<{@link AppRole}>
     */
    @Override
    public PagedResult<AppRole> listByPageQuery(AppRolePagedRequest queryRequest) {
        QueryWrapper<AppRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(queryRequest.getDisplayName()), "display_name", queryRequest.getDisplayName());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        queryWrapper.eq(queryRequest.getEnabled() != null, "is_enabled", queryRequest.getEnabled());
        IPage<AppRole> page = new Page<>();
        page.setCurrent(queryRequest.getCurrent());
        page.setSize(queryRequest.getSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PagedResult.builder(page.getRecords(), page.getTotal());
    }

    @Override
    public void savePermissionsByRole(List<String> permissions, String roleName) {
        var queryWrapper = new LambdaQueryWrapper<AppPermissionGrant>();
        queryWrapper.eq(AppPermissionGrant::getProvideFor, roleName);
        queryWrapper.eq(AppPermissionGrant::getProvideName, "ROLE");
        permissionGrantService.remove(queryWrapper);
        List<AppPermissionGrant> permissionGrants = new ArrayList<>();
        for (var permission : permissions) {
            var permissionGrant = new AppPermissionGrant();
            permissionGrant.setName(permission);
            permissionGrant.setProvideName("ROLE");
            permissionGrant.setProvideFor(roleName);
            permissionGrants.add(permissionGrant);
        }
        permissionGrantService.saveBatch(permissionGrants);
    }
}
