package com.xwc.open.easybatis;

import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 14:41
 */
public class MyBatisSnippetUtils {
    static final String FOREACH_BASE =
            " <foreach item='%s' index='%s' collection='%s' open= '(' close =')' separator=','> %s </foreach> ";

    static final String FOREACH_OBJECT = "<foreach item='%s' index='%s' collection='%s' separator=',' > %s </foreach>";

    static final String SCRIPT = "<script> %s </script>";

    static final String IF_OBJECT = "<if test='%s != null'> %s </if>";

    public static String foreachObject(String itemName, String indexName, String collectionName, String content) {
        return String.format(FOREACH_OBJECT, itemName, indexName, collectionName, content);
    }

    public static String ifObject(String paramName, String conditionSql) {
        return String.format(IF_OBJECT, paramName, conditionSql);
    }

    public static String script(String content) {
        return String.format(SCRIPT, content);
    }


}
