package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 审计日志
 * </p>
 *
 * @author KnifeZ
 * @since 2023-04-26
 */
@Getter
@Setter
@TableName("app_audit_log")
@Schema(name = "AppAuditLog", description = "$!{table.comment}")
public class AppAuditLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "服务名")
    private String serviceName;

    @Schema(description = "方法名")
    private String actionName;

    @Schema(description = "请求参数")
    private String parameters;

    @Schema(description = "执行时间")
    private LocalDateTime executionTime;

    @Schema(description = "执行耗时")
    private Long duration;

    @Schema(description = "异常信息")
    private String exception;

    @Schema(description = "状态码")
    private Integer resultCode;

    @Schema(description = "返回消息")
    private String resultMessage;

    private String ip;

    private String hostName;
}
