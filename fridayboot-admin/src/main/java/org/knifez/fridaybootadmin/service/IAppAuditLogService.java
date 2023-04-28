package org.knifez.fridaybootadmin.service;

import org.knifez.fridaybootadmin.entity.AppAuditLog;
import org.knifez.fridaybootadmin.dto.AppAuditLogPagedRequest;
import org.knifez.fridaybootcore.service.IMybatisPlusService;
import org.knifez.fridaybootcore.dto.PagedResult;

/**
 * <p>
 * 审计日志 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2023-04-26
 */
public interface IAppAuditLogService extends IMybatisPlusService<AppAuditLog> {
    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppAuditLog}>
     */
    PagedResult<AppAuditLog> listByPage(AppAuditLogPagedRequest queryRequest);
}
