package com.knifez.fridaybootadmin.service;

import com.knifez.fridaybootadmin.dto.AppOrganizationUnitQueryRequest;
import com.knifez.fridaybootadmin.entity.AppOrganizationUnit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.knifez.fridaybootcore.dto.PageResult;

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
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link PageResult}<{@link AppOrganizationUnit}>
     */
    PageResult<AppOrganizationUnit> listByPageQuery(AppOrganizationUnitQueryRequest queryRequest);
}
