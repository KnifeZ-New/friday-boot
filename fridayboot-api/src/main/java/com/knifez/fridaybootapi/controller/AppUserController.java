package com.knifez.fridaybootapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.knifez.fridaybootadmin.dto.AppUserResponse;
import com.knifez.fridaybootadmin.entity.AppUser;
import com.knifez.fridaybootadmin.service.IAppRoleService;
import com.knifez.fridaybootadmin.service.IAppUserRoleService;
import com.knifez.fridaybootadmin.service.IAppUserService;
import com.knifez.fridaybootadmin.dto.AppUserPagedQueryRequest;
import com.knifez.fridaybootcore.annotation.ApiRestController;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import com.knifez.fridaybootcore.dto.FridayResult;
import com.knifez.fridaybootcore.dto.PageResult;
import com.knifez.fridaybootcore.enums.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
@AllowAuthenticated
@Api(tags = "用户管理")
@ApiRestController
@RequestMapping("/user")
public class AppUserController {
    private final IAppUserService appUserService;

    private final IAppUserRoleService userRoleService;

    private final IAppRoleService roleService;

    public AppUserController(IAppUserService appUserService, IAppUserRoleService userRoleService, IAppRoleService roleService) {
        this.appUserService = appUserService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
    }

    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return <{@link PageResult}<{@link AppUser}>
     */
    @ApiOperation("分页列表")
    @PostMapping("list")
    public PageResult<AppUserResponse> pagedList(@RequestBody AppUserPagedQueryRequest queryRequest) {
        //查询列表数据
        var ret = appUserService.listByPageQuery(queryRequest);
        var responseList = BeanUtil.copyToList(ret.getItems(), AppUserResponse.class);
        for (AppUserResponse user : responseList) {
            user.setRoles(roleService.listByUserId(user.getId()));
        }
        var list = new PageResult<AppUserResponse>();
        list.setTotal(ret.getTotal());
        list.setItems(responseList);
        return list;
    }


    /**
     * 通过id获取用户
     *
     * @param id id
     * @return {@link AppUserResponse}
     */
    @GetMapping("{id}")
    @ApiOperation("根据id获取用户")
    public AppUserResponse findById(@PathVariable Long id) {
        var user = appUserService.getById(id);
        AppUserResponse result = new AppUserResponse();
        BeanUtils.copyProperties(user, result);
        return result;
    }

    /**
     * 创建
     *
     * @param user 用户
     * @return {@link Boolean}
     */
    @PostMapping
    @ApiOperation("新增用户")
    public Boolean create(@RequestBody AppUser user) {
        return appUserService.saveWithUserRoles(user, false);
    }

    /**
     * 更新
     *
     * @param user 用户
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @ApiOperation("修改用户")
    public Boolean update(@RequestBody AppUser user) {
        return appUserService.saveWithUserRoles(user, true);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link FridayResult}<{@link Boolean}>
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除用户")
    public Boolean delete(@PathVariable Long id) {
        return appUserService.removeById(id);
    }


    /**
     * 检查帐号是否存在
     *
     * @param account 账户
     * @return {@link String}
     */
    @AllowAuthenticated
    @GetMapping("account-exist/{account}")
    @ApiOperation("检查帐号是否存在")
    public FridayResult<String> exist(@PathVariable String account) {
        var user = appUserService.findByAccount(account);
        if (user.getId() == null) {
            return FridayResult.ok(account + "可使用");
        } else {
            return FridayResult.fail(ResultStatus.FORBIDDEN_003);
        }
    }

    /**
     * 获取用户关联角色
     *
     * @param id userid
     * @return roleIds
     */
    @GetMapping("{id}/roles")
    @ApiOperation("获取用户关联角色")
    public List<Long> getUserRoles(@PathVariable Long id) {
        return userRoleService.listRolesByUserId(id);
    }
}
