package com.knifez.fridayboot.service;

import com.knifez.fridayboot.entity.AppUser;
import com.baomidou.mybatisplus.extension.service.IService;

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
    AppUser findByAccount(String account);
}
