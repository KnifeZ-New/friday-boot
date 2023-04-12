package org.knifez.fridaybootcore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootcore.dto.AppDictionaryQueryRequest;
import org.knifez.fridaybootcore.dto.PageResult;
import org.knifez.fridaybootcore.entity.AppDictionary;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
@author KnifeZ
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
