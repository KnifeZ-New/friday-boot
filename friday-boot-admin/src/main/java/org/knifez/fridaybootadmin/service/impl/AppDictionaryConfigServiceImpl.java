package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.entity.AppDictionaryConfig;
import org.knifez.fridaybootadmin.mapper.AppDictionaryConfigMapper;
import org.knifez.fridaybootadmin.service.IAppDictionaryConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典配置 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
@Service
public class AppDictionaryConfigServiceImpl extends ServiceImpl<AppDictionaryConfigMapper, AppDictionaryConfig> implements IAppDictionaryConfigService {

    /**
     * 根据dictCode获取配置列表
     *
     * @param dictCode 字典编码
     * @return 配置列表
     */
    @Override
    public List<AppDictionaryConfig> listByDictCode(String dictCode) {
        QueryWrapper<AppDictionaryConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        queryWrapper.eq("is_enabled", true);
        return list(queryWrapper);
    }
}
