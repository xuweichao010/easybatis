package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.model.ParamMapping;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：
 */
public interface MyBatisOrSqlTemplate {








    default String dynamicSetIf(String setParam, String setValue) {
        return " <if test='" + setParam + " != null'> " + setValue + ",</if>";
    }

    default String insertBatchForeach(String fieldList, String paramName) {
        if (paramName == null) {
            paramName = "list";
        }
        return " <foreach item= 'item'  collection='" + paramName + "' separator=', '> "
                + fieldList
                + " </foreach>";
    }

    default String inForeach(String paramName) {
        if (paramName == null) {
            paramName = "list";
        }
        return " <foreach item= 'item'  collection='" + paramName + "' open='(' separator=', ' close=')'>#{item}</foreach>";
    }


    default String mybatisParam(String fieldName, String prefix) {
        return "#{" + paramName(fieldName, prefix) + "}";
    }

    default String paramName(String fieldName, String prefix) {
        return prefix != null ? prefix + "." + fieldName : fieldName;
    }

    default String columnName(ParamMapping paramMeta) {
        if (StringUtils.hasText(paramMeta.getAlias())) {
            return paramMeta.getAlias() + ".`" + paramMeta.getColumnName() + "`";
        }
        return "`" + paramMeta.getColumnName() + "`";
    }
}
