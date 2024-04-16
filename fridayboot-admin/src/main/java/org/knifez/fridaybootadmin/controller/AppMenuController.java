package org.knifez.fridaybootadmin.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.common.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import org.knifez.fridaybootadmin.dto.AppMenuDTO;
import org.knifez.fridaybootadmin.dto.AppMenuModifyRequest;
import org.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import org.knifez.fridaybootadmin.entity.AppMenu;
import org.knifez.fridaybootadmin.common.enums.MenuTypeEnum;
import org.knifez.fridaybootadmin.service.IAppMenuService;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.common.annotation.ApiRestController;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-11
 */
@Tag(name = "菜单管理")
@ApiRestController
@RequestMapping("/menu")
public class AppMenuController {


    private final IAppMenuService appMenuService;

    public AppMenuController(IAppMenuService appMenuService) {
        this.appMenuService = appMenuService;
    }


    @Operation(summary = "菜单列表", description = "menu.treeList")
    @SaCheckPermission("menu.treeList")
    @PostMapping("tree-list")
    public List<Tree<Integer>> treeList(@RequestBody AppMenuQueryRequest queryRequest) {
        return appMenuService.getTreeList(queryRequest);
    }

    /**
     * 获取用户菜单
     *
     * @return {@link List}<{@link AppMenuDTO}>
     */
    @Operation(summary = "获取权限菜单列表")
    @AllowAuthenticated
    @PostMapping("authentication-list")
    public List<AppMenuDTO> getUserMenu() {
        return appMenuService.getMenuByPermissions(StpUtil.getPermissionList(), JwtTokenUtils.isSuperAdmin());
    }


    @Operation(summary = "菜单按钮列表")
    @AllowAuthenticated
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
    @SaCheckPermission("menu.findById")
    @Operation(summary = "根据id获取菜单", description = "menu.findById")
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
    @SaCheckPermission("menu.create")
    @Operation(summary = "添加", description = "menu.create")
    public Boolean create(@RequestBody AppMenuModifyRequest appMenu) {
        appMenu.setId(null);
        if (Objects.equals(appMenu.getType(), MenuTypeEnum.MENU_BUTTON.getValue())) {
            var parentMenu = appMenuService.getById(appMenu.getParentId());
            appMenu.setRoutePath(parentMenu.getRoute() + "/" + appMenu.getRoute());
        }
        return appMenuService.save(appMenu);
    }

    /**
     * 更新
     *
     * @param appMenu 菜单
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @SaCheckPermission("menu.update")
    @Operation(summary = "修改", description = "menu.update")
    public Boolean update(@RequestBody AppMenuModifyRequest appMenu) {
        if (Objects.equals(appMenu.getType(), MenuTypeEnum.MENU_BUTTON.getValue())) {
            var parentMenu = appMenuService.getById(appMenu.getParentId());
            appMenu.setRoutePath(parentMenu.getRoute() + "/" + appMenu.getRoute());
        }
        return appMenuService.updateById(appMenu);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @SaCheckPermission("menu.delete")
    @Operation(summary = "删除", description = "menu.delete")
    public Boolean delete(@PathVariable Integer id) {
        var result = false;
        var children = appMenuService.getChildrenIds(id);
        if (children.isEmpty()) {
            // 删除当前数据
            result = appMenuService.removeById(id);
        } else {
            var childrenMenus = appMenuService.getMenuByIds(children);
            // 不包含菜单和目录时全部删除
            if (childrenMenus.stream().noneMatch(x -> x.getType() < 2)) {
                children.add(id);
                result = appMenuService.removeBatchByIds(children);
            }
        }
        return result;
    }

}
