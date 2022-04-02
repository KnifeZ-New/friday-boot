package com.knifez.fridayboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridayboot.entity.AppUser;
import com.knifez.fridayboot.mapper.AppUserMapper;
import com.knifez.fridayboot.service.IAppUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

    @Override
    public AppUser getByAccount(String account) {
        var wrapper=new QueryWrapper<AppUser>();
        wrapper.eq("Account",account);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 查找用户权限
     *
     * @param username 账户
     * @return 权限列表
     */
    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        // todo findPermission
        permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");
        return permissions;
    }
}
