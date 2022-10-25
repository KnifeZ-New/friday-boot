package com.knifez.fridaybootadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import com.knifez.fridaybootadmin.dto.AppMenuButtonResponse;
import com.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import com.knifez.fridaybootadmin.entity.AppDictionaryConfig;
import com.knifez.fridaybootadmin.entity.AppMenu;
import com.knifez.fridaybootadmin.mapper.AppMenuMapper;
import com.knifez.fridaybootadmin.service.IAppDictionaryConfigService;
import com.knifez.fridaybootadmin.service.IAppMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<AppMenuButtonResponse> getMenuButtons(AppMenuButtonQueryRequest queryRequest) {
        LambdaQueryWrapper<AppMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppMenu::getId, queryRequest.getMenuId());
        queryWrapper.like(StringUtils.hasText(queryRequest.getName()), AppMenu::getName, queryRequest.getName());
        queryWrapper.eq(queryRequest.getEnabled() != null, AppMenu::getEnabled, queryRequest.getEnabled());
        var list = baseMapper.selectList(queryWrapper);
        var buttons = BeanUtil.copyToList(list, AppMenuButtonResponse.class);
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
        var list = list(queryWrapper);
        return menuListToTree(list);
    }

    private List<Tree<Integer>> menuListToTree(List<AppMenu> list) {
        TreeNodeConfig treeConfig = new TreeNodeConfig();
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
            tree.putExtra("transition", node.getTransition());
            tree.putExtra("sort", node.getSort());
            tree.putExtra("permission", node.getPermission());
            tree.putExtra("routePath", node.getRoutePath());
            tree.putExtra("component", node.getComponent());
            tree.putExtra("createTime", node.getCreateTime());
        });
    }
}
