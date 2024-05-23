package org.knifez.fridaybootcore.utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.dto.TextValuePair;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author KnifeZ
 */
@Slf4j
public class AnnotationUtils {
    private static final String VALUE = "value";

    private AnnotationUtils() {
        throw new IllegalStateException("AnnotationUtils class");
    }

    public static <T> List<String> getAllUrlsByAnnotations(ResourcePatternResolver resourcePatternResolver, String classpath, Class<T> annotationClass) throws Exception {
        Resource[] resources = resourcePatternResolver.getResources(classpath);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory();
        List<String> urls = new ArrayList<>();
        for (Resource r : resources) {
            MetadataReader reader = metaReader.getMetadataReader(r);
            resolveClass(reader, urls, annotationClass);
        }
        return urls;
    }

    private static <T> void resolveClass(MetadataReader reader, List<String> maps, Class<T> annotationClass) {
        String tagAnnotationClassCanonicalName = annotationClass.getCanonicalName();
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(RequestMapping.class.getCanonicalName());
        String pathParent = AppConstants.API_PREFIX;
        if (annotationAttributes != null) {
            String[] pathParents = (String[]) annotationAttributes.get(VALUE);
            if (pathParents.length > 0) {
                pathParent += pathParents[0];
            }
            //获取所有添加了此注解Permission的方法
            Set<MethodMetadata> annotatedMethods = annotationMetadata.getAnnotatedMethods(tagAnnotationClassCanonicalName);
            for (var annotatedMethod : annotatedMethods) {
                //获取当前方法中要扫描注解的属性
                Map<String, Object> targetAttr = annotatedMethod.getAnnotationAttributes(tagAnnotationClassCanonicalName);
                //获取方法GetMapping、PostMapping等注解的属性
                Map<String, Object> mappingAttr = getPathByMethod(annotatedMethod);
                if (mappingAttr == null) {
                    continue;
                }
                String[] childPath = (String[]) mappingAttr.get(VALUE);
                if (targetAttr == null || childPath == null || childPath.length == 0) {
                    continue;
                }
                String path = pathParent + childPath[0];
                if (!pathParent.endsWith("/") && !childPath[0].startsWith("/")) {
                    path = pathParent + "/" + childPath[0];
                }
                if (maps.contains(path)) {
                    log.warn("path关系映射重复");
                    continue;
                }
                maps.add(path);
            }
        }

    }

    private static Map<String, Object> getPathByMethod(MethodMetadata annotatedMethod) {
        Map<String, Object> annotationAttributes = annotatedMethod.getAnnotationAttributes(GetMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            return annotationAttributes;
        }
        annotationAttributes = annotatedMethod.getAnnotationAttributes(PostMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            return annotationAttributes;
        }

        annotationAttributes = annotatedMethod.getAnnotationAttributes(DeleteMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            return annotationAttributes;
        }

        annotationAttributes = annotatedMethod.getAnnotationAttributes(PutMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            return annotationAttributes;
        }
        annotationAttributes = annotatedMethod.getAnnotationAttributes(RequestMapping.class.getCanonicalName());
        return annotationAttributes;
    }


    /**
     * 获取系统所有已配置的接口权限
     *
     * @param resourcePatternResolver 资源模式解析程序
     * @param classpath               classpath
     * @param annotationClass         权限注解
     * @return {@link List}<{@link TextValuePair}> 权限列表
     */
    public static <T> List<TextValuePair> getAuthorityList(ResourcePatternResolver resourcePatternResolver, String classpath, Class<T> annotationClass) throws Exception {
        Resource[] resources = resourcePatternResolver.getResources(classpath);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory();
        List<TextValuePair> authorityList = new ArrayList<>();
        for (Resource r : resources) {
            MetadataReader reader = metaReader.getMetadataReader(r);
            resolveClassWithOutAllowAnnotation(reader, authorityList, annotationClass);
        }
        authorityList = authorityList.stream().distinct().toList();
        return authorityList;
    }

    private static <T> void resolveClassWithOutAllowAnnotation(MetadataReader reader, List<TextValuePair> maps, Class<T> annotationClass) {
        String tagAnnotationClassCanonicalName = annotationClass.getCanonicalName();
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(RequestMapping.class.getCanonicalName());
        if (annotationAttributes != null) {
            //获取所有添加了此注解的方法
            Set<MethodMetadata> annotatedMethods = annotationMetadata.getAnnotatedMethods(tagAnnotationClassCanonicalName);
            var tag = annotationMetadata.getAnnotationAttributes(Tag.class.getCanonicalName());
            if (tag == null) {
                return;
            }
            for (var annotatedMethod : annotatedMethods) {
                //获取当前方法中要扫描注解的属性
                Map<String, Object> targetAttr = annotatedMethod.getAnnotationAttributes(tagAnnotationClassCanonicalName);
                //获取方法GetMapping、PostMapping等注解的属性
                Map<String, Object> summary = annotatedMethod.getAnnotationAttributes(Operation.class.getCanonicalName());
                if (targetAttr == null || summary == null) {
                    continue;
                }
                var authority = (String[]) targetAttr.get(VALUE);
                for (var auth : authority) {
                    maps.add(TextValuePair.from(tag.get("name").toString() + "/" + summary.get("summary").toString(),
                            auth));
                }
            }
        }

    }

}
