package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.enums.IdType;
import com.xwc.open.easy.parse.model.ModelAttribute;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.TableMeta;
import com.xwc.open.easy.parse.model.parameter.CollectionEntityParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.MapParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.ObjectParameterAttribute;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.MyBatisSourceGenerator;
import com.xwc.open.easybatis.binding.MybatisParameterAttribute;
import com.xwc.open.easybatis.exceptions.ParamCheckException;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 15:05
 */
public class DefaultEasyBatisSourceGenerator implements MyBatisSourceGenerator {

    private final EasyBatisConfiguration easyMyBatisConfiguration;

    public DefaultEasyBatisSourceGenerator(EasyBatisConfiguration easyMyBatisConfiguration) {
        this.easyMyBatisConfiguration = easyMyBatisConfiguration;
    }

    @Override
    public String select(OperateMethodMeta operateMethodMeta) {
        return null;
    }

    @Override
    public String insert(OperateMethodMeta operateMethodMeta) {
        boolean multi = isMulti(operateMethodMeta, SqlCommandType.INSERT);
        return null;
    }

    @Override
    public String update(OperateMethodMeta operateMethodMeta) {
        return null;
    }

    @Override
    public String delete(OperateMethodMeta operateMethodMeta) {
        return null;
    }


    public boolean isMulti(OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        // 当参数是多个值的话一定是多参数
        if (operateMethodMeta.paramSize() > 1) {
            return true;
        }
        if (sqlCommandType == SqlCommandType.SELECT) {
            return operateMethodMeta.getDatabaseMeta().getLogic() != null;
        } else if (SqlCommandType.INSERT == sqlCommandType) {
            if (operateMethodMeta.getParameterAttributes().size() != 1) {
                throw new ParamCheckException("构建的INSERT语句时 参数列表错误");
            }
            ParameterAttribute parameterAttribute = operateMethodMeta.getParameterAttributes().get(1);
            if (parameterAttribute instanceof ObjectParameterAttribute) {
                int size = operateMethodMeta.getDatabaseMeta().getFills().size() + (operateMethodMeta.getDatabaseMeta().getLogic() == null ? 0 : 1);
                return size > 0;
            } else if (parameterAttribute instanceof EntityParameterAttribute) {
                return false;
            } else {
                throw new ParamCheckException("构建的INSERT语句时 不支持的参数类型：" + parameterAttribute.getParameterName());
            }
        }
        return false;
    }


    public List<MybatisParameterAttribute> flatEntityParameterAttribute(
            ParameterAttribute parameterAttribute,
            TableMeta tableMeta,
            boolean isMultiParam,
            SqlCommandType sqlCommandType) {

        List<MybatisParameterAttribute> list = new ArrayList<>();
        // 主键只有在插入的时候可以被放入到SQL中
        if (tableMeta.getPrimaryKey().getIdType() != IdType.AUTO && sqlCommandType == SqlCommandType.INSERT) {
            list.add(
                    convertModelAttribute(parameterAttribute, tableMeta.getPrimaryKey(), 1, isMultiParam));
        }
        List<ModelAttribute> normalAttr = tableMeta.getNormalAttr();
        for (int i = 0; i < normalAttr.size(); i++) {
            ModelAttribute modelAttribute = normalAttr.get(i);
            list.add(convertModelAttribute(parameterAttribute, modelAttribute, 1000 + i, isMultiParam));
        }
        for (int i = 0; i < normalAttr.size(); i++) {
            ModelAttribute modelAttribute = normalAttr.get(i);
            list.add(
                    convertModelAttribute(parameterAttribute, modelAttribute, 1000 + i, isMultiParam));
        }
        return list;

    }


    private MybatisParameterAttribute convertModelAttribute(ParameterAttribute parameterAttribute,
                                                            ModelAttribute modelAttribute,
                                                            int modelAttributeIndex, boolean isMultiParam) {
        MybatisParameterAttribute attribute = new MybatisParameterAttribute();
        attribute.setIndex(modelAttributeIndex);
        attribute.setColumn(modelAttribute.getColumn());
        if (isMultiParam) {
            attribute.setPath(new String[]{parameterAttribute.getParameterName(), modelAttribute.getField()});
        } else {
            attribute.setPath(new String[]{modelAttribute.getField()});
        }
        attribute.addAnnotations(modelAttribute.getAnnotations());
        return attribute;
    }

    private boolean hasMapParameterAttribute(Set<ParameterAttribute> parameterAttributes) {
        return null != parameterAttributes.stream()
                .filter(parameterAttribute -> parameterAttribute instanceof MapParameterAttribute)
                .findAny()
                .orElse(null);
    }


}
