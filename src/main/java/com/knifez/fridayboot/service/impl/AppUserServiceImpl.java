package com.knifez.fridayboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridayboot.entity.AppUser;
import com.knifez.fridayboot.mapper.AppUserMapper;
import com.knifez.fridayboot.service.IAppRoleService;
import com.knifez.fridayboot.service.IAppUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
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
    public AppUserServiceImpl(IAppRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public AppUser findByAccount(String account) {
        var wrapper = new QueryWrapper<AppUser>();
        wrapper.eq("Account", account);
        var user = baseMapper.selectOne(wrapper);
        if (user == null) {
            user = new AppUser();
        } else {
            var roles = roleService.listByUserId(user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
