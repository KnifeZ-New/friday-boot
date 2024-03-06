package org.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.dto.AppUserInfoDTO;
import org.knifez.fridaybootadmin.dto.AppUserModifyDTO;
import org.knifez.fridaybootadmin.dto.AppUserPagedRequest;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootcore.dto.PagedResult;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
public interface IAppUserService extends IService<AppUser> {


    /**
     * 分页列表查询
     *
     * @param queryRequest 查询请求
     * @return {@link PagedResult}<{@link AppUser}>
     */
    PagedResult<AppUser> listByPageQuery(AppUserPagedRequest queryRequest);

    /**
     * 找到账户
     * 根据账户获取用户
     *
     * @param account 账户
     * @return User
     */
    AppUserInfoDTO findByAccount(String account);

    AppUserDTO getUserDtoByAccountOrId(String account, long userId);

    /**
     * 账户是否存在
     *
     * @param account 账户
     * @return {@link Boolean}
     */
    Boolean accountExist(String account);

    /**
     * 保存用户数据和角色关系
     *
     * @param user  user对象
     * @param isNew 是否新增
     * @return 操作结果
     */
    Boolean saveWithUserRoles(AppUserModifyDTO user, boolean isNew);

    /**
     * 检查原始密码
     *
     * @param id       id
     * @param password 密码
     * @return {@link Boolean}
     */
    Boolean checkOriginPassword(Long id, String password);

    /**
     * 更新密码
     *
     * @param id       id
     * @param password 密码
     * @return {@link Boolean}
     */
    Boolean updatePassword(Long id, String password);

    /**
     * 获取用户组织id
     *
     * @param account 账户
     * @return {@code Long}
     */
    Integer getUserOrgId(String account);


    /**
     * 保存用户角色
     *
     * @param userId 用户id
     * @param roles  角色列表
     * @return 操作结果
     */
    boolean saveRolesByUserId(Long userId, List<Long> roles);
}
