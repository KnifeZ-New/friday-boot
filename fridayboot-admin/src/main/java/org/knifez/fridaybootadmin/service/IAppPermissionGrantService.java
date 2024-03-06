package org.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppMenuDTO;
import org.knifez.fridaybootadmin.dto.AppPermissionDTO;
import org.knifez.fridaybootadmin.entity.AppPermissionGrant;

import java.util.List;

/**
 * <p>
 * 授权记录 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-23
 */
public interface IAppPermissionGrantService extends IService<AppPermissionGrant> {

    /**
     * 按角色获取菜单列表
     *
     * @param roleName 角色名
     * @return {@link List}<{@link String}>
     */
    List<String> getSelectMenusByRoleName(String roleName);

    /**
     * 按角色获取权限列表
     *
     * @param roleNames 角色名
     * @return {@link List}<{@link String}>
     */
    AppPermissionDTO listByRoles(List<String> roleNames);

    /**
     * 按权限获取用户菜单
     *
     * @param permissions 角色名称
     * @return {@link List}<{@link AppMenuDTO}>
     */
    List<AppMenuDTO> getUserMenuByPermissions(List<String> permissions, Boolean isSuper);
}
