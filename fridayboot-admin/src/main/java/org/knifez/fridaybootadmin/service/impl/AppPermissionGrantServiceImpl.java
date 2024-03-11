package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppMenuDTO;
import org.knifez.fridaybootadmin.dto.AppPermissionDTO;
import org.knifez.fridaybootadmin.entity.AppPermissionGrant;
import org.knifez.fridaybootadmin.mapper.AppPermissionGrantMapper;
import org.knifez.fridaybootadmin.service.IAppMenuService;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.springframework.stereotype.Service;

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
        var list = baseMapper.selectList(queryWrapper);
        var permissions = list.stream().filter(x -> Objects.equals(x.getProvideName(), "ROLE")).map(AppPermissionGrant::getName).distinct().toList();
        result.setApiPermissions(permissions);
        var dataPermissions = list.stream().filter(x -> x.getProvideName().startsWith("DATA_")).map(AppPermissionGrant::getName).distinct().toList();
        result.setDataPermissions(dataPermissions.stream().map(Integer::parseInt).toList());
        return result;
    }

    @Override
    public List<AppMenuDTO> getUserMenuByPermissions(List<String> permissions, Boolean isSuper) {
        var menus = menuService.getMenuByPermissions(permissions, isSuper);
        return menus.stream().filter(x -> x.getType() < 2).toList();
    }
}
