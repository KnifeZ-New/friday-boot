package org.knifez.fridaybootcore.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import org.knifez.fridaybootcore.tools.dto.OsInfo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class FridayUtil {


    public static <T> List<T> beansConvert(Collection<?> collection, Class<T> targetType){
        return BeanUtil.copyToList(collection,targetType);
    }


    public static <T> T beanConvert(Object source, Class<T> tClass, String... ignoreProperties) {
        return BeanUtil.copyProperties(source,tClass,ignoreProperties);
    }


    public static <T, E> List<Tree<E>> buildTree(List<T> list, E rootId, TreeNodeConfig treeNodeConfig, NodeParser<T, E> nodeParser) {
        return TreeUtil.build(list,rootId,treeNodeConfig,nodeParser);
    }


    public static String toJsonStr(Object obj) {
        return JSONUtil.toJsonStr(obj);
    }

    public static <T> boolean isEmpty(T[] array) {
        return ArrayUtil.isEmpty(array);
    }

    public static Duration betweenTimes(LocalDateTime startTimeInclude, LocalDateTime endTimeExclude) {
        return LocalDateTimeUtil.between(startTimeInclude, endTimeExclude);
    }

    public static OsInfo osInfo(){
        return (OsInfo) SystemUtil.getOsInfo();
    }
}
