package com.xwc.open.easybatis.mysql;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.mysql.annotaions.NotIn;
import com.xwc.open.easybatis.mysql.annotaions.NotLike;
import com.xwc.open.easybatis.mysql.snippet.NotInConditional;
import com.xwc.open.easybatis.mysql.snippet.NotLikeConditional;
import com.xwc.open.easybatis.snippet.column.InsertColumnSnippet;
import com.xwc.open.easybatis.snippet.column.SelectColumnSnippet;
import com.xwc.open.easybatis.snippet.from.DeleteFromSnippet;
import com.xwc.open.easybatis.snippet.from.InsertFromSnippet;
import com.xwc.open.easybatis.snippet.from.SelectFromSnippet;
import com.xwc.open.easybatis.snippet.from.UpdateFromSnippet;
import com.xwc.open.easybatis.snippet.order.OrderSnippet;
import com.xwc.open.easybatis.snippet.page.PageSnippet;
import com.xwc.open.easybatis.snippet.set.SetSnippet;
import com.xwc.open.easybatis.snippet.values.InsertValuesSnippet;
import com.xwc.open.easybatis.snippet.where.WhereSnippet;
import com.xwc.open.easybatis.supports.BatisPlaceholder;
import com.xwc.open.easybatis.supports.ConditionalRegistry;
import com.xwc.open.easybatis.supports.DefaultSqlSourceGenerator;
import com.xwc.open.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/10 11:21
 */
public class PostgresSqlSourceGenerator extends DefaultSqlSourceGenerator {

    public PostgresSqlSourceGenerator(SqlPlaceholder sqlPlaceholder, BatisPlaceholder batisPlaceholder, SelectColumnSnippet selectColumnSnippet, ConditionalRegistry conditionalRegistry, EasyBatisConfiguration easyBatisConfiguration, InsertFromSnippet insertSqlFrom, InsertColumnSnippet insertColumnSnippet, InsertValuesSnippet insertValuesSnippet, SelectFromSnippet selectSqlFrom, WhereSnippet whereSnippet, OrderSnippet orderSnippet, PageSnippet pageSnippet, UpdateFromSnippet updateFromSnippet, SetSnippet setSnippet, DeleteFromSnippet deleteFromSnippet) {
        super(sqlPlaceholder, batisPlaceholder, selectColumnSnippet, conditionalRegistry, easyBatisConfiguration, insertSqlFrom, insertColumnSnippet, insertValuesSnippet, selectSqlFrom, whereSnippet, orderSnippet, pageSnippet, updateFromSnippet, setSnippet, deleteFromSnippet);
    }

    public PostgresSqlSourceGenerator(EasyBatisConfiguration easyMyBatisConfiguration) {
        super(easyMyBatisConfiguration, new PostgresSqlPlaceholder());
        this.getConditionalRegistry().register(NotLike.class, new NotLikeConditional(this));
        this.getConditionalRegistry().register(NotIn.class, new NotInConditional(this));
    }
}
