package com.xwc.open.easybatis;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 14:41
 */
public class MyBatisSnippetUtils {
    static final String FOREACH_BASE =
            " <foreach item='%s' index='%s' collection='%s' open= '(' close =')' separator=','> %s </foreach> ";

    static final String FOREACH_OBJECT = " <foreach item='%s' index='%s' collection='%s' separator=',' >%s </foreach>";

    static final String SCRIPT = "<script> %s </script>";

    static final String IF_OBJECT = " <if test='%s != null'> %s </if>";

    static final String IF_CONDITION = " <if test='%s'> %s </if>";

    static final String WHERE = " <where> %s </where>";

    public static String foreachObject(String itemName, String indexName, String collectionName, String content) {
        return String.format(FOREACH_OBJECT, itemName, indexName, collectionName, content);
    }

    public static String ifNonNullObject(String paramName, String conditionSql) {
        return String.format(IF_OBJECT, paramName, conditionSql);
    }

    public static String ifNonCondition(String paramName1, String paramName2, String conditionSql) {
        return String.format(IF_CONDITION, paramName1 + " != null and " + paramName2 + " != null", conditionSql);
    }

    public static String script(String content) {
        return String.format(SCRIPT, content);
    }


    public static String where(String sqlConditions) {
        return String.format(WHERE, sqlConditions);
    }
}
