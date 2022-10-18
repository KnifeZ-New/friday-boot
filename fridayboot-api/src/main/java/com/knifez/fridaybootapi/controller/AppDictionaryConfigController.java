
package com.knifez.fridaybootapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridaybootadmin.entity.AppDictionaryConfig;
import com.knifez.fridaybootadmin.service.IAppDictionaryConfigService;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import com.knifez.fridaybootcore.annotation.ApiRestController;

import java.util.List;

/**
 * <p>
 * 字典配置 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
@AllowAuthenticated
@Api(tags = "字典配置管理")
@ApiRestController
@RequestMapping("/dictionary-config")
public class AppDictionaryConfigController {


    private final IAppDictionaryConfigService appDictionaryConfigService;

    public AppDictionaryConfigController(IAppDictionaryConfigService appDictionaryConfigService) {
        this.appDictionaryConfigService = appDictionaryConfigService;
    }


    /**
     * 根据id获取字典配置
     *
     * @param id id
     * @return {@link AppDictionaryConfig}
     */
    @GetMapping("{id}")
    @ApiOperation("根据id获取字典配置")
    public AppDictionaryConfig findById(@PathVariable Long id) {
        return appDictionaryConfigService.getById(id);
    }

    /**
     * 根据code获取字典属性列表
     *
     * @param dictCode dict类型代码
     * @return {@link List}<{@link AppDictionaryConfig}>
     */
    @PostMapping("list/{dictCode}")
    @ApiOperation("根据code获取字典属性列表")
    public List<AppDictionaryConfig> listByDictCode(@PathVariable String dictCode) {
        QueryWrapper<AppDictionaryConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        return appDictionaryConfigService.list(queryWrapper);
    }

    /**
     * 创建
     *
     * @param appDictionaryConfig 字典配置
     * @return {@link Boolean}
     */
    @PostMapping
    @ApiOperation("添加")
    public Boolean create(@RequestBody AppDictionaryConfig appDictionaryConfig) {
        return appDictionaryConfigService.save(appDictionaryConfig);
    }

    /**
     * 更新
     *
     * @param appDictionaryConfig 字典配置
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @ApiOperation("修改")
    public Boolean update(@RequestBody AppDictionaryConfig appDictionaryConfig) {
        return appDictionaryConfigService.updateById(appDictionaryConfig);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public Boolean delete(@PathVariable Long id) {
        return appDictionaryConfigService.removeById(id);
    }


}
