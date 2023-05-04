
package org.knifez.fridaybootadmin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.knifez.fridaybootadmin.dto.AppAuditLogPagedRequest;
import org.knifez.fridaybootadmin.entity.AppAuditLog;
import org.knifez.fridaybootadmin.service.IAppAuditLogService;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 审计日志 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2023-04-26
 */

@Tag(name = "审计日志管理")
@ApiRestController
@RequestMapping("/appAuditLog")
public class AppAuditLogController {


    private final IAppAuditLogService appAuditLogService;

    public AppAuditLogController(IAppAuditLogService appAuditLogService) {
        this.appAuditLogService = appAuditLogService;
    }

    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link  AppAuditLog}>
     */
    @PostMapping("list")
    @Operation(summary = "分页列表")
    public PagedResult<AppAuditLog> pagedList(@RequestBody AppAuditLogPagedRequest queryRequest) {
        return appAuditLogService.listByPage(queryRequest);
    }

    /**
     * 根据id获取审计日志
     *
     * @param id id
     * @return {@link AppAuditLog}
     */
    @GetMapping("{id}")
    @Operation(summary = "根据id获取审计日志")
    public AppAuditLog findById(@PathVariable Long id) {
        return appAuditLogService.getById(id);
    }

    /**
     * 创建
     *
     * @param appAuditLog 审计日志
     * @return {@link Boolean}
     */
    @PostMapping
    @Operation(summary = "添加")
    public Boolean create(@RequestBody AppAuditLog appAuditLog) {
        appAuditLog.setId(null);
        return appAuditLogService.save(appAuditLog);
    }

    /**
     * 更新
     *
     * @param appAuditLog 审计日志
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @Operation(summary = "修改")
    public Boolean update(@RequestBody AppAuditLog appAuditLog) {
        return appAuditLogService.updateById(appAuditLog);
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
        return appAuditLogService.removeById(id);
    }


}
