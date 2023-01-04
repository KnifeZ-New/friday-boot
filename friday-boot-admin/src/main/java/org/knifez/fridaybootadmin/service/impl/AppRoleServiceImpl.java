package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppRolePagedQueryRequest;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootadmin.mapper.AppRoleMapper;
import org.knifez.fridaybootadmin.service.IAppRoleService;
import org.knifez.fridaybootadmin.service.IAppUserRoleService;
import org.knifez.fridaybootcore.dto.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
@author KnifeZ
 * @since 2022-07-06
 */
@Service
public class AppRoleServiceImpl extends ServiceImpl<AppRoleMapper, AppRole> implements IAppRoleService {

    private final IAppUserRoleService userRoleService;

    public AppRoleServiceImpl(IAppUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    @Override
    public List<String> listRoleNameByUserId(long userId) {
        var ids = userRoleService.listRolesByUserId(userId);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return baseMapper.selectBatchIds(ids).stream().map(AppRole::getName).toList();
    }

    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    @Override
    public List<AppRole> listByUserId(long userId) {
        var ids = userRoleService.listRolesByUserId(userId);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return baseMapper.selectBatchIds(ids);
    }

    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link List}<{@link AppRole}>
     */
    @Override
    public PageResult<AppRole> listByPageQuery(AppRolePagedQueryRequest queryRequest) {
        QueryWrapper<AppRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(queryRequest.getDisplayName()), "display_name", queryRequest.getDisplayName());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        IPage<AppRole> page = new Page<>();
        page.setCurrent(queryRequest.getPage());
        page.setSize(queryRequest.getPageSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PageResult.builder(page.getRecords(), page.getTotal());
    }
}
