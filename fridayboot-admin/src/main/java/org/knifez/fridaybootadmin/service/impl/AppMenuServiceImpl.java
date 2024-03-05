package org.knifez.fridaybootadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.knifez.fridaybootadmin.dto.AppMenuButtonQueryRequest;
import org.knifez.fridaybootadmin.dto.AppMenuDTO;
import org.knifez.fridaybootadmin.dto.AppMenuQueryRequest;
import org.knifez.fridaybootadmin.entity.AppDictionaryConfig;
import org.knifez.fridaybootadmin.entity.AppMenu;
import org.knifez.fridaybootadmin.common.enums.MenuTypeEnum;
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
@RequiredArgsConstructor
public class AppMenuServiceImpl extends ServiceImpl<AppMenuMapper, AppMenu> implements IAppMenuService {

    private final IAppDictionaryConfigService dictionaryConfigService;


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
        queryWrapper.eq(AppMenu::getType, MenuTypeEnum.MENU_BUTTON.getValue());
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
            buttonWrapper.eq("type", MenuTypeEnum.MENU_BUTTON.getValue());
            List<AppMenu> buttons = list(buttonWrapper);
            var buttonList = bindButtonTageColor(BeanUtil.copyToList(buttons, AppMenuDTO.class));
            List<AppMenuDTO> mixdList = new ArrayList<>();
            mixdList.addAll(BeanUtil.copyToList(list, AppMenuDTO.class));
            mixdList.addAll(buttonList);
            result = mixdList;
        }
        return menuListToTree(result);
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
            tree.putExtra("redirect", node.getRedirect());
            tree.putExtra("routePath", node.getRoutePath());
            tree.putExtra("component", node.getComponent());
            tree.putExtra("remark", node.getRemark());
            tree.putExtra("createTime", node.getCreateTime());
        });
    }

    /**
     * 按ID获取菜单
     *
     * @param ids ids
     * @return {@link List}<{@link AppMenu}>
     */
    @Override
    public List<AppMenu> getMenuByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AppMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AppMenu::getId, ids);
        return list(queryWrapper);
    }

    @Override
    public List<AppMenuDTO> getMenuByPermissions(List<String> permissions, Boolean isSuper) {
        if (permissions.isEmpty() && !isSuper) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AppMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!isSuper, AppMenu::getPermission, permissions);
        var list = list(queryWrapper);

        //公共菜单
        LambdaQueryWrapper<AppMenu> queryNoAuthWrapper = new LambdaQueryWrapper<>();
        queryNoAuthWrapper.eq(AppMenu::getPermission, "");
        var noAuthMenu = list(queryNoAuthWrapper);
        for (var m : noAuthMenu) {
            if (list.stream().noneMatch(x -> Objects.equals(x.getId(), m.getId()))) {
                list.add(m);
            }
        }

        //添加目录
        LambdaQueryWrapper<AppMenu> queryType0Wrapper = new LambdaQueryWrapper<>();
        queryType0Wrapper.eq(AppMenu::getType, MenuTypeEnum.MENU_FOLDER.getValue());
        var catalogues = list(queryType0Wrapper);
        for (var ca : catalogues) {
            if (list.stream().anyMatch(x -> Objects.equals(x.getParentId(), ca.getId())) && list.stream().noneMatch(x -> Objects.equals(x.getId(), ca.getId()))) {
                list.add(ca);
            }
        }

        return BeanUtil.copyToList(list, AppMenuDTO.class);
    }
}
