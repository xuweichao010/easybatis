package cn.onetozero.easy.parse.model;

import cn.onetozero.easy.parse.supports.ValueHandler;
import cn.onetozero.easy.parse.supports.impl.DefaultHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 类描述：模型属性映射
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:19
 */
public class ModelAttribute {
    /**
     * 属性的名字
     */
    private String field;
    /**
     * 字段名
     */
    private String column;

    /**
     * 属性的getter 方法
     */
    private Method getter;
    /**
     * 属性的setter 方法
     */
    private Method setter;
    /**
     * 查询忽略
     */
    private boolean selectIgnore;
    /**
     * 更新忽略
     */
    private boolean updateIgnore;
    /**
     * 插入忽略
     */
    private boolean insertIgnore;

    private List<Annotation> annotations;

    private ValueHandler<?> valueHandler = new DefaultHandler();

    public ValueHandler<?> getValueHandler() {
        return valueHandler;
    }

    public void setValueHandler(ValueHandler<?> valueHandler) {
        this.valueHandler = valueHandler;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public boolean isSelectIgnore() {
        return selectIgnore;
    }

    public void setSelectIgnore(boolean selectIgnore) {
        this.selectIgnore = selectIgnore;
    }

    public boolean isUpdateIgnore() {
        return updateIgnore;
    }

    public void setUpdateIgnore(boolean updateIgnore) {
        this.updateIgnore = updateIgnore;
    }

    public boolean isInsertIgnore() {
        return insertIgnore;
    }

    public void setInsertIgnore(boolean insertIgnore) {
        this.insertIgnore = insertIgnore;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }
}
