package org.knifez.fridaybootadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppUserPagedQueryRequest;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootcore.dto.PageResult;

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
     * @return {@link PageResult}<{@link AppUser}>
     */
    PageResult<AppUser> listByPageQuery(AppUserPagedQueryRequest queryRequest);

    /**
     * 找到账户
     * 根据账户获取用户
     *
     * @param account 账户
     * @return User
     */
    AppUser findByAccount(String account);

    /**
     * 保存用户数据和角色关系
     *
     * @param user     user对象
     * @param isUpdate 更新
     * @return 操作结果
     */
    Boolean saveWithUserRoles(AppUser user, boolean isUpdate);
}
