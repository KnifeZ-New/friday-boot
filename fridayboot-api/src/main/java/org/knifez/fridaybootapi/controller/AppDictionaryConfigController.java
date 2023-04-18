package org.knifez.fridaybootapi.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.dto.AppDictionaryConfigQueryRequest;
import org.knifez.fridaybootcore.dto.AppDictionaryConfigTreeSetQueryRequest;
import org.knifez.fridaybootcore.entity.AppDictionaryConfig;
import org.knifez.fridaybootcore.service.IAppDictionaryConfigService;
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
    @Operation(summary = "根据id获取字典配置")
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
            tree.setId(node.getId());
            tree.setName(node.getName());
            tree.setParentId(node.getParentId());
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
    @Operation(summary = "根据code获取字典属性树")
    public List<Tree<Long>> treeListByDictCode(@PathVariable String dictCode, @RequestBody AppDictionaryConfigQueryRequest queryRequest) {
        QueryWrapper<AppDictionaryConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        queryWrapper.eq(queryRequest.getEnabled() != null, "is_enabled", queryRequest.getEnabled());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        var list = appDictionaryConfigService.list(queryWrapper);
        TreeNodeConfig treeConfig = new TreeNodeConfig();
        treeConfig.setWeightKey("sort");
        return TreeUtil.build(list, null, treeConfig, (node, tree) -> {
            tree.setId(node.getId());
            tree.setName(node.getName());
            tree.setParentId(node.getParentId());
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
    @Operation(summary = "添加")
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
    @Operation(summary = "修改")
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
    @Operation(summary = "删除")
    public Boolean delete(@PathVariable Long id) {
        return appDictionaryConfigService.removeById(id);
    }


}
