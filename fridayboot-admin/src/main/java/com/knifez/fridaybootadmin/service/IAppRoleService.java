package com.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.knifez.fridaybootadmin.dto.AppRolePagedQueryRequest;
import com.knifez.fridaybootadmin.entity.AppRole;
import com.knifez.fridaybootcore.dto.PageResult;

import java.util.List;

/**
 * <p>
 * 服务类
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
    List<String> listByUserId(long userId);

    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link List}<{@link AppRole}>
     */
    PageResult<AppRole> listByPageQuery(AppRolePagedQueryRequest queryRequest);
}
