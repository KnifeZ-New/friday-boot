
package org.knifez.fridaybootapi.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppDictionaryQueryRequest;
import org.knifez.fridaybootadmin.entity.AppDictionary;
import org.knifez.fridaybootadmin.service.IAppDictionaryService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.dto.PageResult;
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

    @PostMapping("list")
    @Operation(summary = "分页列表")
    public PageResult<AppDictionary> pagedList(@RequestBody AppDictionaryQueryRequest queryRequest) {
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
