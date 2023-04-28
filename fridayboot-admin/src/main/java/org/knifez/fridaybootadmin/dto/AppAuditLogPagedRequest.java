package org.knifez.fridaybootadmin.dto;

    import io.swagger.v3.oas.annotations.media.Schema;
    import lombok.Getter;
    import lombok.Setter;
import org.knifez.fridaybootcore.dto.PagedRequest;
/**
* <p>
    * 审计日志 分页请求
    * </p>
*
* @author KnifeZ
* @since 2023-04-26
*/
@Getter
@Setter
@Schema(title = "AppAuditLogPagedRequest")
public class AppAuditLogPagedRequest extends PagedRequest {

@Schema(title = "ID")
private String id;
}