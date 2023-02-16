package cn.onetozero.easybatis.binding;

import cn.onetozero.easy.parse.model.ParameterAttribute;
import cn.onetozero.easy.parse.utils.AnnotationUtils;
import cn.onetozero.easy.parse.utils.StringUtils;
import cn.onetozero.easybatis.annotaions.AnnotationAttributeProtocol;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.Annotation;

/**
 * 类描述：描述已经处理的参数结果
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:32
 */
public class BatisColumnAttribute extends ParameterAttribute {

    /**
     *
     */
    private String column;

    private Object virtualValue;

    private SqlCommandType sqlCommandType;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public boolean useDynamic(Annotation annotation) {
        if (isMethodDynamic()) {
            return true;
        } else if (annotation != null) {
            Object value = AnnotationUtils.getValue(annotation, AnnotationAttributeProtocol.DYNAMIC);
            return value != null && (boolean) value;
        }
        return false;
    }

    public Object getVirtualValue() {
        return virtualValue;
    }

    public void setVirtualValue(Object virtualValue) {
        this.virtualValue = virtualValue;
    }

    public String useColumn(Annotation annotation) {
        Object value = AnnotationUtils.getValue(annotation, AnnotationAttributeProtocol.VALUE);
        if (value != null && StringUtils.hasText(value.toString())) {
            return value.toString();
        } else {
            return column;
        }
    }
}
