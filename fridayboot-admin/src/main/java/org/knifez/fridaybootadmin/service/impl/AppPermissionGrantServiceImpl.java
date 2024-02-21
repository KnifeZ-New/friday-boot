package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppPermissionDTO;
import org.knifez.fridaybootadmin.entity.AppMenu;
import org.knifez.fridaybootadmin.entity.AppPermissionGrant;
import org.knifez.fridaybootadmin.mapper.AppPermissionGrantMapper;
import org.knifez.fridaybootadmin.service.IAppMenuService;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private final IAppMenuService menuService;

    public AppPermissionGrantServiceImpl(IAppMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 按角色获取菜单列表
     *
     * @param roleName 角色名
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> getSelectMenusByRoleName(String roleName) {
        var queryWrapper = new LambdaQueryWrapper<AppPermissionGrant>();
        queryWrapper.eq(AppPermissionGrant::getProvideFor, roleName);
        queryWrapper.eq(AppPermissionGrant::getProvideName, "ROLE");
        var list = baseMapper.selectList(queryWrapper);
        return list.stream().map(AppPermissionGrant::getName).distinct().toList();
    }

    @Override
    public List<AppMenu> getUserMenuByRoleNames(List<String> roleNames) {
        List<AppMenu> result = new ArrayList<>();
        if (roleNames.isEmpty()) {
            return result;
        }
        var queryWrapper = new LambdaQueryWrapper<AppPermissionGrant>();
        queryWrapper.in(AppPermissionGrant::getProvideFor, roleNames);
        queryWrapper.eq(AppPermissionGrant::getProvideName, "ROLE");
        var list = baseMapper.selectList(queryWrapper);
        var menuIds = list.stream().map(AppPermissionGrant::getName).mapToInt(Integer::parseInt).distinct().toArray();
        List<Integer> ids = new ArrayList<>();
        for (var id : menuIds) {
            ids.add(id);
        }
        result = menuService.getMenuByIds(ids);
        return result;
    }

    @Override
    public void saveByRole(List<Integer> permissions, String roleName) {
        var queryWrapper = new LambdaQueryWrapper<AppPermissionGrant>();
        queryWrapper.eq(AppPermissionGrant::getProvideFor, roleName);
        queryWrapper.eq(AppPermissionGrant::getProvideName, "ROLE");
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
    public AppPermissionDTO listByRoles(List<String> roleNames) {
        var result = new AppPermissionDTO();
        if (roleNames.isEmpty()) {
            return result;
        }
        var queryWrapper = new LambdaQueryWrapper<AppPermissionGrant>();
        queryWrapper.in(AppPermissionGrant::getProvideFor, roleNames);
        queryWrapper.eq(AppPermissionGrant::getProvideName, "ROLE");
        var list = baseMapper.selectList(queryWrapper);
        var ids = list.stream().map(AppPermissionGrant::getName).distinct().toList();
        var permissions = menuService.getMenuPermissions(ids);
        result.setApiPermissions(permissions.stream().filter(x -> Objects.equals(x.getText(), "API")).map(x -> x.getValue().toString()).distinct().toList());
        result.setWebPermissions(permissions.stream().filter(x -> Objects.equals(x.getText(), "WEB")).map(x -> x.getValue().toString()).distinct().toList());
        return result;
    }
}
