package com.xwc.open.easybatis.core.assistant;


import com.xwc.open.easybatis.core.EasyBatisEnvironment;
import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.support.TableMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  9:22
 * 业务：
 * 功能：
 */
@Slf4j
public class EasyBatisMapperAnnotationBuilder extends MapperAnnotationBuilder {

    private final Configuration configuration;
    private final MapperBuilderAssistant assistant;
    private final Class<?> type;
    private final TableMeta tableMetadata;
    private final com.xwc.open.easybatis.core.assistant.AnnotationAssistan annotationAssistan;
    private static Set<Class<? extends Annotation>> GENDERATE_ANNOTATION_TYPES = new HashSet<>();

    static {
        GENDERATE_ANNOTATION_TYPES.add(DeleteSql.class);
        GENDERATE_ANNOTATION_TYPES.add(InsertSql.class);
        GENDERATE_ANNOTATION_TYPES.add(UpdateSql.class);
        GENDERATE_ANNOTATION_TYPES.add(SelectSql.class);
    }

    public EasyBatisMapperAnnotationBuilder(Configuration configuration, Class<?> type, Class<?> entityClass, EasyBatisEnvironment environment) {
        super(configuration, type);
        String resource = type.getName().replace('.', '/') + ".java (best guess)";
        this.assistant = new MapperBuilderAssistant(configuration, resource);
        this.configuration = configuration;
        this.type = type;
        //TODO 自定义逻辑
        annotationAssistan = new AnnotationAssistan(environment);
        this.tableMetadata = annotationAssistan.parseEntityMate(entityClass);
    }

}
