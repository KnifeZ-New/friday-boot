package org.knifez.fridaybootcore.utils;

import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author KnifeZ
 */
@Slf4j
public class AnnotationUtils {
    private AnnotationUtils() {
        throw new IllegalStateException("AnnotationUtils class");
    }

    private static final String VALUE = "value";

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
                    log.error("path关系映射重复");
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
}
