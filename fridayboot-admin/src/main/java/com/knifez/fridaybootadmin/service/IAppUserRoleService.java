package com.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.knifez.fridaybootadmin.entity.AppUserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-07
 */
public interface IAppUserRoleService extends IService<AppUserRole> {
    /**
     * 获取角色id列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> listRolesByUserId(Long userId);

}
