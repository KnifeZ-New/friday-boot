package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.entity.AppUserRole;
import org.knifez.fridaybootadmin.mapper.AppUserRoleMapper;
import org.knifez.fridaybootadmin.service.IAppUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
@author KnifeZ
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

    /**
     * 保存用户角色
     *
     * @param userId 用户id
     * @param roles  角色列表
     * @return 操作结果
     */
    @Override
    public boolean saveRolesByUserId(Long userId, List<Long> roles) {
        var wrapper = new LambdaQueryWrapper<AppUserRole>();
        wrapper.eq(AppUserRole::getUserId, userId);
        baseMapper.delete(wrapper);
        List<AppUserRole> list = new ArrayList<>();
        for (Long role : roles) {
            AppUserRole userRole = new AppUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role);
            list.add(userRole);
        }
        return saveBatch(list);
    }
}
