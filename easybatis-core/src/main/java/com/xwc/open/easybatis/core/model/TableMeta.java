package com.xwc.open.easybatis.core.model;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.table.FieldFillMapping;
import com.xwc.open.easybatis.core.model.table.IdMapping;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import com.xwc.open.easybatis.core.model.table.Mapping;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 创建人：徐卫超
 * 时间：2019/8/3 16:35
 * 功能：对象表的描述属性
 */

@Data
public class TableMeta {


    /**
     * 表名
     */
    private String tableName;
    /**
     * 主键信息
     */
    private IdMapping id;

    /**
     * 普通属性信息
     */
    private Set<Mapping> columnMetas = new HashSet<>(32);
    /**
     * 填充字段信息
     */
    private Set<FieldFillMapping> FillMetas= new HashSet<>();
    /**
     * 逻辑字段
     */
    private LogicMapping logic;

    /**
     * 原始信息
     */
    private Class<?> source;


    public TableMeta validate() {
        if (id == null) {
            throw new EasyBatisException(this.source.getName() + "未指定主键注解");
        }
        return this;
    }

    public void addFill(FieldFillMapping fieldFillMapping) {
        this.FillMetas.add(fieldFillMapping);
    }

    public void addColumn(Mapping mapping) {
        this.columnMetas.add(mapping);
    }
}
