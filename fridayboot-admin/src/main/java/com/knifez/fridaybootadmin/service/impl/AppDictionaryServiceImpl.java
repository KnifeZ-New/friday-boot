package com.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knifez.fridaybootadmin.dto.AppDictionaryQueryRequest;
import com.knifez.fridaybootadmin.entity.AppDictionary;
import com.knifez.fridaybootadmin.entity.AppRole;
import com.knifez.fridaybootadmin.mapper.AppDictionaryMapper;
import com.knifez.fridaybootadmin.service.IAppDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knifez.fridaybootcore.dto.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
@Service
public class AppDictionaryServiceImpl extends ServiceImpl<AppDictionaryMapper, AppDictionary> implements IAppDictionaryService {

    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link PageResult}<{@link AppDictionary}>
     */
    @Override
    public PageResult<AppDictionary> listByPageQuery(AppDictionaryQueryRequest queryRequest) {
        QueryWrapper<AppDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(queryRequest.getEnabled() != null, "is_enabled", queryRequest.getEnabled());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        IPage<AppDictionary> page = new Page<>();
        page.setCurrent(queryRequest.getPage());
        page.setSize(queryRequest.getPageSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PageResult.builder(page.getRecords(), page.getTotal());
    }
}
