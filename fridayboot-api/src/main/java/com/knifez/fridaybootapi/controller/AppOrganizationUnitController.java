
package com.knifez.fridaybootapi.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridaybootadmin.dto.AppOrganizationUnitQueryRequest;
import com.knifez.fridaybootadmin.entity.AppOrganizationUnit;
import com.knifez.fridaybootadmin.service.IAppOrganizationUnitService;
import com.knifez.fridaybootcore.annotation.ApiRestController;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-09-01
 */

@Api(tags = "组织机构管理")
@ApiRestController
@AllowAuthenticated
@RequestMapping("/organization-unit")
public class AppOrganizationUnitController {


    private final IAppOrganizationUnitService appOrganizationUnitService;

    public AppOrganizationUnitController(IAppOrganizationUnitService appOrganizationUnitService) {
        this.appOrganizationUnitService = appOrganizationUnitService;
    }


    @PostMapping("tree-list")
    @ApiOperation("列表")
    public List<Tree<Long>> treeList(@RequestBody AppOrganizationUnitQueryRequest queryRequest) {
        QueryWrapper<AppOrganizationUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(queryRequest.getUnitCode()), "unit_code", queryRequest.getUnitCode());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());

        var list = appOrganizationUnitService.list(queryWrapper);
        TreeNodeConfig treeConfig = new TreeNodeConfig();
        return TreeUtil.build(list, null, treeConfig, (node, tree) -> {
            tree.setId(node.getId());
            tree.setName(node.getName());
            tree.setParentId(node.getParentId());
            tree.putExtra("description", node.getDescription());
            tree.putExtra("unitCode", node.getUnitCode());
            tree.putExtra("createTime", node.getCreateTime());
        });
    }

    /**
     * 根据id获取组织机构
     *
     * @param id id
     * @return {@link AppOrganizationUnit}
     */
    @GetMapping("{id}")
    @ApiOperation("根据id获取组织机构")
    public AppOrganizationUnit findById(@PathVariable Long id) {
        return appOrganizationUnitService.getById(id);
    }

    /**
     * 创建
     *
     * @param appOrganizationUnit 组织机构
     * @return {@link Boolean}
     */
    @PostMapping
    @ApiOperation("添加")
    public Boolean create(@RequestBody AppOrganizationUnit appOrganizationUnit) {
        return appOrganizationUnitService.save(appOrganizationUnit);
    }

    /**
     * 更新
     *
     * @param appOrganizationUnit 组织机构
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @ApiOperation("修改")
    public Boolean update(@RequestBody AppOrganizationUnit appOrganizationUnit) {
        return appOrganizationUnitService.updateById(appOrganizationUnit);
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
        return appOrganizationUnitService.removeById(id);
    }


}
