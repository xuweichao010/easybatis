package com.xwc.open.easybatis.fill;

import java.util.Map;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/3 11:17
 */

public class MapFillWrapper implements FillWrapper {
    private final Map<String, Object> data;

    public MapFillWrapper(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public Object getValue(String name) {
        return data.get(name);
    }

    @Override
    public void setValue(String name, Object value) {
        data.put(name, value);
    }
}
