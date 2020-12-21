package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.ParamType;
import lombok.Data;

/**
 * 作者：徐卫超 cc
 * 时间：2020/9/9
 * 描述：属性元数据
 */

@Data
public class ParamMeta {
    /**
     * 属于自定义对象还是java对象 true 是自定义对象  false为非自定义对象
     */
    private ParamType type;

    private String paramName;

    private String columnName;

    private ConditionType condition;

    private String alias;

    public ParamMeta() {
    }

    public static ParamMeta builder(String columnName, String paramName, ConditionType type) {
        ParamMeta tar = new ParamMeta();
        tar.columnName = columnName;
        tar.paramName = paramName;
        tar.condition = type;
        tar.type = ParamType.FILED_TYPE;
        return tar;
    }

}
