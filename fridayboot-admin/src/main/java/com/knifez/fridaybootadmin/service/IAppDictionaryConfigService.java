package com.knifez.fridaybootadmin.service;

import com.knifez.fridaybootadmin.entity.AppDictionaryConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典配置 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
public interface IAppDictionaryConfigService extends IService<AppDictionaryConfig> {

    /**
     * 根据dictCode获取配置列表
     * @param dictCode 字典编码
     * @return 配置列表
     */
    List<AppDictionaryConfig> listByDictCode(String dictCode);
}
