package com.knifez.fridayboot.service;

import com.knifez.fridayboot.entity.AppRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
public interface IAppRoleService extends IService<AppRole> {


    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    List<AppRole> listByUserId(long userId);
}
