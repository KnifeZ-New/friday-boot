package org.knifez.fridaybootadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.entity.AppUserRole;
import org.knifez.fridaybootadmin.mapper.AppUserRoleMapper;
import org.knifez.fridaybootadmin.service.IAppUserRoleService;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * @return {@link List}<{@link Integer}>
     */
    @Override
    public List<Integer> listRolesByUserId(Integer userId) {
        LambdaQueryWrapper<AppUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppUserRole::getUserId, userId);
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
    public boolean saveRolesByUserId(Integer userId, List<Integer> roles) {
        var wrapper = new LambdaQueryWrapper<AppUserRole>();
        wrapper.eq(AppUserRole::getUserId, userId);
        baseMapper.delete(wrapper);
        List<AppUserRole> list = new ArrayList<>();
        for (Integer role : roles) {
            AppUserRole userRole = new AppUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role);
            userRole.setCreateBy(JwtTokenUtils.getCurrentUser());
            userRole.setUpdateBy(JwtTokenUtils.getCurrentUser());
            list.add(userRole);
        }
        return saveBatch(list);
    }

}
