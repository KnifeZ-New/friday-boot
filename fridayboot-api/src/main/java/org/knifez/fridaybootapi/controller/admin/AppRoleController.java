package org.knifez.fridaybootapi.controller.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppRolePagedRequest;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.knifez.fridaybootadmin.service.IAppRoleService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
@AllowAuthenticated
@Tag(name = "角色管理")
@ApiRestController
@RequestMapping("/role")
public class AppRoleController {


    private final IAppRoleService roleService;

    private final IAppPermissionGrantService permissionService;

    public AppRoleController(IAppRoleService roleService, IAppPermissionGrantService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }


    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppRole}>
     */
    @PostMapping("list")
    @Operation(summary = "分页列表")
    public PagedResult<AppRole> pagedList(@RequestBody AppRolePagedRequest queryRequest) {
        return roleService.listByPageQuery(queryRequest);
    }

    /**
     * 所有角色列表
     *
     * @return {@link List}<{@link AppRole}>
     */
    @AllowAuthenticated
    @PostMapping("all")
    @Operation(summary = "所有角色列表")
    public List<AppRole> allRoles() {
        return roleService.list();
    }

    @AllowAuthenticated
    @GetMapping("{roleName}/permission-list")
    @Operation(summary = "根据角色名获取角色绑定菜单列表")
    public int[] permissionsByRoleName(@PathVariable String roleName) {
        var list = permissionService.getSelectMenusByRoleName(roleName);
        return list.stream().mapToInt(Integer::parseInt).toArray();
    }

    /**
     * 根据id获取角色
     *
     * @param id id
     * @return {@link AppUserDTO}
     */
    @GetMapping("{id}")
    @Operation(summary = "根据id获取角色")
    public AppRole findById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    /**
     * 创建
     *
     * @param role 角色
     * @return {@link Boolean}
     */
    @PostMapping
    @Operation(summary = "新增角色")
    public Boolean create(@RequestBody AppRole role) {
        permissionService.saveByRole(role.getPermissions(), role.getName());
        role.setId(null);
        return roleService.save(role);
    }

    /**
     * 更新
     *
     * @param role 角色
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @Operation(summary = "修改角色")
    public Boolean update(@RequestBody AppRole role) {
        permissionService.saveByRole(role.getPermissions(), role.getName());
        return roleService.updateById(role);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @Operation(summary = "删除角色")
    public Boolean delete(@PathVariable Long id) {
        return roleService.removeById(id);
    }
}
