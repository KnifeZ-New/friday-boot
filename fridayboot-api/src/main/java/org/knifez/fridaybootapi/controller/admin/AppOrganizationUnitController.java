
package org.knifez.fridaybootapi.controller.admin;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppOrganizationUnitQueryRequest;
import org.knifez.fridaybootadmin.entity.AppOrganizationUnit;
import org.knifez.fridaybootadmin.service.IAppOrganizationUnitService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-09-01
 */

@Tag(name = "组织机构管理")
@ApiRestController
@AllowAuthenticated
@RequestMapping("/organization-unit")
public class AppOrganizationUnitController {


    private final IAppOrganizationUnitService appOrganizationUnitService;

    public AppOrganizationUnitController(IAppOrganizationUnitService appOrganizationUnitService) {
        this.appOrganizationUnitService = appOrganizationUnitService;
    }


    @PostMapping("tree-list")
    @Operation(summary = "列表")
    public List<Tree<Long>> treeList(@RequestBody AppOrganizationUnitQueryRequest queryRequest) {
        var list = appOrganizationUnitService.listWithParentNodes(queryRequest);
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
    @Operation(summary = "根据id获取组织机构")
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
    @Operation(summary = "添加")
    public Boolean create(@RequestBody AppOrganizationUnit appOrganizationUnit) {
        appOrganizationUnit.setId(null);
        return appOrganizationUnitService.save(appOrganizationUnit);
    }

    /**
     * 更新
     *
     * @param appOrganizationUnit 组织机构
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @Operation(summary = "修改")
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
    @Operation(summary = "删除")
    public Boolean delete(@PathVariable Long id) {
        return appOrganizationUnitService.removeById(id);
    }


}
