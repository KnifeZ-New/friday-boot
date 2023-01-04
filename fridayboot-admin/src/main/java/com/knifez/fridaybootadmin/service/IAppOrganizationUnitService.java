package com.knifez.fridaybootadmin.service;

import com.knifez.fridaybootadmin.dto.AppOrganizationUnitQueryRequest;
import com.knifez.fridaybootadmin.entity.AppOrganizationUnit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-09-01
 */
public interface IAppOrganizationUnitService extends IService<AppOrganizationUnit> {

    /**
     * 组织架构列表（包含查询结果的父节点）
     *
     * @param queryRequest 查询请求
     * @return {@link List}<{@link AppOrganizationUnit}>
     */
    List<AppOrganizationUnit> listWithParentNodes(AppOrganizationUnitQueryRequest queryRequest);
}
