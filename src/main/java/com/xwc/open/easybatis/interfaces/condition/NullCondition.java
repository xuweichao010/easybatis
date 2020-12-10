package com.xwc.open.easybatis.interfaces.condition;

import com.xwc.open.easybatis.enums.ConditionType;
import com.xwc.open.easybatis.interfaces.QueryCondition;
import com.xwc.open.easybatis.support.ParamMeta;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:10
 * 备注：
 */
public class NullCondition implements QueryCondition {
    private Set<ConditionType> conditionTypeSet;

    public NullCondition() {
        this.conditionTypeSet = new HashSet<>();
        conditionTypeSet.add(ConditionType.IS_NULL);
        conditionTypeSet.add(ConditionType.NOT_NULL);
    }

    @Override
    public String apply(ParamMeta metaData, boolean isDynamic) {
        if (!conditionTypeSet.contains(metaData.getCondition())) return null;
        String condition = "`" + metaData.getColumnName() + "` " +
                metaData.getCondition().expression() + " ";
        if (StringUtils.hasText(metaData.getAlias())) {
            condition = metaData.getAlias() + "." + condition;
        }
        if (metaData.isCustom()) {
            return dynamicIf(metaData.getParamName(), condition);
        } else if (isDynamic) {
            return dynamicIsNull(metaData.getParamName(), condition);
        }
        return condition;
    }

}
