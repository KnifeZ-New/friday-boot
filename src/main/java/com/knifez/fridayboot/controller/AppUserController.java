package com.knifez.fridayboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knifez.fridayboot.domain.dto.AppUserResponse;
import com.knifez.fridayboot.domain.dto.FridayResponse;
import com.knifez.fridayboot.entity.AppUser;
import com.knifez.fridayboot.service.IAppUserService;
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
@RestController
@RequestMapping("/api/user")
public class AppUserController {
    private final IAppUserService appUserService;

    public AppUserController(IAppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @ApiOperation("分页列表")
    @PostMapping("paged-list")
    public FridayResponse<IPage<AppUserResponse>> pagedList(@RequestBody IPage<AppUserResponse> page) {
        IPage<AppUser> userPage = new Page<>();
        BeanUtils.copyProperties(page,userPage);

        QueryWrapper<AppUser> queryWrapper=new QueryWrapper<>();
        userPage = appUserService.page(userPage,queryWrapper);

        BeanUtils.copyProperties(userPage,page);
        return FridayResponse.success(page);
    }

    @ApiOperation("根据账号获取用户")
    @GetMapping("{account}")
    public FridayResponse<AppUserResponse> query(@PathVariable String account) {
        var user=appUserService.findByAccount(account);
        AppUserResponse result=new AppUserResponse();
        BeanUtils.copyProperties(user,result);
        return FridayResponse.success(result);
    }

    @PostMapping
    @ApiOperation("添加用户")
    public FridayResponse<Boolean> create(@RequestBody AppUser user) {
        return FridayResponse.success(appUserService.save(user));
    }


}
