package org.knifez.fridaybootadmin.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import org.knifez.fridaybootadmin.dto.AppMenuButtonResponse;
import org.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import org.knifez.fridaybootadmin.entity.AppMenu;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-11
 */
public interface IAppMenuService extends IService<AppMenu> {
    /**
     * 获取菜单权限
     *
     * @param ids 菜单id集合
     * @return 菜单权限集合
     */
    List<String> getMenuPermissions(List<String> ids);

    /**
     * 获取菜单按钮
     *
     * @param queryRequest 查询条件
     * @return 按钮集合
     */
    List<AppMenuButtonResponse> getMenuButtons(AppMenuButtonQueryRequest queryRequest);

    /**
     * 获取菜单树
     *
     * @param queryRequest 查询条件
     * @return 树集合
     */
    List<Tree<Integer>> getTreeList(AppMenuQueryRequest queryRequest);

    /**
     * 获取所有子节点id
     *
     * @param id id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> getChildrenIds(Integer id);
}
