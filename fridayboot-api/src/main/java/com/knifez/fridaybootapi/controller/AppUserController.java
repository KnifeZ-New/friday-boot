package com.knifez.fridaybootapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.knifez.fridaybootadmin.dto.AppUserResponse;
import com.knifez.fridaybootadmin.entity.AppUser;
import com.knifez.fridaybootadmin.service.IAppUserService;
import com.knifez.fridaybootadmin.dto.AppUserPagedQueryRequest;
import com.knifez.fridaybootcore.annotation.ApiRestController;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import com.knifez.fridaybootcore.dto.FridayResult;
import com.knifez.fridaybootcore.dto.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
@Api(tags = "用户管理")
@ApiRestController
@RequestMapping("/user")
public class AppUserController {
    private final IAppUserService appUserService;

    public AppUserController(IAppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return <{@link PageResult}<{@link AppUser}>
     */
    @AllowAuthenticated
    @ApiOperation("分页列表")
    @PostMapping("list")
    public PageResult<AppUserResponse> pagedList(@RequestBody AppUserPagedQueryRequest queryRequest) {
        //查询列表数据
        var ret = appUserService.listByPageQuery(queryRequest);
        var list = new PageResult<AppUserResponse>();
        list.setTotal(ret.getTotal());
        list.setItems(BeanUtil.copyToList(ret.getItems(), AppUserResponse.class));
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
        return appUserService.save(user);
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
        return appUserService.updateById(user);
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


}
