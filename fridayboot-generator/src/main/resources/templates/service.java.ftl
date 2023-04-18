package ${package.Service};

import org.knifez.fridaybootcore.service.IMybatisPlusService;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : IMybatisPlusService<${entity}>
<#else>
    public interface ${table.serviceName} extends IMybatisPlusService<${entity}> {
    /**
    * 列表页面查询
    *
    * @param queryRequest 查询请求
    * @return {@link List}<{@link ${entity}}>
    */
    PageResult<${entity}> listByPage(${entity}PagedRequest queryRequest);
    }
</#if>