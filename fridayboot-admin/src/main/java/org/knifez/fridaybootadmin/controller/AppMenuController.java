package org.knifez.fridaybootadmin.controller;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import org.knifez.fridaybootadmin.dto.AppMenuDTO;
import org.knifez.fridaybootadmin.dto.AppMenuModifyRequest;
import org.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import org.knifez.fridaybootadmin.entity.AppMenu;
import org.knifez.fridaybootadmin.enums.MenuTypeEnum;
import org.knifez.fridaybootadmin.service.IAppMenuService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
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


    @Operation(summary = "菜单列表")
    @PostMapping("tree-list")
    public List<Tree<Integer>> treeList(@RequestBody AppMenuQueryRequest queryRequest) {
        return appMenuService.getTreeList(queryRequest);
    }

    @AllowAuthenticated
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
    @Operation(summary = "修改")
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
    @Operation(summary = "删除")
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
