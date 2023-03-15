package org.knifez.fridaybootadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.knifez.fridaybootadmin.common.MenuTypeEnum;
import org.knifez.fridaybootadmin.dto.*;
import org.knifez.fridaybootadmin.entity.AppDictionaryConfig;
import org.knifez.fridaybootadmin.entity.AppMenu;
import org.knifez.fridaybootadmin.mapper.AppMenuMapper;
import org.knifez.fridaybootadmin.service.IAppDictionaryConfigService;
import org.knifez.fridaybootadmin.service.IAppMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-11
 */
@Service
public class AppMenuServiceImpl extends ServiceImpl<AppMenuMapper, AppMenu> implements IAppMenuService {

    private final IAppDictionaryConfigService dictionaryConfigService;

    public AppMenuServiceImpl(IAppDictionaryConfigService dictionaryConfigService) {
        this.dictionaryConfigService = dictionaryConfigService;
    }

    /**
     * 获取菜单权限
     *
     * @param ids 菜单id集合
     * @return 菜单权限集合
     */
    @Override
    public List<String> getMenuPermissions(List<String> ids) {
        return listByIds(ids).stream().map(AppMenu::getPermission).filter(Objects::nonNull).toList();
    }

    /**
     * 获取菜单按钮
     *
     * @param queryRequest 查询条件
     * @return 按钮集合
     */
    @Override
    public List<AppMenuDTO> getMenuButtons(AppMenuButtonQueryRequest queryRequest) {
        LambdaQueryWrapper<AppMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppMenu::getParentId, queryRequest.getMenuId());
        queryWrapper.eq(AppMenu::getType, 2);
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), AppMenu::getName, queryRequest.getName());
        queryWrapper.eq(queryRequest.getEnabled() != null, AppMenu::getEnabled, queryRequest.getEnabled());
        var list = baseMapper.selectList(queryWrapper);
        var result = BeanUtil.copyToList(list, AppMenuDTO.class);
        return bindButtonTageColor(result);
    }

    private List<AppMenuDTO> bindButtonTageColor(List<AppMenuDTO> buttons) {
        var systemButtons = dictionaryConfigService.listByDictCode("system_button");
        for (var button : buttons) {
            var labelLevel = systemButtons.stream().filter(x -> Objects.equals(x.getValue(), button.getRoute())).map(AppDictionaryConfig::getLabelLevel).findFirst();
            if (labelLevel.isPresent()) {
                button.setTagColor(labelLevel.get());
            } else {
                button.setTagColor("primary");
            }
        }
        return buttons;
    }

    /**
     * 获取菜单树
     *
     * @param queryRequest 查询条件
     * @return 树集合
     */
    @Override
    public List<Tree<Integer>> getTreeList(AppMenuQueryRequest queryRequest) {
        QueryWrapper<AppMenu> queryWrapper = new QueryWrapper<>();
        if (Boolean.FALSE.equals(queryRequest.getIncludeButton())) {
            queryWrapper.lt("type", 2);
        }
        queryWrapper.eq(queryRequest.getEnabled() != null, "is_enabled", queryRequest.getEnabled());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), "name", queryRequest.getName());
        List<AppMenu> list = list(queryWrapper);
        var result = BeanUtil.copyToList(list, AppMenuDTO.class);
        if (StringUtils.hasText(queryRequest.getName())) {
            result = mixWithParentNodes(result);
        }
        //重新加载菜单按钮
        if (Boolean.TRUE.equals(queryRequest.getIncludeButton())) {
            var ids = list.stream().map(AppMenu::getId).toList();
            QueryWrapper<AppMenu> buttonWrapper = new QueryWrapper<>();
            buttonWrapper.in("parent_id", ids);
            buttonWrapper.eq("type", 2);
            List<AppMenu> buttons = list(buttonWrapper);
            var buttonList = bindButtonTageColor(BeanUtil.copyToList(buttons, AppMenuDTO.class));
            List<AppMenuDTO> mixdList = new ArrayList<>();
            mixdList.addAll(BeanUtil.copyToList(list, AppMenuDTO.class));
            mixdList.addAll(buttonList);
            result = mixdList;
        }
        return menuListToTree(result);
    }

    /**
     * 获取菜单路由
     *
     * @return {@link List}<{@link AppMenuRouteDTO}>
     */
    @Override
    public List<AppMenuRouteDTO> getMenuRoutes() {
        // 获取当前登录用户权限，
        LambdaQueryWrapper<AppMenu> queryWrapper = new LambdaQueryWrapper<>();
        // 启用
        queryWrapper.eq(AppMenu::getEnabled, true);
        // 不包含按钮
        queryWrapper.lt(AppMenu::getType, MenuTypeEnum.BUTTON.getValue());
        queryWrapper.orderByAsc(AppMenu::getSort);
        // 所有菜单
        var list = list(queryWrapper);
        List<AppMenuRouteDTO> menuRoutes = new ArrayList<>();
        for (var menu : list) {
            if (menu.getParentId() == null || menu.getParentId().equals(0)) {
                var menuRoute = findMenuRouteChildren(list, menu);
                menuRoutes.add(menuRoute);
            }
        }
        return menuRoutes;
    }

    private AppMenuRouteDTO convertToMenuRoute(AppMenu menu) {
        var route = new AppMenuRouteDTO();
        route.setPath(menu.getRoutePath());
        route.setName(menu.getRoute());
        route.setComponent(menu.getComponent());
        RouteMeta meta = new RouteMeta();
        meta.setTitle(menu.getName());
        meta.setIcon(menu.getIcon());
        meta.setOrderNo(menu.getSort());
        // 是否隐藏菜单
        meta.setHideMenu(!menu.getVisible());
        // 是否启用缓存
        meta.setIgnoreKeepAlive(menu.getKeepAlive());
        // 权限
        meta.setGrantedPolicy(menu.getPermission());
        meta.setIgnoreAuth(!StringUtils.hasText(menu.getPermission()));
        route.setMeta(meta);
        route.setChildren(new ArrayList<>());
        return route;
    }

    private AppMenuRouteDTO findMenuRouteChildren(List<AppMenu> menus, AppMenu node) {
        var menuRoute = convertToMenuRoute(node);
        for (var menu : menus) {
            if (menu.getParentId() != null && node.getId().equals(menu.getParentId())) {
                menuRoute.getChildren().add(findMenuRouteChildren(menus, menu));
            }
        }
        return menuRoute;
    }

    private List<AppMenuDTO> mixWithParentNodes(List<AppMenuDTO> list) {
        List<AppMenuDTO> mixdList = new ArrayList<>();
        for (var item : list) {
            if (item.getParentId() != null) {
                List<AppMenu> parentNodes = new ArrayList<>();
                parentNodes.add(item);
                while (item.getParentId() != null) {
                    var tmpItem = getById(item.getParentId());
                    parentNodes.add(tmpItem);
                }
                mixdList.addAll(BeanUtil.copyToList(parentNodes, AppMenuDTO.class));
            } else {
                mixdList.add(item);
            }
        }
        return mixdList.stream().distinct().toList();
    }

    @Override
    public List<Integer> getChildrenIds(Integer id) {
        LambdaQueryWrapper<AppMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(AppMenu::getId);
        queryWrapper.eq(AppMenu::getParentId, id);
        return baseMapper.selectList(queryWrapper).stream().map(AppMenu::getId).toList();
    }

    @Override
    public Boolean updateChildrenLevel(Integer id, Integer parentId) {
        LambdaQueryWrapper<AppMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppMenu::getParentId, id);
        var children = list(queryWrapper);
        for (var child : children) {
            child.setParentId(parentId);
        }
        return updateBatchById(children);
    }

    private List<Tree<Integer>> menuListToTree(List<AppMenuDTO> list) {
        TreeNodeConfig treeConfig = new TreeNodeConfig();
        treeConfig.setWeightKey("sort");
        return TreeUtil.build(list, null, treeConfig, (node, tree) -> {
            tree.setId(node.getId());
            tree.setName(node.getName());
            tree.setParentId(node.getParentId());
            tree.putExtra("name", node.getName());
            tree.putExtra("route", node.getRoute());
            tree.putExtra("type", node.getType());
            tree.putExtra("enabled", node.getEnabled());
            tree.putExtra("fixed", node.getFixed());
            tree.putExtra("hot", node.getHot());
            tree.putExtra("visible", node.getVisible());
            tree.putExtra("keepAlive", node.getKeepAlive());
            tree.putExtra("icon", node.getIcon());
            tree.putExtra("badge", node.getBadge());
            tree.putExtra("tagColor", node.getTagColor());
            tree.putExtra("transition", node.getTransition());
            tree.putExtra("sort", node.getSort());
            tree.putExtra("permission", node.getPermission());
            tree.putExtra("routePath", node.getRoutePath());
            tree.putExtra("component", node.getComponent());
            tree.putExtra("remark", node.getRemark());
            tree.putExtra("createTime", node.getCreateTime());
        });
    }
}
