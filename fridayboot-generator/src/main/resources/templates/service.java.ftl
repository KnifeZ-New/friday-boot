package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Parent}.dto.${entity}PagedRequest;
import org.knifez.fridaybootcore.service.IMybatisPlusService;
import org.knifez.fridaybootcore.dto.PagedResult;

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
    * @return {@link PagedResult}<{@link ${entity}}>
    */
    PagedResult<${entity}> listByPage(${entity}PagedRequest queryRequest);
    }
</#if>