package com.xwc.open.easybatis.core.model;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.table.FieldFillMapping;
import com.xwc.open.easybatis.core.model.table.IdMapping;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import com.xwc.open.easybatis.core.model.table.Mapping;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：徐卫超
 * 时间：2019/8/3 16:35
 * 功能：对象表的描述属性
 */

@Data
public class TableMeta {

    /**
     * 实体映射时 需不需要注入Result信息
     */
    private boolean result = false;

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
    private List<Mapping> columnMetaList = new ArrayList<>(10);
    /**
     * 审计信息
     */
    private List<FieldFillMapping> FieldFills = new ArrayList<>(6);

    /**
     * 逻辑字段
     */
    private LogicMapping logic;

    private Class<?> source;


    public void setId(IdMapping id) {
        this.id = id;
        setResult(id.isResult());
    }

    public void setLogic(LogicMapping logic) {
        this.logic = logic;
        setResult(logic.isResult());
    }

    public void setResult(boolean result) {
        if (!this.result && result) this.result = true;
    }

    public void addAuditor(FieldFillMapping auditor) {
        this.FieldFills.add(auditor);
        this.setResult(auditor.isResult());
    }


    public void addColumn(Mapping columnMeta) {
        this.columnMetaList.add(columnMeta);
        setResult(columnMeta.isResult());
    }


    public boolean isSource(Class<?> aClass) {
        return this.source.getName().equals(aClass.getName());
    }

    public TableMeta validate() {
        if (id == null) {
            throw new EasyBatisException(this.source.getName() + "未指定主键注解");
        }
        return this;
    }
}
