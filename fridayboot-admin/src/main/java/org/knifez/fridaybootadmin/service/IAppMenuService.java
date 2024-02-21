package org.knifez.fridaybootadmin.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import org.knifez.fridaybootadmin.dto.AppMenuDTO;
import org.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import org.knifez.fridaybootadmin.dto.AppMenuRouteDTO;
import org.knifez.fridaybootadmin.entity.AppMenu;
import org.knifez.fridaybootcore.dto.TextValuePair;

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
    List<TextValuePair> getMenuPermissions(List<String> ids);

    /**
     * 获取菜单按钮
     *
     * @param queryRequest 查询条件
     * @return 按钮集合
     */
    List<AppMenuDTO> getMenuButtons(AppMenuButtonQueryRequest queryRequest);

    /**
     * 获取菜单树
     *
     * @param queryRequest 查询条件
     * @return 树集合
     */
    List<Tree<Integer>> getTreeList(AppMenuQueryRequest queryRequest);

    /**
     * 获取菜单路由
     *
     * @return {@link List}<{@link AppMenuRouteDTO}>
     */
    List<AppMenuRouteDTO> getMenuRoutes();

    /**
     * 获取所有子节点id
     *
     * @param id id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> getChildrenIds(Integer id);

    Boolean updateChildrenLevel(Integer id, Integer parentId);
    /**
     * 按ID获取菜单
     *
     * @param ids ids
     * @return {@link List}<{@link AppMenu}>
     */
    List<AppMenu> getMenuByIds(List<Integer> ids);
}
