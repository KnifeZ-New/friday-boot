package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppDictionaryPagedRequest;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.knifez.fridaybootadmin.entity.AppDictionary;
import org.knifez.fridaybootadmin.mapper.AppDictionaryMapper;
import org.knifez.fridaybootadmin.service.IAppDictionaryService;
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
     * @return {@link PagedResult}<{@link AppDictionary}>
     */
    @Override
    public PagedResult<AppDictionary> listByPageQuery(AppDictionaryPagedRequest queryRequest) {
        LambdaQueryWrapper<AppDictionary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(queryRequest.getEnabled() != null, AppDictionary::getEnabled, queryRequest.getEnabled());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), AppDictionary::getName, queryRequest.getName());
        IPage<AppDictionary> page = new Page<>();
        page.setCurrent(queryRequest.getCurrent());
        page.setSize(queryRequest.getSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PagedResult.builder(page.getRecords(), page.getTotal());
    }
}
