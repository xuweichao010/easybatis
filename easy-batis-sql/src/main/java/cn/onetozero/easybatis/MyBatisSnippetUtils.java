package cn.onetozero.easybatis;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/16 14:41
 */
public class MyBatisSnippetUtils {
    static final String FOREACH_ITEM =
            " <foreach item='%s' collection='%s' open= '(' close =')' separator=','> #{%s} </foreach> ";

    static final String FOREACH_OBJECT = " <foreach item='%s' index='%s' collection='%s' separator='%s' >%s </foreach>";

    static final String SCRIPT = "<script> %s </script>";

    static final String SET = " <set> %s </set>";

    static final String IF_OBJECT = " <if test='%s != null'> %s </if>";

    static final String IF_CONDITION = " <if test='%s'> %s </if>";

    static final String WHERE = " <where> %s </where>";

    static final String TRIM_SUFFIX_OVERRIDES = "<trim prefix= '%s' suffixOverrides= '%s'> %s </trim>";

    public static String foreachItem(String itemName, String collectionName) {
        return String.format(FOREACH_ITEM, itemName, collectionName, itemName);
    }

    public static String foreachObject(String itemName, String indexName, String collectionName, String content) {
        return String.format(FOREACH_OBJECT, itemName, indexName, collectionName, ",", content);
    }

    public static String foreachObject(String itemName, String indexName, String collectionName, String content, String separator) {
        return String.format(FOREACH_OBJECT, itemName, indexName, collectionName, separator, content);
    }

    public static String ifNonNullObject(String paramName, String content) {
        return String.format(IF_OBJECT, paramName, content);
    }

    public static String ifNonCondition(String paramName1, String paramName2, String content) {
        return String.format(IF_CONDITION, paramName1 + " != null and " + paramName2 + " != null", content);
    }

    public static String script(String content) {
        return String.format(SCRIPT, content);
    }


    public static String where(String content) {
        return String.format(WHERE, content);
    }

    public static String trimSuffixOverrides(String prefix, String suffixOverrides, String content) {
        return String.format(TRIM_SUFFIX_OVERRIDES, prefix, suffixOverrides, content);
    }

    public static String set(String content) {
        return String.format(SET, content);
    }
}
