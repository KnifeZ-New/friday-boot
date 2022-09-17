package com.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knifez.fridaybootadmin.dto.AppOrganizationUnitQueryRequest;
import com.knifez.fridaybootadmin.entity.AppOrganizationUnit;
import com.knifez.fridaybootadmin.mapper.AppOrganizationUnitMapper;
import com.knifez.fridaybootadmin.service.IAppOrganizationUnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knifez.fridaybootcore.dto.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-09-01
 */
@Service
public class AppOrganizationUnitServiceImpl extends ServiceImpl<AppOrganizationUnitMapper, AppOrganizationUnit> implements IAppOrganizationUnitService {

    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link PageResult}<{@link AppOrganizationUnit}>
     */
    @Override
    public PageResult<AppOrganizationUnit> listByPageQuery(AppOrganizationUnitQueryRequest queryRequest) {
        QueryWrapper<AppOrganizationUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(queryRequest.getUnitCode()), "unit_code", queryRequest.getUnitCode());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        IPage<AppOrganizationUnit> page = new Page<>();
        page.setCurrent(queryRequest.getPage());
        page.setSize(queryRequest.getPageSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        var list = getBaseMapper().selectList(queryWrapper);
        return PageResult.builder(page.getRecords(), page.getTotal());
    }
}
