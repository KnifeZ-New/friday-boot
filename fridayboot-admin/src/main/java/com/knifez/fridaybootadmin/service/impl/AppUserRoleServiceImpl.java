package com.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridaybootadmin.entity.AppUserRole;
import com.knifez.fridaybootadmin.mapper.AppUserRoleMapper;
import com.knifez.fridaybootadmin.service.IAppUserRoleService;
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
        wrapper.eq("user_id", userId);
        return baseMapper.selectList(wrapper).stream().map(AppUserRole::getRoleId).toList();
    }
}
