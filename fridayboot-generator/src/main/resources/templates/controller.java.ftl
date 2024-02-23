
package ${package.Controller};
<#if springdoc>
    import io.swagger.v3.oas.annotations.tags.Tag;
    import io.swagger.v3.oas.annotations.Operation;
<#elseif swagger>
    import io.swagger.annotations.Api;
    import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.web.bind.annotation.*;
<#if restControllerStyle>
    import org.knifez.fridaybootcore.annotation.ApiRestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>
import ${package.Parent}.dto.${entity}PagedRequest;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.knifez.fridaybootcore.dto.PagedResult;

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/

@Tag(name = "${table.comment!}管理")
<#if restControllerStyle>
@ApiRestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>


    private final ${table.serviceName} ${table.entityPath}Service;

    public ${table.controllerName}(${table.serviceName} ${table.entityPath}Service) {
    this.${table.entityPath}Service = ${table.entityPath}Service;
    }

    /**
    * 分页列表
    *
    * @param queryRequest 查询请求
    * @return {@link PagedResult}<{@link  ${table.entityName}}>
    */
    @PostMapping("list")
    @PreAuthorize("hasAuthority('${table.entityName}.pagedList')")
    @Operation(summary = "分页列表")
    public PagedResult<${table.entityName}> pagedList(@RequestBody  ${table.entityName}PagedRequest queryRequest) {
    return ${table.entityPath}Service.listByPage(queryRequest);
    }

    /**
    * 根据id获取${table.comment!}
    *
    * @param id id
    * @return {@link ${table.entityName}}
    */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('${table.entityName}.findById')")
    @Operation(summary = "根据id获取${table.comment!}")
    public ${table.entityName} findById(@PathVariable Long id) {
    return ${table.entityPath}Service.getById(id);
    }

    /**
    * 创建
    *
    * @param ${table.entityPath} ${table.comment!}
    * @return {@link Boolean}
    */
    @PostMapping
    @PreAuthorize("hasAuthority('${table.entityName}.create')")
    @Operation(summary = "添加")
    public Boolean create(@RequestBody ${table.entityName} ${table.entityPath}) {
    ${table.entityPath}.setId(null);
    return ${table.entityPath}Service.save(${table.entityPath});
    }

    /**
    * 更新
    *
    * @param ${table.entityPath} ${table.comment!}
    * @return {@link Boolean}
    */
    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('${table.entityName}.update')")
    @Operation(summary = "修改")
    public Boolean update(@RequestBody ${table.entityName} ${table.entityPath}) {
        return ${table.entityPath}Service.updateById(${table.entityPath});
    }

    /**
    * 删除
    *
    * @param id id
    * @return {@link Boolean}
    */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('${table.entityName}.delete')")
    @Operation(summary = "删除")
    public Boolean delete(@PathVariable Long id) {
    return ${table.entityPath}Service.removeById(id);
    }


    }
</#if>