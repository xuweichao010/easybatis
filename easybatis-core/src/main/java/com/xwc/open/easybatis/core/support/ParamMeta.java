package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.ParamType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

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

    private boolean dynamic = false;

    private boolean custom = false;

    private boolean entity = false;

    private ParamMeta() {
    }


    public static ParamMeta builder(String columnName, String paramName, ConditionType condition, String alias, boolean custom, boolean dynamic) {
        ParamMeta tar = new ParamMeta();
        tar.columnName = columnName;
        tar.paramName = paramName;
        tar.condition = condition;
        tar.type = ParamType.FILED_TYPE;
        tar.alias = alias;
        tar.custom = custom;
        tar.dynamic = dynamic;
        tar.paramType();
        return tar;
    }

    public static ParamMeta builderEqual(String column, String field) {
        return builder(column, field, ConditionType.EQUAL, null, false, false);
    }

    public static ParamMeta builderInsert(String field, boolean entity) {
        ParamMeta builder = builder(field, field, ConditionType.EQUAL, null, false, false);
        builder.setEntity(entity);
        return builder;
    }

    public void mergeConditionAnnotation(Map<String, Object> map) {
        String value = (String) map.get("value");
        if (StringUtils.hasText(value) && !Objects.equals(value, this.columnName)) {
            this.columnName = value;
        }
        this.alias = (String) map.get("alias");
        Boolean dynamic = (Boolean) map.get("dynamic");
        this.dynamic = this.dynamic || dynamic;
        this.paramType();
        ;
    }

    public void paramType() {
        if (this.custom) {
            this.type = ParamType.FILED_TYPE_DYNAMIC;
        } else if (this.dynamic) {
            this.type = ParamType.PARAM_TYPE_DYNAMIC;
        } else {
            this.type = ParamType.PARAM_TYPE;
        }
    }

}
