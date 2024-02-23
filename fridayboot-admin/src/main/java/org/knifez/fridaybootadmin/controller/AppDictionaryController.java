package org.knifez.fridaybootadmin.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppDictionaryPagedRequest;
import org.knifez.fridaybootadmin.entity.AppDictionary;
import org.knifez.fridaybootadmin.service.IAppDictionaryService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
@AllowAuthenticated
@Tag(name = "字典管理")
@ApiRestController
@RequestMapping("/dictionary")
public class AppDictionaryController {


    private final IAppDictionaryService appDictionaryService;

    public AppDictionaryController(IAppDictionaryService appDictionaryService) {
        this.appDictionaryService = appDictionaryService;
    }

    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppDictionary}>
     */
    @PostMapping("list")
    @PreAuthorize("hasAuthority('dict.pagedList')")
    @Operation(summary = "分页列表", description = "dict.pagedList")
    public PagedResult<AppDictionary> pagedList(@RequestBody AppDictionaryPagedRequest queryRequest) {
        return appDictionaryService.listByPageQuery(queryRequest);
    }

    /**
     * 根据id获取字典
     *
     * @param id id
     * @return {@link AppDictionary}
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('dict.findById')")
    @Operation(summary = "根据id获取字典", description = "dict.findById")
    public AppDictionary findById(@PathVariable Long id) {
        return appDictionaryService.getById(id);
    }

    /**
     * 创建
     *
     * @param appDictionary 字典
     * @return {@link Boolean}
     */
    @PostMapping
    @PreAuthorize("hasAuthority('dict.create')")
    @Operation(summary = "添加", description = "dict.create")
    public Boolean create(@RequestBody AppDictionary appDictionary) {
        appDictionary.setId(null);
        return appDictionaryService.save(appDictionary);
    }

    /**
     * 更新
     *
     * @param appDictionary 字典
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('dict.update')")
    @Operation(summary = "修改", description = "dict.update")
    public Boolean update(@RequestBody AppDictionary appDictionary) {
        return appDictionaryService.updateById(appDictionary);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('dict.delete')")
    @Operation(summary = "删除", description = "dict.delete")
    public Boolean delete(@PathVariable Long id) {
        return appDictionaryService.removeById(id);
    }

}
