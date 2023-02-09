package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.model.FillAttribute;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easybatis.annotaions.other.Dynamic;
import com.xwc.open.easybatis.exceptions.ParamCheckException;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * 类描述：创建一个sql语句
 * 作者：徐卫超 (cc)
 * 时间 2022/12/2 10:20
 */
public interface SqlSourceGenerator {

    /**
     * 查询语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String select(OperateMethodMeta operateMethodMeta);

    /**
     * 插入语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String insert(OperateMethodMeta operateMethodMeta);


    /**
     * 更新语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String update(OperateMethodMeta operateMethodMeta);


    /**
     * 删除语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String delete(OperateMethodMeta operateMethodMeta);


    /**
     * 判断构建语句是否需要进行动态语句构建
     *
     * @param operateMethodMeta 操作方法的源信息
     * @param sqlCommandType    操作类型
     * @return
     */
    static boolean isMethodDynamic(OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        return operateMethodMeta.findAnnotation(Dynamic.class) != null;
    }

    /**
     * 推断这个方法是否需要进行多属性构建
     * 已经在方法中的属性 还有事不在方法中的属性 被称为虚拟属性 都要被纳入判断返回内
     *
     * @param operateMethodMeta 查询的元方法
     * @param sqlCommandType    sql类型
     * @return 方法是否需要多属性构建
     */
    static boolean isMulti(OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        // 当参数是多个值的话一定是多参数
        int paramNum = operateMethodMeta.paramSize();
        if (sqlCommandType == SqlCommandType.SELECT) {
            if (operateMethodMeta.getDatabaseMeta().getLogic() != null) {
                paramNum += 1;
            }
        } else if (SqlCommandType.INSERT == sqlCommandType) {
            if (operateMethodMeta.getParameterAttributes().size() != 1) {
                throw new ParamCheckException("构建的INSERT语句时 参数列表错误");
            }
            ParameterAttribute parameterAttribute = operateMethodMeta.getParameterAttributes().get(0);
            if (parameterAttribute instanceof EntityParameterAttribute) {
                return paramNum > 1;
            } else {
                throw new ParamCheckException("构建的INSERT语句时 不支持的参数类型：" + parameterAttribute.getParameterName());
            }
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            ParameterAttribute entityAttribute = operateMethodMeta.getParameterAttributes().stream()
                    .filter(item -> item instanceof EntityParameterAttribute).findAny()
                    .orElse(null);
            if (entityAttribute == null) {
                paramNum += operateMethodMeta.getDatabaseMeta().getFills().stream().filter(FillAttribute::isUpdateFill).count();
                if (operateMethodMeta.getDatabaseMeta().getLogic() != null) {
                    paramNum += 1;
                }
            }
        }
        return paramNum > 1;
    }


}
