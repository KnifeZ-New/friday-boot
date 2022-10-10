package com.knifez.fridaybootadmin.service;

import com.knifez.fridaybootadmin.dto.AppDictionaryQueryRequest;
import com.knifez.fridaybootadmin.entity.AppDictionary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.knifez.fridaybootcore.dto.PageResult;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
public interface IAppDictionaryService extends IService<AppDictionary> {

    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link PageResult}<{@link AppDictionary}>
     */
    PageResult<AppDictionary> listByPageQuery(AppDictionaryQueryRequest queryRequest);
}
