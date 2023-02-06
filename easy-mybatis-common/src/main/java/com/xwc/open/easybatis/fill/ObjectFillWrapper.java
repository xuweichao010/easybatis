package com.xwc.open.easybatis.fill;

import com.xwc.open.easy.parse.model.FillAttribute;
import com.xwc.open.easy.parse.model.ModelAttribute;
import com.xwc.open.easy.parse.model.TableMeta;
import com.xwc.open.easybatis.exceptions.EasyMybatisException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/3 11:17
 */

public class ObjectFillWrapper implements FillWrapper {
    private final Map<String, ModelAttribute> modelAttributeMap;
    private final Object data;

    public ObjectFillWrapper(TableMeta tableMeta, Object data) {
        this.modelAttributeMap = tableMeta.getFills().stream()
                .collect(Collectors.toMap(FillAttribute::getField, val -> val));
        this.data = data;
    }

    public ObjectFillWrapper(ModelAttribute modelAttribute, Object data) {
        this.modelAttributeMap = new HashMap<>();
        this.data = data;
        this.modelAttributeMap.put(modelAttribute.getField(), modelAttribute);
    }

    @Override
    public Object getValue(String name) {
        ModelAttribute fillAttribute = modelAttributeMap.get(name);
        if (fillAttribute == null) {
            throw new EasyMybatisException("未被定义的填充属性");
        }
        try {
            return fillAttribute.getGetter().invoke(data);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EasyMybatisException("获取填充属性错误" + name);
        }
    }

    @Override
    public void setValue(String name, Object value) {
        ModelAttribute modelAttribute = modelAttributeMap.get(name);
        if (modelAttribute == null) {
            throw new EasyMybatisException("未被定义的填充属性");
        }
        try {
            modelAttribute.getSetter().invoke(data, value);
        } catch (Exception e) {
            throw new EasyMybatisException("设置填充属性类型错误" + name);
        }
    }
}
