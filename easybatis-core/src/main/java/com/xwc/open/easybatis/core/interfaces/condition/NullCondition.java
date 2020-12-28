package com.xwc.open.easybatis.core.interfaces.condition;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.support.ParamMeta;

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
    public String apply(ParamMeta metaData) {
        if (!conditionTypeSet.contains(metaData.getCondition())) return null;
        String condition = "`" + metaData.getColumnName() + "` " +
                metaData.getCondition().expression() + " ";
        if (StringUtils.hasText(metaData.getAlias())) {
            condition = metaData.getAlias() + "." + condition;
        }
        return doApply(metaData.getParamName(), condition, metaData.getType());
    }
}
