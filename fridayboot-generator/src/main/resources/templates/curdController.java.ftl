
package ${package.Controller};

import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
<#if restControllerStyle>
import com.knifez.fridaybootcore.annotation.ApiRestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/

@Api(tags = "${table.comment!}管理")
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
    * 根据id获取${table.comment!}
    *
    * @param id id
    * @return {@link ${table.entityName}}
    */
    @GetMapping("{id}")
    @ApiOperation("根据id获取${table.comment!}")
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
    @ApiOperation("添加")
    public Boolean create(@RequestBody ${table.entityName} ${table.entityPath}) {
        return ${table.entityPath}Service.save(${table.entityPath});
    }

    /**
    * 更新
    *
    * @param ${table.entityPath} ${table.comment!}
    * @return {@link Boolean}
    */
    @PostMapping("{id}")
    @ApiOperation("修改")
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
    @ApiOperation("删除")
    public Boolean delete(@PathVariable Long id) {
        return ${table.entityPath}Service.removeById(id);
    }


    }
</#if>