package com.knifez.fridayboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridayboot.entity.AppUserRole;
import com.knifez.fridayboot.mapper.AppUserRoleMapper;
import com.knifez.fridayboot.service.IAppUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-07
 */
@Service
public class AppUserRoleServiceImpl extends ServiceImpl<AppUserRoleMapper, AppUserRole> implements IAppUserRoleService {

    /**
     * 获取角色id列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    @Override
    public List<Long> listRolesByUserId(Long userId) {
        var wrapper = new QueryWrapper<AppUserRole>();
        wrapper.eq("app_user_id", userId);
        return baseMapper.selectList(wrapper).stream().map(AppUserRole::getAppRoleId).toList();
    }
}
