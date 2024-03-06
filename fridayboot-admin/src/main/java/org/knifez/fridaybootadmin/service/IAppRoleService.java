package org.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppRolePagedRequest;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootcore.dto.PagedResult;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
public interface IAppRoleService extends IService<AppRole> {


    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    List<String> listRoleNameByUserId(long userId);

    /**
     * 通过用户id获取角色列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link AppRole}>
     */
    List<AppRole> listByUserId(long userId);

    /**
     * 列表页面查询
     *
     * @param queryRequest 查询请求
     * @return {@link List}<{@link AppRole}>
     */
    PagedResult<AppRole> listByPageQuery(AppRolePagedRequest queryRequest);

    /**
     * 根据角色保存权限
     *
     * @param permissions 菜单按钮Permission集合
     * @param roleName    角色
     */
    void savePermissionsByRole(List<String> permissions, String roleName);
}
