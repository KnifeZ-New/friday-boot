package com.knifez.fridaybootadmin.service;

import com.knifez.fridaybootadmin.entity.AppPermissionGrant;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 按角色获取权限列表
     *
     * @param roleName 角色名
     * @return {@link List}<{@link String}>
     */
    List<String> listByRole(String roleName);

    /**
     * 按角色获取权限列表
     *
     * @param roleNames 角色名
     * @return {@link List}<{@link String}>
     */
    List<String> listByRoles(List<String> roleNames);
}
