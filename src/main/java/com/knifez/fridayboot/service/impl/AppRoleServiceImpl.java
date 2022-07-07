package com.knifez.fridayboot.service.impl;

import com.knifez.fridayboot.entity.AppRole;
import com.knifez.fridayboot.mapper.AppRoleMapper;
import com.knifez.fridayboot.service.IAppRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knifez.fridayboot.service.IAppUserRoleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
@Service
public class AppRoleServiceImpl extends ServiceImpl<AppRoleMapper, AppRole> implements IAppRoleService {

    private final IAppUserRoleService userRoleService;

    public AppRoleServiceImpl(IAppUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    @Override
    public List<AppRole> listByUserId(long userId) {
        var ids = userRoleService.listRolesByUserId(userId);
        if(ids.isEmpty()){
            return Collections.emptyList();
        }
        return baseMapper.selectBatchIds(ids);
    }
}
