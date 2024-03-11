package org.knifez.fridaybootadmin.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppDictionaryConfigQueryRequest;
import org.knifez.fridaybootadmin.dto.AppDictionaryConfigTreeSetQueryRequest;
import org.knifez.fridaybootadmin.entity.AppDictionaryConfig;
import org.knifez.fridaybootadmin.service.IAppDictionaryConfigService;
import org.knifez.fridaybootcore.common.annotation.ApiRestController;
import org.knifez.fridaybootcore.common.annotation.permission.AllowAuthenticated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "字典配置管理")
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
    @PreAuthorize("hasAuthority('dictConfig.findById')")
    @Operation(summary = "根据id获取字典配置", description = "dictConfig.findById")
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
    @Operation(summary = "根据code获取字典属性列表")
    public List<AppDictionaryConfig> listByDictCode(@PathVariable String dictCode, @RequestBody AppDictionaryConfigQueryRequest queryRequest) {
        QueryWrapper<AppDictionaryConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        queryWrapper.eq(queryRequest.getEnabled() != null, "is_enabled", queryRequest.getEnabled());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        return appDictionaryConfigService.list(queryWrapper);
    }

    @PostMapping("tree-sets")
    @Operation(summary = "获取字典属性树集合")
    public List<Tree<Long>> treeListByDictCodes(@RequestBody AppDictionaryConfigTreeSetQueryRequest queryRequest) {
        QueryWrapper<AppDictionaryConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("dict_code", queryRequest.getDictCodes());
        queryWrapper.eq("is_enabled", true);
        var list = appDictionaryConfigService.list(queryWrapper);
        TreeNodeConfig treeConfig = new TreeNodeConfig();
        treeConfig.setWeightKey("sort");
        return TreeUtil.build(list, null, treeConfig, (node, tree) -> {
            tree.setId(Long.valueOf(node.getId()));
            tree.setName(node.getName());
            tree.setParentId(Long.valueOf(node.getParentId()));
            tree.putExtra("sort", node.getSort());
            tree.putExtra("dictCode", node.getDictCode());
            tree.putExtra("value", node.getValue());
        });
    }

    /**
     * 根据code获取字典属性列表
     *
     * @param dictCode dict类型代码
     * @return {@link List}<{@link AppDictionaryConfig}>
     */
    @PostMapping("tree/{dictCode}")
    @PreAuthorize("hasAuthority('dictConfig.treeListByDictCode')")
    @Operation(summary = "根据code获取字典属性树", description = "dictConfig.treeListByDictCode")
    public List<Tree<Long>> treeListByDictCode(@PathVariable String dictCode, @RequestBody AppDictionaryConfigQueryRequest queryRequest) {
        QueryWrapper<AppDictionaryConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        queryWrapper.eq(queryRequest.getEnabled() != null, "is_enabled", queryRequest.getEnabled());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        var list = appDictionaryConfigService.list(queryWrapper);
        TreeNodeConfig treeConfig = new TreeNodeConfig();
        treeConfig.setWeightKey("sort");
        return TreeUtil.build(list, null, treeConfig, (node, tree) -> {
            tree.setId(Long.valueOf(node.getId()));
            tree.setName(node.getName());
            tree.setParentId(Long.valueOf(node.getParentId()));
            tree.putExtra("dictCode", node.getDictCode());
            tree.putExtra("value", node.getValue());
            tree.putExtra("icon", node.getIcon());
            tree.putExtra("sort", node.getSort());
            tree.putExtra("enabled", node.getEnabled());
            tree.putExtra("labelLevel", node.getLabelLevel());
            tree.putExtra("valueType", node.getValueType());
            tree.putExtra("description", node.getDescription());
        });
    }

    /**
     * 创建
     *
     * @param appDictionaryConfig 字典配置
     * @return {@link Boolean}
     */
    @PostMapping
    @PreAuthorize("hasAuthority('dictConfig.create')")
    @Operation(summary = "添加", description = "dictConfig.create")
    public Boolean create(@RequestBody AppDictionaryConfig appDictionaryConfig) {
        appDictionaryConfig.setId(null);
        return appDictionaryConfigService.save(appDictionaryConfig);
    }

    /**
     * 更新
     *
     * @param appDictionaryConfig 字典配置
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('dictConfig.update')")
    @Operation(summary = "修改", description = "dictConfig.update")
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
    @PreAuthorize("hasAuthority('dictConfig.delete')")
    @Operation(summary = "删除", description = "dictConfig.delete")
    public Boolean delete(@PathVariable Long id) {
        return appDictionaryConfigService.removeById(id);
    }


}
