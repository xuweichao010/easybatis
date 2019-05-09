package com.xwc.esbatis.assistant;//TODO 被AnnotationAssistan 代替

//package com.xwc.esbatis.assistant;
//
//import com.xwc.esbatis.anno.Count;
//import com.xwc.esbatis.anno.Distinct;
//import com.xwc.esbatis.anno.enums.KeyEnum;
//import com.xwc.esbatis.anno.table.Ignore;
//import com.xwc.esbatis.anno.table.PrimaryKey;
//import com.xwc.esbatis.anno.table.Table;
//import com.xwc.esbatis.interfaces.SqlAssistant;
//import com.xwc.esbatis.meta.ColumMate;
//import com.xwc.esbatis.meta.EntityMate;
//import com.xwc.esbatis.meta.FilterColumMate;
//import com.xwc.esbatis.meta.QueryMate;
//import org.apache.ibatis.binding.BindingException;
//import org.apache.ibatis.reflection.ParamNameUtil;
//import org.apache.ibatis.session.Configuration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotationUtils;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.util.List;
//
///**
// * 创建人：徐卫超
// * 创建时间：2019/4/24  11:09
// * 业务：
// * 功能：
// */
//
//public class AnalysisAssistant {
//
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisAssistant.class);
//
//
//    private final AnnotationAssistan annotianAssistan;
//    private final SqlAssistant sqlAssistant;
//
//    public AnalysisAssistant(Configuration configuration) {
//        this.annotianAssistan = new AnnotationAssistan(configuration);
//        this.sqlAssistant = new MySQLAssistant();
//    }
//
//    /**
//     * 构建一个根据主键查询的sql  主键依据@PrimaryKey 来声明的
//     */
//    public String parseSelectById(EntityMate entityMate) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<script> ").append(buildSelectSyntax(entityMate)).append(buildWherePrimeKey(entityMate)).append(" </script>");
//        return sb.toString();
//    }
//
//    /**
//     * 构建一个标准的Update语句
//     */
//    public StringBuilder buildUpdate(EntityMate meta) {
//        StringBuilder updateField = new StringBuilder();
//        meta.getUpdateColum().forEach(item -> {
//            updateField.append(", ").append(item.getColunm()).append(" = ").append(item.getBatisField());
//
//        });
//        StringBuilder sb = new StringBuilder();
//        sb.append(" UPDATE ").append(meta.getTableName()).append(" SET ").append(updateField.substring(1)).append(buildWherePrimeKey(meta));
//        return sb;
//    }
//
//    public String parseUpdate(QueryMate mate, EntityMate entityMate) {
//        List<FilterColumMate> queryFilter = mate.getQueryFilter();
//        List<FilterColumMate> setList = mate.getSet();
//        if (setList.isEmpty()) throw new BindingException("Set filed not find ");
//        StringBuilder updateField = new StringBuilder();
//        setList.forEach(item -> {
//            updateField.append(", ").append(item.getColunm()).append(" = ").append(item.getBatisField());
//
//        });
//        StringBuilder sb = new StringBuilder();
//        sb.append(" <script> UPDATE ").append(entityMate.getTableName()).append(" SET ").append(updateField.substring(1)).append(buildSelectParam(mate)).append(" </script>");
//        return sb.toString();
//    }
//
//    /**
//     * 构建一个插入语句sql
//     */
//    public String parseInsert(EntityMate entityMate) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<script> ").append(buildInsertSyntax(entityMate)).append(buildInsertColunm(entityMate)).append(" </script>");
//        return sb.toString();
//    }
//
//    public String parseBatchInsert(EntityMate entityMate) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<script> ").append(buildInsertSyntax(entityMate)).append(buildBatchInsertColunm(entityMate)).append(" </script>");
//        return sb.toString();
//    }
//
//    /**
//     * 构建一个根据主键删除的sql  主键依据@PrimaryKey 来声明的
//     */
//    public String parseDelete(EntityMate mate) {
//        return new StringBuilder("<script>  DELETE FROM  ").append(mate.getTableName()).append(" WHERE ").append(mate.getKey().getBatisField()).append(" = #{id}").append(" </script>").toString();
//    }
//
//    /**
//     * 构建一个动态sql
//     *
//     * @param entityMate 返回实体信息
//     * @param queryMate  查询的条件
//     * @param colums     自定义查询字段
//     * @param count      统计
//     * @param distinct   去重
//     * @param isProvider 是否构建动态sql
//     * @return
//     */
//    public String parseSelectSql(EntityMate entityMate, QueryMate queryMate, Object colums, Count count, Distinct distinct, Boolean isProvider) {
//        String fields = "";
//        if (colums != null) fields = colums.toString().trim();
//        if (count != null) fields = count.colums().trim();
//        StringBuilder selectColum = new StringBuilder(fields.isEmpty() ? buildSelectColunm(entityMate) : fields);
//        if (distinct != null && !fields.equals("*")) {
//            selectColum.insert(0, " DISTINCT ");
//        }
//        if (count != null) {
//            selectColum.insert(0, " COUNT(").append(") ");
//            queryMate.clearPage();
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append("<script> ")
//                .append("SELECT ")
//                .append(selectColum) //查询字段
//                .append(" FROM ").append(entityMate.getTableName())
//                .append(isProvider ? buildSelectParam(queryMate) : buildSelectQuery(queryMate))
//                .append(" </script>");
//
//        return sb.toString();
//    }
//
//    /**
//     * 解析实体信息
//     */
//    public EntityMate parseEntityMate(Class<?> entityType) {
//        Table tableAnno = entityType.getDeclaredAnnotation(Table.class);
//        if (tableAnno == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
//        EntityMate table = new EntityMate();
//        table.setTableName(tableAnno.value());
//        ColumMate columMate;
//        Field[] fieldArr = entityType.getDeclaredFields();
//        for (Field field : fieldArr) {
//            try {
//                columMate = analysisColum(field, entityType);
//                if (columMate == null) {
//                    continue;
//                } else if (isIgnore(field)) {
//                    continue;
//                } else if (isKey(field)) {
//                    table.addPrimaryKey(columMate);
//                    table.setKeyEnum(getKeyType(field));
//                } else {
//                    table.addDefault(columMate);
//                }
//            } catch (NoSuchMethodException e) {
//                LOGGER.debug("{} : not find getterAndSetter method", field.getName());
//            }
//        }
//
//        return table.validate();
//    }
//
//    /**
//     * 解析查询实体
//     *
//     * @param method
//     * @return
//     */
//    public QueryMate parseQueryEntity(Method method) {
//        QueryMate query = new QueryMate();
//        int index = 0;
//        Class<?>[] parameterTypes = method.getParameterTypes();
//        int paramCount = parameterTypes.length;
//        if (paramCount != 1) throw new BindingException("parameters filed null Unable to build Sql");
//        for (Class<?> par : parameterTypes) {
//            List<Field> fields = Reflection.getField(par);
//            for (Field f : fields) {
//                if (annotianAssistan.isIgnore(f)) continue;
//                query.addFilter(annotianAssistan.queryMate(f, index));
//                ++index;
//            }
//        }
//        return query;
//    }
//
//    /**
//     * 解析查询方法
//     *
//     * @param method
//     * @return
//     */
//    public QueryMate parseQueryMethod(Method method) {
//        QueryMate query = new QueryMate();
//        int index = 0;
//        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//        Parameter[] parameters = method.getParameters();
//        int paramCount = parameterAnnotations.length;
//        boolean isQueryObject = false;
//        List<String> paramNames = ParamNameUtil.getParamNames(method);
//        if (paramCount == 0) throw new BindingException("parameters filed null Unable to build Sql");
//        for (int i = 0; i < paramCount; ++i) {
//            FilterColumMate paramFilter = annotianAssistan.queryMate(parameterAnnotations[i], i, paramNames.get(i));
//            query.addFilter(paramFilter);
//        }
//        return query;
//    }
//
//
////    /**
////     * 构建一个查询片段
////     */
////    private StringBuilder buildSelectParam(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        if (queryMate.getQueryFilter().isEmpty()) return sb;
////        sb.append(" WHERE ").append(buildQuery(queryMate.getQueryFilter()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////        return sb;
////    }
////
////    /**
////     * 构建动态查询片段
////     */
////    private StringBuilder buildSelectQuery(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" WHERE ")
////                .append(buildQueryIf(queryMate.getQueryFilter()))
////                .append(buildGroup(queryMate.getGroup()))
////                .append(buildOrder(queryMate.getOrder()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////
////        return sb;
////    }
////
////    /**
////     * 构建分页片段
////     */
////    private StringBuilder buildLimit(FilterColumMate start, FilterColumMate offset) {
////        StringBuilder sb = new StringBuilder();
////        if (start == null || offset == null) return sb;
////        sb
////                .append(start.getConditionEnum().getSymbol())
////                .append(start.getBatisField())
////                .append(offset.getConditionEnum().getSymbol())
////                .append(offset.getBatisField());
////        return sb;
////    }
////
////    /**
////     * 构建动态排序片段
////     */
////    private StringBuilder buildOrder(FilterColumMate order) {
////        StringBuilder sb = new StringBuilder();
////        if (order == null) return sb;
////        sb.append(String.format(templateIf, order.getField(), String.format(order.getConditionEnum().getSymbol(), order.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 构建动态分组片段
////     */
////    private StringBuilder buildGroup(FilterColumMate group) {
////        StringBuilder sb = new StringBuilder();
////        if (group == null) return sb;
////        sb.append(String.format(templateIf, group.getField(), String.format(group.getConditionEnum().getSymbol(), group.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 查询片段的生成
////     */
////    private StringBuilder buildQuery(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1= 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField()));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField()));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm()));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                case SET:
////                    break;
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 动态查询片段实现
////     */
////    private StringBuilder buildQueryIf(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1 = 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField())));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField())));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm())));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 解析查询信息
////     */
////
////
////    /**
////     * 获取主键的枚举信息
////     *
////     * @param field
////     * @return
////     */
////    private KeyEnum getKeyType(Field field) {
////        return AnnotationUtils.findAnnotation(field, PrimaryKey.class).type();
////    }
////
////    /**
////     * 判断是否是主键
////     */
////    private boolean isKey(Field field) {
////        return field.getDeclaredAnnotation(PrimaryKey.class) != null;
////    }
////
////    /**
////     * 判断是否是忽略字段
////     */
////    private boolean isIgnore(Field field) {
////        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
////    }
////
////
////    private String underscoreName(String camelCaseName) {
////
////        StringBuilder result = new StringBuilder();
////        if (camelCaseName != null && camelCaseName.length() > 0) {
////            result.append(camelCaseName.substring(0, 1).toLowerCase());
////            for (int i = 1; i < camelCaseName.length(); i++) {
////                char ch = camelCaseName.charAt(i);
////                if (Character.isUpperCase(ch)) {
////                    result.append("_");
////                    result.append(Character.toLowerCase(ch));
////                } else {
////                    result.append(ch);
////                }
////            }
////        }
////        return result.toString();
////    }
////
////    private ColumMate analysisColum(Field field, Class<?> clazz) throws NoSuchMethodException {
////        ColumMate mapper = new ColumMate();
////        mapper.setField(field.getName());
////        mapper.setColunm(underscoreName(field.getName()));
////        try {
////            Reflection.setter(field, clazz);
////            Reflection.getter(field, clazz);
////        } catch (NoSuchMethodException e) {
////            return null;
////        }
////
////        return mapper;
////    }
////
////
////    /**
////     * 构建一个带标签标准的Update语句
////     *
////     * @param entityMate 构建的标准
////     * @return 返回sql语句
////     */
////    public String parseUpdate(EntityMate entityMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("<script> ").append(buildUpdate(entityMate)).append(" </script>");
////        return sb.toString();
////    }
////
////    public static StringBuilder buildInsertSyntax(EntityMate mate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("INSERT INTO ").append(mate.getTableName());
////        return sb;
////    }
////
////    public static StringBuilder buildInsertColunm(EntityMate mate) {
////        StringBuilder colunm = new StringBuilder();
////        StringBuilder field = new StringBuilder();
////        mate.getInsertColum().forEach(item -> {
////            colunm.append(", ").append(item.getColunm());
////            field.append(", ").append(item.getBatisField());
////
////        });
////        StringBuilder sb = new StringBuilder();
////        sb.append(" ( ").append(colunm.substring(1)).append(") VALUES (").append(field.substring(1)).append(" )");
////        return sb;
////    }
////
//    private StringBuilder buildBatchInsertColunm(EntityMate mate) {
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(" ( ").append(colunm.substring(1))
//                .append(") VALUES <foreach collection='list' item='item' index='index' separator=',' >").append(field.delete(0, 1).insert(0, "(").append(")")).append("</foreach>");
//        return sb;
//    }
////
////
////    //构建查询语句字段
////    public static String buildSelectColunm(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        table.getSelectColum().forEach(item -> {
////            sb.append(", ").append(item.getColunm());
////        });
////        return sb.substring(1);
////    }
////
////    //构建查询的语句语法+字段
////    public static StringBuilder buildSelectSyntax(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" SELECT ").append(buildSelectColunm(table)).append(" FROM ").append(table.getTableName());
////    }
////
////    //构建查询语句的主键查询
////    public static StringBuilder buildWherePrimeKey(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" WHERE ").append(table.getKey().getColunm()).append(" = ").append(table.getKey().getBatisField());
////    }
///**
// //     * 构建一个查询片段
// //     */
////    private StringBuilder buildSelectParam(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        if (queryMate.getQueryFilter().isEmpty()) return sb;
////        sb.append(" WHERE ").append(buildQuery(queryMate.getQueryFilter()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////        return sb;
////    }
////
////    /**
////     * 构建动态查询片段
////     */
////    private StringBuilder buildSelectQuery(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" WHERE ")
////                .append(buildQueryIf(queryMate.getQueryFilter()))
////                .append(buildGroup(queryMate.getGroup()))
////                .append(buildOrder(queryMate.getOrder()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////
////        return sb;
////    }
////
////    /**
////     * 构建分页片段
////     */
////    private StringBuilder buildLimit(FilterColumMate start, FilterColumMate offset) {
////        StringBuilder sb = new StringBuilder();
////        if (start == null || offset == null) return sb;
////        sb
////                .append(start.getConditionEnum().getSymbol())
////                .append(start.getBatisField())
////                .append(offset.getConditionEnum().getSymbol())
////                .append(offset.getBatisField());
////        return sb;
////    }
////
////    /**
////     * 构建动态排序片段
////     */
////    private StringBuilder buildOrder(FilterColumMate order) {
////        StringBuilder sb = new StringBuilder();
////        if (order == null) return sb;
////        sb.append(String.format(templateIf, order.getField(), String.format(order.getConditionEnum().getSymbol(), order.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 构建动态分组片段
////     */
////    private StringBuilder buildGroup(FilterColumMate group) {
////        StringBuilder sb = new StringBuilder();
////        if (group == null) return sb;
////        sb.append(String.format(templateIf, group.getField(), String.format(group.getConditionEnum().getSymbol(), group.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 查询片段的生成
////     */
////    private StringBuilder buildQuery(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1= 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField()));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField()));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm()));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                case SET:
////                    break;
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 动态查询片段实现
////     */
////    private StringBuilder buildQueryIf(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1 = 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField())));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField())));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm())));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 解析查询信息
////     */
////
////
////    /**
////     * 获取主键的枚举信息
////     *
////     * @param field
////     * @return
////     */
////    private KeyEnum getKeyType(Field field) {
////        return AnnotationUtils.findAnnotation(field, PrimaryKey.class).type();
////    }
////
////    /**
////     * 判断是否是主键
////     */
////    private boolean isKey(Field field) {
////        return field.getDeclaredAnnotation(PrimaryKey.class) != null;
////    }
////
////    /**
////     * 判断是否是忽略字段
////     */
////    private boolean isIgnore(Field field) {
////        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
////    }
////
////
////    private String underscoreName(String camelCaseName) {
////
////        StringBuilder result = new StringBuilder();
////        if (camelCaseName != null && camelCaseName.length() > 0) {
////            result.append(camelCaseName.substring(0, 1).toLowerCase());
////            for (int i = 1; i < camelCaseName.length(); i++) {
////                char ch = camelCaseName.charAt(i);
////                if (Character.isUpperCase(ch)) {
////                    result.append("_");
////                    result.append(Character.toLowerCase(ch));
////                } else {
////                    result.append(ch);
////                }
////            }
////        }
////        return result.toString();
////    }
////
////    private ColumMate analysisColum(Field field, Class<?> clazz) throws NoSuchMethodException {
////        ColumMate mapper = new ColumMate();
////        mapper.setField(field.getName());
////        mapper.setColunm(underscoreName(field.getName()));
////        try {
////            Reflection.setter(field, clazz);
////            Reflection.getter(field, clazz);
////        } catch (NoSuchMethodException e) {
////            return null;
////        }
////
////        return mapper;
////    }
////
////
////    /**
////     * 构建一个带标签标准的Update语句
////     *
////     * @param entityMate 构建的标准
////     * @return 返回sql语句
////     */
////    public String parseUpdate(EntityMate entityMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("<script> ").append(buildUpdate(entityMate)).append(" </script>");
////        return sb.toString();
////    }
////
////    public static StringBuilder buildInsertSyntax(EntityMate mate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("INSERT INTO ").append(mate.getTableName());
////        return sb;
////    }
////
////    public static StringBuilder buildInsertColunm(EntityMate mate) {
////        StringBuilder colunm = new StringBuilder();
////        StringBuilder field = new StringBuilder();
////        mate.getInsertColum().forEach(item -> {
////            colunm.append(", ").append(item.getColunm());
////            field.append(", ").append(item.getBatisField());
////
////        });
////        StringBuilder sb = new StringBuilder();
////        sb.append(" ( ").append(colunm.substring(1)).append(") VALUES (").append(field.substring(1)).append(" )");
////        return sb;
////    }
////
////    private StringBuilder buildBatchInsertColunm(EntityMate mate) {
////        StringBuilder colunm = new StringBuilder();
////        StringBuilder field = new StringBuilder();
////        mate.getInsertColum().forEach(item -> {
////            colunm.append(", ").append(item.getColunm());
////            field.append(", ").append(item.getBatisField("item"));
////
////        });
////        StringBuilder sb = new StringBuilder();
////        sb.append(" ( ").append(colunm.substring(1))
////                .append(") VALUES <foreach collection='list' item='item' index='index' separator=',' >").append(field.delete(0, 1).insert(0, "(").append(")")).append("</foreach>");
////        return sb;
////    }
////
////
////    //构建查询语句字段
////    public static String buildSelectColunm(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        table.getSelectColum().forEach(item -> {
////            sb.append(", ").append(item.getColunm());
////        });
////        return sb.substring(1);
////    }
////
////    //构建查询的语句语法+字段
////    public static StringBuilder buildSelectSyntax(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" SELECT ").append(buildSelectColunm(table)).append(" FROM ").append(table.getTableName());
////    }
////
////    //构建查询语句的主键查询
////    public static StringBuilder buildWherePrimeKey(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" WHERE ").append(table.getKey().getColunm()).append(" = ").append(table.getKey().getBatisField());
////    }
///**
// //     * 构建一个查询片段
// //     */
////    private StringBuilder buildSelectParam(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        if (queryMate.getQueryFilter().isEmpty()) return sb;
////        sb.append(" WHERE ").append(buildQuery(queryMate.getQueryFilter()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////        return sb;
////    }
////
////    /**
////     * 构建动态查询片段
////     */
////    private StringBuilder buildSelectQuery(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" WHERE ")
////                .append(buildQueryIf(queryMate.getQueryFilter()))
////                .append(buildGroup(queryMate.getGroup()))
////                .append(buildOrder(queryMate.getOrder()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////
////        return sb;
////    }
////
////    /**
////     * 构建分页片段
////     */
////    private StringBuilder buildLimit(FilterColumMate start, FilterColumMate offset) {
////        StringBuilder sb = new StringBuilder();
////        if (start == null || offset == null) return sb;
////        sb
////                .append(start.getConditionEnum().getSymbol())
////                .append(start.getBatisField())
////                .append(offset.getConditionEnum().getSymbol())
////                .append(offset.getBatisField());
////        return sb;
////    }
////
////    /**
////     * 构建动态排序片段
////     */
////    private StringBuilder buildOrder(FilterColumMate order) {
////        StringBuilder sb = new StringBuilder();
////        if (order == null) return sb;
////        sb.append(String.format(templateIf, order.getField(), String.format(order.getConditionEnum().getSymbol(), order.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 构建动态分组片段
////     */
////    private StringBuilder buildGroup(FilterColumMate group) {
////        StringBuilder sb = new StringBuilder();
////        if (group == null) return sb;
////        sb.append(String.format(templateIf, group.getField(), String.format(group.getConditionEnum().getSymbol(), group.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 查询片段的生成
////     */
////    private StringBuilder buildQuery(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1= 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField()));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField()));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm()));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                case SET:
////                    break;
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 动态查询片段实现
////     */
////    private StringBuilder buildQueryIf(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1 = 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField())));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField())));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm())));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 解析查询信息
////     */
////
////
////    /**
////     * 获取主键的枚举信息
////     *
////     * @param field
////     * @return
////     */
////    private KeyEnum getKeyType(Field field) {
////        return AnnotationUtils.findAnnotation(field, PrimaryKey.class).type();
////    }
////
////    /**
////     * 判断是否是主键
////     */
////    private boolean isKey(Field field) {
////        return field.getDeclaredAnnotation(PrimaryKey.class) != null;
////    }
////
////    /**
////     * 判断是否是忽略字段
////     */
////    private boolean isIgnore(Field field) {
////        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
////    }
////
////
////    private String underscoreName(String camelCaseName) {
////
////        StringBuilder result = new StringBuilder();
////        if (camelCaseName != null && camelCaseName.length() > 0) {
////            result.append(camelCaseName.substring(0, 1).toLowerCase());
////            for (int i = 1; i < camelCaseName.length(); i++) {
////                char ch = camelCaseName.charAt(i);
////                if (Character.isUpperCase(ch)) {
////                    result.append("_");
////                    result.append(Character.toLowerCase(ch));
////                } else {
////                    result.append(ch);
////                }
////            }
////        }
////        return result.toString();
////    }
////
////    private ColumMate analysisColum(Field field, Class<?> clazz) throws NoSuchMethodException {
////        ColumMate mapper = new ColumMate();
////        mapper.setField(field.getName());
////        mapper.setColunm(underscoreName(field.getName()));
////        try {
////            Reflection.setter(field, clazz);
////            Reflection.getter(field, clazz);
////        } catch (NoSuchMethodException e) {
////            return null;
////        }
////
////        return mapper;
////    }
////
////
////    /**
////     * 构建一个带标签标准的Update语句
////     *
////     * @param entityMate 构建的标准
////     * @return 返回sql语句
////     */
////    public String parseUpdate(EntityMate entityMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("<script> ").append(buildUpdate(entityMate)).append(" </script>");
////        return sb.toString();
////    }
////
////    public static StringBuilder buildInsertSyntax(EntityMate mate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("INSERT INTO ").append(mate.getTableName());
////        return sb;
////    }
////
////    public static StringBuilder buildInsertColunm(EntityMate mate) {
////        StringBuilder colunm = new StringBuilder();
////        StringBuilder field = new StringBuilder();
////        mate.getInsertColum().forEach(item -> {
////            colunm.append(", ").append(item.getColunm());
////            field.append(", ").append(item.getBatisField());
////
////        });
////        StringBuilder sb = new StringBuilder();
////        sb.append(" ( ").append(colunm.substring(1)).append(") VALUES (").append(field.substring(1)).append(" )");
////        return sb;
////    }
////
////    private StringBuilder buildBatchInsertColunm(EntityMate mate) {
////        StringBuilder colunm = new StringBuilder();
////        StringBuilder field = new StringBuilder();
////        mate.getInsertColum().forEach(item -> {
////            colunm.append(", ").append(item.getColunm());
////            field.append(", ").append(item.getBatisField("item"));
////
////        });
////        StringBuilder sb = new StringBuilder();
////        sb.append(" ( ").append(colunm.substring(1))
////                .append(") VALUES <foreach collection='list' item='item' index='index' separator=',' >").append(field.delete(0, 1).insert(0, "(").append(")")).append("</foreach>");
////        return sb;
////    }
////
////
////    //构建查询语句字段
////    public static String buildSelectColunm(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        table.getSelectColum().forEach(item -> {
////            sb.append(", ").append(item.getColunm());
////        });
////        return sb.substring(1);
////    }
////
////    //构建查询的语句语法+字段
////    public static StringBuilder buildSelectSyntax(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" SELECT ").append(buildSelectColunm(table)).append(" FROM ").append(table.getTableName());
////    }
////
////    //构建查询语句的主键查询
////    public static StringBuilder buildWherePrimeKey(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" WHERE ").append(table.getKey().getColunm()).append(" = ").append(table.getKey().getBatisField());
////    }
///**
// //     * 构建一个查询片段
// //     */
////    private StringBuilder buildSelectParam(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        if (queryMate.getQueryFilter().isEmpty()) return sb;
////        sb.append(" WHERE ").append(buildQuery(queryMate.getQueryFilter()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////        return sb;
////    }
////
////    /**
////     * 构建动态查询片段
////     */
////    private StringBuilder buildSelectQuery(QueryMate queryMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" WHERE ")
////                .append(buildQueryIf(queryMate.getQueryFilter()))
////                .append(buildGroup(queryMate.getGroup()))
////                .append(buildOrder(queryMate.getOrder()))
////                .append(buildLimit(queryMate.getStart(), queryMate.getOffset()));
////
////        return sb;
////    }
////
////    /**
////     * 构建分页片段
////     */
////    private StringBuilder buildLimit(FilterColumMate start, FilterColumMate offset) {
////        StringBuilder sb = new StringBuilder();
////        if (start == null || offset == null) return sb;
////        sb
////                .append(start.getConditionEnum().getSymbol())
////                .append(start.getBatisField())
////                .append(offset.getConditionEnum().getSymbol())
////                .append(offset.getBatisField());
////        return sb;
////    }
////
////    /**
////     * 构建动态排序片段
////     */
////    private StringBuilder buildOrder(FilterColumMate order) {
////        StringBuilder sb = new StringBuilder();
////        if (order == null) return sb;
////        sb.append(String.format(templateIf, order.getField(), String.format(order.getConditionEnum().getSymbol(), order.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 构建动态分组片段
////     */
////    private StringBuilder buildGroup(FilterColumMate group) {
////        StringBuilder sb = new StringBuilder();
////        if (group == null) return sb;
////        sb.append(String.format(templateIf, group.getField(), String.format(group.getConditionEnum().getSymbol(), group.getFieldValue())));
////        return sb;
////    }
////
////    /**
////     * 查询片段的生成
////     */
////    private StringBuilder buildQuery(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1= 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField()));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField()));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm()));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                case SET:
////                    break;
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 动态查询片段实现
////     */
////    private StringBuilder buildQueryIf(List<FilterColumMate> list) {
////        StringBuilder sb = new StringBuilder();
////        sb.append(" 1 = 1");
////        list.forEach(item -> {
////            switch (item.getConditionEnum()) {
////                case IN:
////                case NOT_IN:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField())));
////                    break;
////                case EQUEL:
////                case NOT_EQUEL:
////                case LIKE:
////                case LEFT_LIKE:
////                case RIGHT_LIKE:
////                case NOT_LIKE:
////                case NOT_LEFT_LIKE:
////                case NOT_RIGHT_LIKE:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField())));
////                    break;
////                case IS_NULL:
////                case NOT_NULL:
////                    sb.append(String.format(templateIf, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm())));
////                    break;
////                case GROUP_BY:
////                case OEDER_ASC:
////                case OEDER_DESC:
////                case LIMIT_START:
////                case LIMIT_OFFSET:
////                default:
////                    throw new BindingException("生成sql语句约束错误");
////            }
////        });
////        return sb;
////    }
////
////    /**
////     * 解析查询信息
////     */
////
////
////    /**
////     * 获取主键的枚举信息
////     *
////     * @param field
////     * @return
////     */
////    private KeyEnum getKeyType(Field field) {
////        return AnnotationUtils.findAnnotation(field, PrimaryKey.class).type();
////    }
////
////    /**
////     * 判断是否是主键
////     */
////    private boolean isKey(Field field) {
////        return field.getDeclaredAnnotation(PrimaryKey.class) != null;
////    }
////
////    /**
////     * 判断是否是忽略字段
////     */
////    private boolean isIgnore(Field field) {
////        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
////    }
////
////
////    private String underscoreName(String camelCaseName) {
////
////        StringBuilder result = new StringBuilder();
////        if (camelCaseName != null && camelCaseName.length() > 0) {
////            result.append(camelCaseName.substring(0, 1).toLowerCase());
////            for (int i = 1; i < camelCaseName.length(); i++) {
////                char ch = camelCaseName.charAt(i);
////                if (Character.isUpperCase(ch)) {
////                    result.append("_");
////                    result.append(Character.toLowerCase(ch));
////                } else {
////                    result.append(ch);
////                }
////            }
////        }
////        return result.toString();
////    }
////
////    private ColumMate analysisColum(Field field, Class<?> clazz) throws NoSuchMethodException {
////        ColumMate mapper = new ColumMate();
////        mapper.setField(field.getName());
////        mapper.setColunm(underscoreName(field.getName()));
////        try {
////            Reflection.setter(field, clazz);
////            Reflection.getter(field, clazz);
////        } catch (NoSuchMethodException e) {
////            return null;
////        }
////
////        return mapper;
////    }
////
////
////    /**
////     * 构建一个带标签标准的Update语句
////     *
////     * @param entityMate 构建的标准
////     * @return 返回sql语句
////     */
////    public String parseUpdate(EntityMate entityMate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("<script> ").append(buildUpdate(entityMate)).append(" </script>");
////        return sb.toString();
////    }
////
////    public static StringBuilder buildInsertSyntax(EntityMate mate) {
////        StringBuilder sb = new StringBuilder();
////        sb.append("INSERT INTO ").append(mate.getTableName());
////        return sb;
////    }
////
////    public static StringBuilder buildInsertColunm(EntityMate mate) {
////        StringBuilder colunm = new StringBuilder();
////        StringBuilder field = new StringBuilder();
////        mate.getInsertColum().forEach(item -> {
////            colunm.append(", ").append(item.getColunm());
////            field.append(", ").append(item.getBatisField());
////
////        });
////        StringBuilder sb = new StringBuilder();
////        sb.append(" ( ").append(colunm.substring(1)).append(") VALUES (").append(field.substring(1)).append(" )");
////        return sb;
////    }
////
////    private StringBuilder buildBatchInsertColunm(EntityMate mate) {
////        StringBuilder colunm = new StringBuilder();
////        StringBuilder field = new StringBuilder();
////        mate.getInsertColum().forEach(item -> {
////            colunm.append(", ").append(item.getColunm());
////            field.append(", ").append(item.getBatisField("item"));
////
////        });
////        StringBuilder sb = new StringBuilder();
////        sb.append(" ( ").append(colunm.substring(1))
////                .append(") VALUES <foreach collection='list' item='item' index='index' separator=',' >").append(field.delete(0, 1).insert(0, "(").append(")")).append("</foreach>");
////        return sb;
////    }
////
////
////    //构建查询语句字段
////    public static String buildSelectColunm(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        table.getSelectColum().forEach(item -> {
////            sb.append(", ").append(item.getColunm());
////        });
////        return sb.substring(1);
////    }
////
////    //构建查询的语句语法+字段
////    public static StringBuilder buildSelectSyntax(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" SELECT ").append(buildSelectColunm(table)).append(" FROM ").append(table.getTableName());
////    }
////
////    //构建查询语句的主键查询
////    public static StringBuilder buildWherePrimeKey(EntityMate table) {
////        StringBuilder sb = new StringBuilder();
////        return sb.append(" WHERE ").append(table.getKey().getColunm()).append(" = ").append(table.getKey().getBatisField());
////    }
//
//
//}
