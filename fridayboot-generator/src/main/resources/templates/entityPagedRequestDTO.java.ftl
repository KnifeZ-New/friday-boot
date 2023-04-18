package ${package.Parent}.dto;

<#if springdoc>
    import io.swagger.v3.oas.annotations.media.Schema;
<#elseif swagger>
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
    import lombok.Getter;
    import lombok.Setter;
</#if>
import org.knifez.fridaybootcore.dto.PagedRequest;
/**
* <p>
    * ${table.comment!} 分页请求
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Getter
@Setter
@Schema(title = "${entity}PagedRequest")
public class ${entity}PagedRequest extends PagedRequest {

@Schema(title = "ID")
private String id;
}