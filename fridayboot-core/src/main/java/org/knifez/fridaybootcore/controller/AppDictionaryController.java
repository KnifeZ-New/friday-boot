
package org.knifez.fridaybootcore.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.dto.AppDictionaryPagedRequest;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.knifez.fridaybootcore.entity.AppDictionary;
import org.knifez.fridaybootcore.service.IAppDictionaryService;
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
    @Operation(summary = "分页列表")
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
    @Operation(summary = "根据id获取字典")
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
    @Operation(summary = "添加")
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
    @Operation(summary = "修改")
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
    @Operation(summary = "删除")
    public Boolean delete(@PathVariable Long id) {
        return appDictionaryService.removeById(id);
    }


}
