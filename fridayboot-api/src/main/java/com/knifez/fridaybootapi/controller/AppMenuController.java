
package com.knifez.fridaybootapi.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import com.knifez.fridaybootadmin.entity.AppMenu;
import com.knifez.fridaybootadmin.service.IAppMenuService;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.knifez.fridaybootcore.annotation.ApiRestController;

import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-11
 */
@AllowAuthenticated
@Api(tags = "菜单管理")
@ApiRestController
@RequestMapping("/menu")
public class AppMenuController {


    private final IAppMenuService appMenuService;

    public AppMenuController(IAppMenuService appMenuService) {
        this.appMenuService = appMenuService;
    }


    @PostMapping("tree-list")
    @ApiOperation("列表")
    public List<Tree<Integer>> treeList(@RequestBody AppMenuQueryRequest queryRequest) {
        QueryWrapper<AppMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        queryWrapper.orderByAsc("sort");
        var list = appMenuService.list(queryWrapper);
        TreeNodeConfig treeConfig = new TreeNodeConfig();

        return TreeUtil.build(list, null, treeConfig, (node, tree) -> {
            tree.setId(node.getId());
            tree.setName(node.getName());
            tree.setParentId(node.getParentId());
            tree.putExtra("icon", node.getIcon());
            tree.putExtra("type", node.getType());
            tree.putExtra("sort", node.getSort());
            tree.putExtra("routePath", node.getRoutePath());
            tree.putExtra("component", node.getComponent());
            tree.putExtra("permission", node.getPermission());
            tree.putExtra("visible", node.getVisible());
            tree.putExtra("enabled", node.getEnabled());
            tree.putExtra("keepAlive", node.getKeepAlive());
            tree.putExtra("createTime", node.getCreateTime());
        });
    }

    /**
     * 根据id获取菜单
     *
     * @param id id
     * @return {@link AppMenu}
     */
    @GetMapping("{id}")
    @ApiOperation("根据id获取菜单")
    public AppMenu findById(@PathVariable Long id) {
        return appMenuService.getById(id);
    }

    /**
     * 创建
     *
     * @param appMenu 菜单
     * @return {@link Boolean}
     */
    @PostMapping
    @ApiOperation("添加")
    public Boolean create(@RequestBody AppMenu appMenu) {
        return appMenuService.save(appMenu);
    }

    /**
     * 更新
     *
     * @param appMenu 菜单
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @ApiOperation("修改")
    public Boolean update(@RequestBody AppMenu appMenu) {
        return appMenuService.updateById(appMenu);
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
        return appMenuService.removeById(id);
    }


}
