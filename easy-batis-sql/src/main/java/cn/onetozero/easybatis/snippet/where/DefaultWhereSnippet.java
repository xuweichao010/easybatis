package cn.onetozero.easybatis.snippet.where;

import cn.onetozero.easy.parse.utils.AnnotationUtils;
import cn.onetozero.easy.parse.utils.StringUtils;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.annotaions.AnnotationAttributeProtocol;
import cn.onetozero.easybatis.annotaions.conditions.Equal;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.exceptions.CheckException;
import cn.onetozero.easybatis.exceptions.NotFoundException;
import cn.onetozero.easybatis.snippet.conditional.ConditionalSnippet;
import cn.onetozero.easybatis.snippet.conditional.MultiConditionalSnippet;
import cn.onetozero.easybatis.snippet.conditional.SingleConditionalSnippet;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.ConditionalRegistry;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/29 15:17
 */
public class DefaultWhereSnippet implements WhereSnippet {
    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultWhereSnippet(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String where(List<BatisColumnAttribute> batisColumnAttributes) {
        AtomicBoolean dynamic = new AtomicBoolean(false);
        ConditionalRegistry conditionalRegistry = this.sourceGenerator.getConditionalRegistry();
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        /**
         * 这里我们需要排除 order 、 group  page的情况
         *
         *  batisColumnAttributes 携带了很多属性 其中包括了 condition 、 order 、 group  page属性
         *  但是 ConditionalRegistry中只有 condition 片段的处理器   order 、 group  page是交给其他的片段来维护的
         */
        List<BatisColumnAttribute> conditionAttributes = batisColumnAttributes.stream()
                .filter(columnAttribute -> {
                    // 有条件注解的情况
                    Annotation annotation;
                    if ((annotation = conditionalRegistry.chooseAnnotation(columnAttribute)) != null) {
                        if (columnAttribute.isMethodDynamic() && !dynamic.get()) {
                            dynamic.set(true);
                        }
                        Object dynamicAttr = AnnotationUtils.getValue(annotation, AnnotationAttributeProtocol.DYNAMIC);
                        if (dynamicAttr instanceof Boolean && (boolean) dynamicAttr) {
                            dynamic.set(true);
                        }
                        return true;
                    }
                    // 这里是因为只有当带有 @Syntax的注解才会被放进去  SqlCommandType.SELECT 语句参数是没有注解默认为 Equal 更新语句为 SetParam
                    if (columnAttribute.getSqlCommandType() == SqlCommandType.SELECT &&
                            conditionalRegistry.chooseAnnotation(columnAttribute) == null &&
                            columnAttribute.annotations().isEmpty()) {
                        if (columnAttribute.isMethodDynamic() && !dynamic.get()) {
                            dynamic.set(true);
                        }
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
        if (conditionAttributes.isEmpty()) {
            return "";
        }
        // 区分 多条件和单条件的语句 比如 SQl语句中的 Between 属于多条件 ;
        Map<Class<? extends ConditionalSnippet>, List<BatisColumnAttribute>> conditionalSnippetMap =
                conditionAttributes.stream().collect(Collectors.groupingBy(columnAttribute -> {
                    Annotation conditionAnnotation = conditionalRegistry.chooseAnnotation(columnAttribute);
                    if (conditionAnnotation != null) {
                        Object value = AnnotationUtils.getValue(conditionAnnotation, AnnotationAttributeProtocol.OF);
                        return Objects.isNull(value) ? SingleConditionalSnippet.class : MultiConditionalSnippet.class;
                    } else {
                        return SingleConditionalSnippet.class;
                    }
                }));
        Map<String, BatisColumnAttribute> singleConditionalSnippetMap;
        if (conditionalSnippetMap.get(SingleConditionalSnippet.class) != null) {
            singleConditionalSnippetMap = conditionalSnippetMap.get(SingleConditionalSnippet.class)
                    .stream().collect(Collectors.toMap(batisPlaceholder::path, val -> val));
        } else {
            singleConditionalSnippetMap = new HashMap<>();
        }
        List<Condition> conditions = new ArrayList<>(conditionAttributes.size());
        if (conditionalSnippetMap.get(MultiConditionalSnippet.class) != null) {
            // 处理多条件片段
            List<Condition> multiConditionalSnippets =
                    conditionalSnippetMap.get(MultiConditionalSnippet.class).stream().map(fromAttribute -> {
                        Annotation annotation = conditionalRegistry.chooseAnnotation(fromAttribute);
                        ConditionalSnippet conditionalSnippet = conditionalRegistry.chooseSnippet(annotation.annotationType());
                        if (conditionalSnippet == null) {
                            throw new NotFoundException("没有找到需要的片段处理器");
                        }
                        if (conditionalSnippet instanceof MultiConditionalSnippet) {
                            Object value = AnnotationUtils.getValue(annotation, AnnotationAttributeProtocol.OF);
                            if (!StringUtils.hasText(value)) {
                                throw new CheckException(fromAttribute.getParameterName() + "中的" + annotation.annotationType() + "注解中的 of" + "属性无效");
                            }
                            String of = String.valueOf(value);
                            // 在多参数情况下 of 属性可能在同一层 需要进行路径匹配
                            if (fromAttribute.isMulti() && fromAttribute.getPath().length > 1) {
                                String[] ofPath = fromAttribute.getPath().clone();
                                ofPath[ofPath.length - 1] = of;
                                of = batisPlaceholder.join(ofPath);
                            }
                            BatisColumnAttribute ofAttribute = singleConditionalSnippetMap.remove(of);
                            if (ofAttribute == null) {
                                throw new CheckException("无法找到 " + annotation.annotationType() + "注解中的 of 属性");
                            }
                            MultiConditionalSnippet multiConditionalSnippet = (MultiConditionalSnippet) conditionalSnippet;
                            String snippet = multiConditionalSnippet.snippet(fromAttribute, ofAttribute);
                            return new Condition(fromAttribute.getIndex(), snippet);
                        } else {
                            throw new CheckException(fromAttribute.getParameterName() + "的片段处理器无效");
                        }
                    }).collect(Collectors.toList());
            conditions.addAll(multiConditionalSnippets);
        }


        // 处理单条件片段
        List<Condition> singleConditionalSnippets = singleConditionalSnippetMap.values().stream()
                .map(columnAttribute -> {
                    ConditionalSnippet conditionalSnippet;
                    Annotation annotation = conditionalRegistry.chooseAnnotation(columnAttribute);
                    if (annotation == null) {
                        conditionalSnippet = conditionalRegistry.chooseSnippet(Equal.class);
                    } else {
                        conditionalSnippet = conditionalRegistry.chooseSnippet(annotation.annotationType());
                        if (conditionalSnippet == null) {
                            throw new CheckException(annotation.annotationType() + "没有找到对应的Snippet");
                        }
                    }

                    if (conditionalSnippet instanceof SingleConditionalSnippet) {
                        SingleConditionalSnippet singleConditionalSnippet = (SingleConditionalSnippet) conditionalSnippet;
                        String snippet = singleConditionalSnippet.snippet(columnAttribute);
                        return new Condition(columnAttribute.getIndex(), snippet);
                    } else {
                        throw new CheckException(columnAttribute.getParameterName() + "的片段处理器无效");
                    }
                }).collect(Collectors.toList());
        conditions.addAll(singleConditionalSnippets);
        // 对条件排序
        String sqlConditions = conditions.stream().sorted().map(Condition::getConditionalSnippet)
                .collect(Collectors.joining(" "));
        return dynamic.get() ? MyBatisSnippetUtils.where(sqlConditions) : " WHERE " + tryDelAndPrefix(sqlConditions);
    }

    public String tryDelAndPrefix(String sql) {
        if (sql.trim().startsWith("AND")) {
            return sql.trim().substring(sql.indexOf("AND") + 4);
        } else {
            return sql;
        }
    }

    static class Condition implements Comparable<Condition> {
        private final int index;

        private final String conditionalSnippet;

        public Condition(int index, String conditionalSnippet) {
            this.index = index;
            this.conditionalSnippet = conditionalSnippet;
        }

        public int getIndex() {
            return index;
        }

        public String getConditionalSnippet() {
            return conditionalSnippet;
        }


        @Override
        public int compareTo(Condition o) {
            return this.getIndex() - o.getIndex();
        }
    }
}
