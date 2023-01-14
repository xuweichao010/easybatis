package com.xwc.open.easybatis.binding;

import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.PrimaryKeyAttribute;

/**
 * 类描述：描述已经处理的参数结果
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:32
 */
public class MybatisParameterAttribute extends ParameterAttribute {

    /**
     *
     */
    private String column;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
