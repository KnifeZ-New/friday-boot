package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.knifez.fridaybootadmin.service.IAppAuditLogService;
import org.knifez.fridaybootadmin.entity.AppAuditLog;
import org.knifez.fridaybootadmin.mapper.AppAuditLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.dto.AppAuditLogPagedRequest;
import org.knifez.fridaybootcore.dto.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 审计日志 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2023-04-26
 */
@Service
public class AppAuditLogServiceImpl extends ServiceImpl<AppAuditLogMapper, AppAuditLog> implements IAppAuditLogService {
    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppAuditLog}>
     */
    @Override
    public PagedResult<AppAuditLog> listByPage(AppAuditLogPagedRequest queryRequest) {
        LambdaQueryWrapper<AppAuditLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(queryRequest.getId()), AppAuditLog::getId, queryRequest.getId());
        IPage<AppAuditLog> page = new Page<>();
        page.setCurrent(queryRequest.getPage());
        page.setSize(queryRequest.getPageSize());
        page = getBaseMapper().selectPage(page, queryWrapper);
        return PagedResult.builder(page.getRecords(), page.getTotal());
    }
}
