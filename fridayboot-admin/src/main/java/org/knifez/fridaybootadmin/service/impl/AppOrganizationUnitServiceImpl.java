package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppOrganizationUnitQueryRequest;
import org.knifez.fridaybootadmin.entity.AppOrganizationUnit;
import org.knifez.fridaybootadmin.mapper.AppOrganizationUnitMapper;
import org.knifez.fridaybootadmin.service.IAppOrganizationUnitService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
     * 组织架构列表（包含查询结果的父节点）
     *
     * @param queryRequest 查询请求
     * @return {@link List}<{@link AppOrganizationUnit}>
     */
    @Override
    public List<AppOrganizationUnit> listWithParentNodes(AppOrganizationUnitQueryRequest queryRequest) {
        LambdaQueryWrapper<AppOrganizationUnit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(queryRequest.getUnitCode()), AppOrganizationUnit::getUnitCode, queryRequest.getUnitCode());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), AppOrganizationUnit::getName, queryRequest.getName());
        var list = list(queryWrapper);
        if (StringUtils.hasText(queryRequest.getName()) || StringUtils.hasText(queryRequest.getUnitCode())) {
            list = mixWithParentNodes(list);
        }
        return list;
    }

    private List<AppOrganizationUnit> mixWithParentNodes(List<AppOrganizationUnit> list) {
        List<AppOrganizationUnit> mixdList = new ArrayList<>();
        for (var item : list) {
            if (item.getParentId() != null) {
                List<AppOrganizationUnit> parentNodes = new ArrayList<>();
                parentNodes.add(item);
                while (item.getParentId() != null) {
                    item = getById(item.getParentId());
                    parentNodes.add(item);
                }
                mixdList.addAll(parentNodes);
            } else {
                mixdList.add(item);
            }
        }
        return mixdList.stream().distinct().toList();
    }
}
