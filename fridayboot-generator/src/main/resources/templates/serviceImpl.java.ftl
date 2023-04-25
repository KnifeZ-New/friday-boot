package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
<#if table.serviceInterface>
    import ${package.Service}.${table.serviceName};
</#if>
import ${superServiceImplClassPackage};
import ${package.Parent}.dto.${entity}PagedRequest;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>()<#if table.serviceInterface>, ${table.serviceName}</#if> {

    }
<#else>
    public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}><#if table.serviceInterface> implements ${table.serviceName}</#if> {
    /**
    * 列表页面查询
    *
    * @param queryRequest 查询请求
    * @return {@link PagedResult}<{@link ${entity}}>
    */
    @Override
    public PagedResult<${entity}> listByPage(${entity}PagedRequest queryRequest) {
    LambdaQueryWrapper<${entity}> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(StringUtils.hasText(queryRequest.getId()), ${entity}::getId, queryRequest.getId());
    IPage<${entity}> page = new Page<>();
    page.setCurrent(queryRequest.getPage());
    page.setSize(queryRequest.getPageSize());
    page = getBaseMapper().selectPage(page, queryWrapper);
    return PagedResult.builder(page.getRecords(), page.getTotal());
    }
    }
</#if>