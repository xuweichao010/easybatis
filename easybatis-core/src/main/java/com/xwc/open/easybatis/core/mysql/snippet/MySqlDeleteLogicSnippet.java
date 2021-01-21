package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.table.LoglicColumn;
import com.xwc.open.easybatis.core.support.snippet.DeleteSetLogicSnippet;

public class MySqlDeleteLogicSnippet implements DeleteSetLogicSnippet {
    @Override
    public String apply(MethodMeta methodMeta) {
        StringBuffer sb = new StringBuffer();
        LoglicColumn logic = methodMeta.getTableMetadata().getLogic();
        if (logic == null) {
            throw new EasyBatisException("无法进行逻辑删除");
        } else {
            sb.append(logic.getColumn()).append(" = ").append(logic.getInvalid());
        }
        return sb.toString();
    }
}
