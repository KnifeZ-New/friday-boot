
package com.knifez.fridaybootapi.controller;

import cn.hutool.core.lang.tree.Tree;
import com.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import com.knifez.fridaybootadmin.dto.AppMenuButtonResponse;
import com.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import com.knifez.fridaybootadmin.entity.AppMenu;
import com.knifez.fridaybootadmin.service.IAppMenuService;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
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


    @ApiOperation("菜单列表")
    @PostMapping("tree-list")
    public List<Tree<Integer>> treeList(@RequestBody AppMenuQueryRequest queryRequest) {
        return appMenuService.getTreeList(queryRequest);
    }

    @ApiOperation("菜单按钮列表")
    @PostMapping("button-list")
    public List<AppMenuButtonResponse> menuButtonList(@RequestBody AppMenuButtonQueryRequest queryRequest) {
        return appMenuService.getMenuButtons(queryRequest);
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
