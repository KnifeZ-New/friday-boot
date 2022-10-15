package com.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridaybootadmin.entity.AppPermissionGrant;
import com.knifez.fridaybootadmin.mapper.AppPermissionGrantMapper;
import com.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 授权记录 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-23
 */
@Service
public class AppPermissionGrantServiceImpl extends ServiceImpl<AppPermissionGrantMapper, AppPermissionGrant> implements IAppPermissionGrantService {

    /**
     * 按角色获取权限列表
     *
     * @param roleName 角色名
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> listByRole(String roleName) {
        var queryWrapper = new QueryWrapper<AppPermissionGrant>();
        queryWrapper.eq("provide_for", roleName);
        queryWrapper.eq("provide_name", "ROLE");
        var list = baseMapper.selectList(queryWrapper);
        return list.stream().map(AppPermissionGrant::getName).toList();
    }

    @Override
    public void saveByRole(List<Integer> permissions, String roleName) {
        var queryWrapper = new QueryWrapper<AppPermissionGrant>();
        queryWrapper.eq("provide_for", roleName);
        queryWrapper.eq("provide_name", "ROLE");
        baseMapper.delete(queryWrapper);
        List<AppPermissionGrant> permissionGrants = new ArrayList<>();
        for (var menu : permissions) {
            var permission = new AppPermissionGrant();
            permission.setName(menu.toString());
            permission.setProvideName("ROLE");
            permission.setProvideFor(roleName);
            permissionGrants.add(permission);
        }
        this.saveBatch(permissionGrants);
    }

    /**
     * 按角色获取权限列表
     *
     * @param roleNames 角色名
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> listByRoles(List<String> roleNames) {
        var queryWrapper = new QueryWrapper<AppPermissionGrant>();
        queryWrapper.in("provide_for", roleNames);
        queryWrapper.eq("provide_name", "ROLE");
        var list = baseMapper.selectList(queryWrapper);
        return list.stream().map(AppPermissionGrant::getName).distinct().toList();
    }
}
