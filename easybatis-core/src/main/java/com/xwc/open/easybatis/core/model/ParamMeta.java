package com.xwc.open.easybatis.core.model;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.DynamicType;
import lombok.Data;

import java.util.List;
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
    private String paramName;

    private String columnName;

    private ConditionType condition;

    private String parentParamName;

    private String alias;

    private String group;


    private DynamicType dynamicType = DynamicType.NO_DYNAMIC;

    private boolean entity = false;

    private boolean list = false;

    private boolean primaryKey = false;

    private List<ParamMeta> childList;

    private ParamMeta() {
    }

    public static ParamMeta builder(String columnName, String paramName, boolean dynamic) {
        ParamMeta tar = new ParamMeta();
        tar.columnName = columnName;
        tar.paramName = paramName;
        tar.dynamicType = dynamic ? DynamicType.DYNAMIC : DynamicType.NO_DYNAMIC;
        tar.condition = ConditionType.NONE;
        return tar;
    }


    public static ParamMeta builder(String columnName, String paramName, ConditionType condition, String alias, boolean custom, boolean dynamic) {
        ParamMeta tar = new ParamMeta();
        tar.columnName = columnName;
        tar.paramName = paramName;
        tar.condition = condition;
        tar.alias = alias;
        tar.dynamicType = dynamic ? DynamicType.DYNAMIC : DynamicType.NO_DYNAMIC;
        return tar;
    }

    public static ParamMeta builder(String column, String field) {
        return builder(column, field, ConditionType.NONE, null, false, false);
    }

    public static ParamMeta builder(String column, String field, ConditionType condition) {
        return builder(column, field, condition, null, false, false);
    }

    public static ParamMeta builder(String field, boolean entity, boolean list) {
        ParamMeta builder = builder(field, field, ConditionType.NONE, null, false, false);
        builder.setEntity(entity);
        builder.setList(list);
        return builder;
    }


    public void mergeConditionAnnotation(Map<String, Object> map) {
        String value = (String) map.get("value");
        if (StringUtils.hasText(value) && !Objects.equals(value, this.columnName)) {
            this.columnName = value;
        }
        this.alias = (String) map.get("alias");
        if (dynamicType == DynamicType.NO_DYNAMIC) {
            Boolean dynamic = (Boolean) map.get("dynamic");
            if (dynamic != null && dynamic) {
                this.dynamicType = DynamicType.DYNAMIC;
            }
        }
        String group = (String) map.get("group");
        if (StringUtils.hasText(group)) {
            this.group = group;
        }
    }

    public DynamicType paramType() {
        return dynamicType;
    }

    public boolean isMultiCondition() {
        return this.childList != null && !this.childList.isEmpty();
    }

    public boolean hasParent() {
        return this.parentParamName != null;
    }

    public boolean isSetParam() {
        return this.condition == ConditionType.SET_PARAM;
    }

    public boolean isIgnore() {
        return this.condition == ConditionType.IGNORE;
    }

    public boolean isCondition() {
        if (isMultiCondition()) {
            for (ParamMeta paramMeta : this.childList) {
                if (paramMeta.isParamCondition()) {
                    return true;
                }
            }
            return false;
        } else {
            return isParamCondition();
        }
    }

    public boolean isParamCondition() {
        return this.condition != ConditionType.NONE && this.condition != ConditionType.IGNORE && this.condition != ConditionType.SET_PARAM;
    }

    public static boolean isDynamic(ParamMeta paramMeta) {
        return paramMeta.dynamicType == DynamicType.DYNAMIC;
    }
}
