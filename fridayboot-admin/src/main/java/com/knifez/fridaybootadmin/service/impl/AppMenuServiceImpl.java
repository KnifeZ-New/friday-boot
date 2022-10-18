package com.knifez.fridaybootadmin.service.impl;

import com.knifez.fridaybootadmin.entity.AppMenu;
import com.knifez.fridaybootadmin.mapper.AppMenuMapper;
import com.knifez.fridaybootadmin.service.IAppMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-11
 */
@Service
public class AppMenuServiceImpl extends ServiceImpl<AppMenuMapper, AppMenu> implements IAppMenuService {
    /**
     * 获取菜单权限
     *
     * @param ids 菜单id集合
     * @return 菜单权限集合
     */
    @Override
    public List<String> getMenuPermissions(List<String> ids) {
        return listByIds(ids).stream().map(AppMenu::getPermission).filter(Objects::nonNull).toList();
    }
}
