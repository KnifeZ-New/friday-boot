package org.knifez.fridaybootadmin.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.dto.AppUserModifyDTO;
import org.knifez.fridaybootadmin.dto.AppUserPagedRequest;
import org.knifez.fridaybootadmin.dto.AppUserResetPasswordRequest;
import org.knifez.fridaybootadmin.entity.AppOrganizationUnit;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootadmin.service.IAppOrganizationUnitService;
import org.knifez.fridaybootadmin.service.IAppRoleService;
import org.knifez.fridaybootadmin.service.IAppUserRoleService;
import org.knifez.fridaybootadmin.service.IAppUserService;
import org.knifez.fridaybootcore.common.annotation.ApiRestController;
import org.knifez.fridaybootcore.common.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.dto.FridayResult;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.knifez.fridaybootcore.common.enums.ResultStatus;
import org.knifez.fridaybootcore.common.exception.FridayResultException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
@Tag(name = "用户管理")
@ApiRestController
@RequestMapping("/user")
public class AppUserController {
    private final IAppUserService appUserService;

    private final IAppUserRoleService userRoleService;
    private final IAppRoleService roleService;

    private final IAppOrganizationUnitService organizationUnitService;

    public AppUserController(IAppUserService appUserService, IAppUserRoleService userRoleService, IAppRoleService roleService, IAppOrganizationUnitService organizationUnitService) {
        this.appUserService = appUserService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.organizationUnitService = organizationUnitService;
    }

    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return <{@link PagedResult}<{@link AppUser}>
     */
    @Operation(summary = "分页列表", description = "user.pagedList")
    @PreAuthorize("hasAuthority('user.pagedList')")
    @PostMapping("list")
    public PagedResult<AppUserDTO> pagedList(@RequestBody AppUserPagedRequest queryRequest) {
        //查询列表数据
        var ret = appUserService.listByPageQuery(queryRequest);
        var responseList = BeanUtil.copyToList(ret.getItems(), AppUserDTO.class);
        var organizationList = organizationUnitService.list();
        for (AppUserDTO user : responseList) {
            user.setRoles(roleService.listByUserId(user.getId()));
            var organization = organizationList.stream()
                    .filter(x -> x.getId().equals(user.getOrganizationId())).map(AppOrganizationUnit::getName).collect(Collectors.joining());
            user.setOrganizationName(organization);
        }
        var list = new PagedResult<AppUserDTO>();
        list.setTotal(ret.getTotal());
        list.setItems(responseList);
        return list;
    }


    /**
     * 通过id获取用户
     *
     * @param id id
     * @return {@link AppUserDTO}
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('user.findById')")
    @Operation(summary = "根据id获取用户", description = "user.findById")
    public AppUserDTO findById(@PathVariable Long id) {
        return appUserService.getUserDtoByAccountOrId("", id);
    }

    /**
     * 创建
     *
     * @param user 用户
     * @return {@link Boolean}
     */
    @PostMapping
    @PreAuthorize("hasAuthority('user.create')")
    @Operation(summary = "新增用户", description = "user.create")
    public Boolean create(@RequestBody AppUserModifyDTO user) {
        if (appUserService.accountExist(user.getAccount())) {
            throw new FridayResultException(ResultStatus.FORBIDDEN_003);
        }
        return appUserService.saveWithUserRoles(user, true);
    }

    /**
     * 更新
     *
     * @param user 用户
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('user.update')")
    @Operation(summary = "修改用户", description = "user.update")
    public Boolean update(@RequestBody AppUserModifyDTO user) {
        return appUserService.saveWithUserRoles(user, false);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link FridayResult}<{@link Boolean}>
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    @Operation(summary = "删除用户", description = "user.delete")
    public Boolean delete(@PathVariable Long id) {
        return appUserService.removeById(id);
    }


    @PostMapping("reset-password")
    @PreAuthorize("hasAuthority('user.resetPassword')")
    @Operation(summary = "重置密码", description = "user.resetPassword")
    public Boolean resetPassword(@RequestBody AppUserResetPasswordRequest user) {
        if (appUserService.checkOriginPassword(user.getId(), user.getOriginPassword()) || user.getSkipCheckPassword()) {
            return appUserService.updatePassword(user.getId(), user.getNewPassword());
        } else {
            throw new FridayResultException(ResultStatus.UNAUTHORIZED_002);
        }
    }

    /**
     * 检查帐号是否存在
     *
     * @param account 账户
     * @return {@link String}
     */
    @AllowAuthenticated
    @GetMapping("account-exist/{account}")
    @Operation(summary = "检查帐号是否存在")
    public FridayResult<String> exist(@PathVariable String account) {
        var user = appUserService.accountExist(account);
        if (user) {
            return FridayResult.fail(ResultStatus.FORBIDDEN_003);
        } else {
            return FridayResult.ok(account + "可使用");
        }
    }

    /**
     * 获取用户关联角色
     *
     * @param id userid
     * @return roleIds
     */
    @GetMapping("{id}/roles")
    @AllowAuthenticated
    @Operation(summary = "获取用户关联角色")
    public List<Integer> getUserRoles(@PathVariable Integer id) {
        return userRoleService.listRolesByUserId(id);
    }
}
