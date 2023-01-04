package org.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 根据角色保存权限
     *
     * @param permissions 菜单权限id集合
     * @param roleName    角色
     */
    void saveByRole(List<Integer> permissions, String roleName);

    /**
     * 按角色获取权限列表
     *
     * @param roleNames 角色名
     * @return {@link List}<{@link String}>
     */
    List<String> listByRoles(List<String> roleNames);
}
