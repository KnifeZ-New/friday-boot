package com.knifez.fridayboot.service;

import com.knifez.fridayboot.entity.AppUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
public interface IAppUserService extends IService<AppUser> {

    /**
     *  根据账户获取用户
     * @param account 账户
     * @return User
     */
    AppUser getByAccount(String account);

    /**
     * 查找用户权限
     * @param username 账户
     * @return 权限列表
     */
    Set<String> findPermissions(String username);
}
