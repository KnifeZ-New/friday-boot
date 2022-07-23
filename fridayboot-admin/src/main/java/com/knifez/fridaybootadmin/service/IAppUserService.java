package com.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.knifez.fridaybootadmin.dto.AppUserPagedQueryRequest;
import com.knifez.fridaybootadmin.entity.AppUser;
import com.knifez.fridaybootcore.dto.PageResult;

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
     * 分页列表查询
     *
     * @param queryRequest 查询请求
     * @return {@link PageResult}<{@link AppUser}>
     */
    PageResult<AppUser> listByPageQuery(AppUserPagedQueryRequest queryRequest);

    /**
     * 根据账户获取用户
     *
     * @param account 账户
     * @return User
     */
    AppUser findByAccount(String account);
}
