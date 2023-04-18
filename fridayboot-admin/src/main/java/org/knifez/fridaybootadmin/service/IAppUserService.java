package org.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.dto.AppUserModifyDTO;
import org.knifez.fridaybootadmin.dto.AppUserPagedQueryRequest;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootcore.dto.PagedResult;

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
    PagedResult<AppUser> listByPageQuery(AppUserPagedQueryRequest queryRequest);

    /**
     * 找到账户
     * 根据账户获取用户
     *
     * @param account 账户
     * @return User
     */
    AppUserDTO findByAccount(String account);

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
}
