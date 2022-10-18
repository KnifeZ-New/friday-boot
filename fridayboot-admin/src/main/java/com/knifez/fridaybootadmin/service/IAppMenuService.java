package com.knifez.fridaybootadmin.service;

import com.knifez.fridaybootadmin.entity.AppMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-11
 */
public interface IAppMenuService extends IService<AppMenu> {
    /**
     * 获取菜单权限
     *
     * @param ids 菜单id集合
     * @return 菜单权限集合
     */
    List<String> getMenuPermissions(List<String> ids);
}
