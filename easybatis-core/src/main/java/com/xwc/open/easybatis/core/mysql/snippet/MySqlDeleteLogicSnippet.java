package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.support.snippet.DeleteSetLogicSnippet;

public class MySqlDeleteLogicSnippet implements DeleteSetLogicSnippet, MyBatisOrSqlTemplate {
    @Override
    public String apply(MethodMeta methodMeta) {
        StringBuffer sb = new StringBuffer();
        LogicMapping logic = methodMeta.getTableMetadata().getLogic();
        if (logic == null) {
            throw new EasyBatisException("无法进行逻辑删除");
        } else {
            sb.append(logic.getColumn()).append(" = ").append(this.mybatisParam("invalid", null));
        }
        return sb.toString();
    }
}
