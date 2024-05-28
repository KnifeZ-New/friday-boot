package org.knifez.fridaybootadmin.controller;


import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.common.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootadmin.dto.AppRoleDTO;
import org.knifez.fridaybootadmin.dto.AppRolePagedRequest;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.knifez.fridaybootadmin.service.IAppRoleService;
import org.knifez.fridaybootcore.common.annotation.ApiRestController;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.knifez.fridaybootcore.dto.TextValuePair;
import org.knifez.fridaybootcore.utils.AnnotationUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
@Tag(name = "角色管理")
@ApiRestController
@RequestMapping("/role")
public class AppRoleController {


    private final IAppRoleService roleService;

    private final IAppPermissionGrantService permissionService;
    private final ResourcePatternResolver resourcePatternResolver;

    public AppRoleController(IAppRoleService roleService, IAppPermissionGrantService permissionService, ResourcePatternResolver resourcePatternResolver) {
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.resourcePatternResolver = resourcePatternResolver;
    }


    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppRole}>
     */
    @PostMapping("list")
    @SaCheckPermission("role.pagedList")
    @Operation(summary = "分页列表", description = "role.pagedList")
    public PagedResult<AppRole> pagedList(@RequestBody AppRolePagedRequest queryRequest) {
        return roleService.listByPageQuery(queryRequest);
    }

    /**
     * 根据id获取角色
     *
     * @param id id
     * @return {@link AppUserDTO}
     */
    @GetMapping("{id}")
    @SaCheckPermission("role.findById")
    @Operation(summary = "根据id获取角色", description = "role.findById")
    public AppRoleDTO findById(@PathVariable Long id) {
        var role = roleService.getById(id);
        var result = BeanUtil.copyProperties(role, AppRoleDTO.class);
        result.setPermissions(permissionService.getSelectedMenusByRoleName(role.getName()));
        return result;
    }

    /**
     * 创建
     *
     * @param role 角色
     * @return {@link Boolean}
     */
    @PostMapping
    @SaCheckPermission("role.create")
    @Operation(summary = "新增角色", description = "role.create")
    public Boolean create(@RequestBody AppRoleDTO role) {
        role.setId(null);
        var isAdded = roleService.save(role);
        if (isAdded) {
            roleService.savePermissionsByRole(role.getPermissions(), role.getName());
        }
        return isAdded;
    }

    /**
     * 更新
     *
     * @param role 角色
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @SaCheckPermission("role.update")
    @Operation(summary = "修改角色", description = "role.update")
    public Boolean update(@RequestBody AppRoleDTO role) {
        roleService.savePermissionsByRole(role.getPermissions(), role.getName());
        return roleService.updateById(role);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @SaCheckPermission("role.delete")
    @Operation(summary = "删除角色", description = "role.delete")
    public Boolean delete(@PathVariable Long id) {
        return roleService.removeById(id);
    }

    /**
     * 获取系统权限
     *
     * @return {@link List}<{@link String}>
     */
    @AllowAuthenticated
    @GetMapping("authorities/list")
    @Operation(summary = "获取系统权限列表")
    public List<TextValuePair> getSystemAuthorities() throws Exception {
        return AnnotationUtils.getAuthorityList(resourcePatternResolver, ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "**/controller/**/**.class", SaCheckPermission.class);
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

}
