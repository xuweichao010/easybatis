package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.support.table.AuditorColumn;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;
import com.xwc.open.easybatis.core.support.table.PrimayKey;
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
    private PrimayKey id;

    /**
     * 普通属性信息
     */
    private List<ColumnMeta> columnMetaList = new ArrayList<>(10);
    /**
     * 审计信息
     */
    private List<AuditorColumn> auditorList = new ArrayList<>(6);

    /**
     * 逻辑字段
     */
    private LoglicColumn logic;


    public void setId(PrimayKey id) {
        this.id = id;
        setResult(id.isResult());
    }

    public void setLogic(LoglicColumn loglic) {
        this.logic = loglic;
        setResult(loglic.isResult());
    }

    public void setResult(boolean result) {
        if (!this.result && result) this.result = true;
    }

    public void addAuditor(AuditorColumn auditor) {
        this.auditorList.add(auditor);
        this.setResult(auditor.isResult());
    }


    public void addColumn(ColumnMeta columnMeta) {
        this.columnMetaList.add(columnMeta);
        setResult(columnMeta.isResult());
    }

}
