
package org.knifez.fridaybootapi.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.entity.AppPermissionGrant;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 授权记录 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-25
 */
@Tag(name = "授权记录管理")
@ApiRestController
@RequestMapping("/permission-grant")
public class AppPermissionGrantController {


    private final IAppPermissionGrantService appPermissionGrantService;

    public AppPermissionGrantController(IAppPermissionGrantService appPermissionGrantService) {
        this.appPermissionGrantService = appPermissionGrantService;
    }


    /**
     * 根据id获取授权记录
     *
     * @param id id
     * @return {@link AppPermissionGrant}
     */
    @GetMapping("{id}")
    @Operation(summary = "根据id获取授权记录")
    public AppPermissionGrant findById(@PathVariable Long id) {
        return appPermissionGrantService.getById(id);
    }

    /**
     * 创建
     *
     * @param appPermissionGrant 授权记录
     * @return {@link Boolean}
     */
    @PostMapping
    @Operation(summary = "新增")
    public Boolean create(@RequestBody AppPermissionGrant appPermissionGrant) {
        appPermissionGrant.setId(null);
        return appPermissionGrantService.save(appPermissionGrant);
    }

    /**
     * 更新
     *
     * @param appPermissionGrant 授权记录
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @Operation(summary = "修改")
    public Boolean update(@RequestBody AppPermissionGrant appPermissionGrant) {
        return appPermissionGrantService.updateById(appPermissionGrant);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    public Boolean delete(@PathVariable Long id) {
        return appPermissionGrantService.removeById(id);
    }


}
