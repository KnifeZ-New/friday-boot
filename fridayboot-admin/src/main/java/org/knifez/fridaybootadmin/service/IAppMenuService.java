package org.knifez.fridaybootadmin.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import org.knifez.fridaybootadmin.dto.AppMenuDTO;
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
     * 获取所有子节点id
     *
     * @param id id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> getChildrenIds(Integer id);

    /**
     * 按ID获取菜单
     *
     * @param ids ids
     * @return {@link List}<{@link AppMenu}>
     */
    List<AppMenu> getMenuByIds(List<Integer> ids);

    /**
     * 按权限获取菜单
     *
     * @param permissions 权限
     * @return {@link List}<{@link AppMenuDTO}>
     */
    List<AppMenuDTO> getMenuByPermissions(List<String> permissions, Boolean isSuper);
}
