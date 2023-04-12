package org.knifez.fridaybootapi.controller.admin;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.*;
import org.knifez.fridaybootadmin.entity.AppMenu;
import org.knifez.fridaybootadmin.service.IAppMenuService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "菜单管理")
@ApiRestController
@RequestMapping("/menu")
public class AppMenuController {


    private final IAppMenuService appMenuService;

    public AppMenuController(IAppMenuService appMenuService) {
        this.appMenuService = appMenuService;
    }


    @Operation(summary = "菜单列表")
    @PostMapping("tree-list")
    public List<Tree<Integer>> treeList(@RequestBody AppMenuQueryRequest queryRequest) {
        return appMenuService.getTreeList(queryRequest);
    }

    @Operation(summary = "菜单路由")
    @PostMapping("menu-routes")
    public List<AppMenuRouteDTO> list() {
        return appMenuService.getMenuRoutes();
    }


    @Operation(summary = "菜单按钮列表")
    @PostMapping("button-list")
    public List<AppMenuDTO> menuButtonList(@RequestBody AppMenuButtonQueryRequest queryRequest) {
        return appMenuService.getMenuButtons(queryRequest);
    }

    /**
     * 根据id获取菜单
     *
     * @param id id
     * @return {@link AppMenu}
     */
    @GetMapping("{id}")
    @Operation(summary = "根据id获取菜单")
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
    @Operation(summary = "添加")
    public Boolean create(@RequestBody AppMenuModifyRequest appMenu) {
        appMenu.setId(null);
        return appMenuService.save(appMenu);
    }

    /**
     * 更新
     *
     * @param appMenu 菜单
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @Operation(summary = "修改")
    public Boolean update(@RequestBody AppMenuModifyRequest appMenu) {
        return appMenuService.updateById(appMenu);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    public Boolean delete(@PathVariable Integer id) {
        var currentMenu = appMenuService.getById(id);
        var result = false;
        // 删除当前数据
        result = appMenuService.removeById(id);
        if (result) {
            // 设置children菜单父级菜单为当前菜单的父级
            result = appMenuService.updateChildrenLevel(id, currentMenu.getParentId());
        }
        return result;
    }


}
