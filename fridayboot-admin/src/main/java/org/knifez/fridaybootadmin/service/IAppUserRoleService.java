package org.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.entity.AppUserRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-07
 */
public interface IAppUserRoleService extends IService<AppUserRole> {
    /**
     * 获取角色id列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> listRolesByUserId(Integer userId);


    /**
     * 保存用户角色
     *
     * @param userId 用户id
     * @param roles  角色列表
     * @return 操作结果
     */
    boolean saveRolesByUserId(Integer userId, List<Integer> roles);
}
