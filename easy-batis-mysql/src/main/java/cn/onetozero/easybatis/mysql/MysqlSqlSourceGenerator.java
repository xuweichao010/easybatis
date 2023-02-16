package cn.onetozero.easybatis.mysql;

import cn.onetozero.easybatis.mysql.annotaions.NotIn;
import cn.onetozero.easybatis.mysql.annotaions.NotLike;
import cn.onetozero.easybatis.mysql.snippet.NotInConditional;
import cn.onetozero.easybatis.mysql.snippet.NotLikeConditional;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.snippet.column.InsertColumnSnippet;
import cn.onetozero.easybatis.snippet.column.SelectColumnSnippet;
import cn.onetozero.easybatis.snippet.from.DeleteFromSnippet;
import cn.onetozero.easybatis.snippet.from.InsertFromSnippet;
import cn.onetozero.easybatis.snippet.from.SelectFromSnippet;
import cn.onetozero.easybatis.snippet.from.UpdateFromSnippet;
import cn.onetozero.easybatis.snippet.order.OrderSnippet;
import cn.onetozero.easybatis.snippet.page.PageSnippet;
import cn.onetozero.easybatis.snippet.set.SetSnippet;
import cn.onetozero.easybatis.snippet.values.InsertValuesSnippet;
import cn.onetozero.easybatis.snippet.where.WhereSnippet;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.ConditionalRegistry;
import cn.onetozero.easybatis.supports.DefaultSqlSourceGenerator;
import cn.onetozero.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/10 11:21
 */
public class MysqlSqlSourceGenerator extends DefaultSqlSourceGenerator {

    public MysqlSqlSourceGenerator(SqlPlaceholder sqlPlaceholder, BatisPlaceholder batisPlaceholder, SelectColumnSnippet selectColumnSnippet, ConditionalRegistry conditionalRegistry, EasyBatisConfiguration easyBatisConfiguration, InsertFromSnippet insertSqlFrom, InsertColumnSnippet insertColumnSnippet, InsertValuesSnippet insertValuesSnippet, SelectFromSnippet selectSqlFrom, WhereSnippet whereSnippet, OrderSnippet orderSnippet, PageSnippet pageSnippet, UpdateFromSnippet updateFromSnippet, SetSnippet setSnippet, DeleteFromSnippet deleteFromSnippet) {
        super(sqlPlaceholder, batisPlaceholder, selectColumnSnippet, conditionalRegistry, easyBatisConfiguration, insertSqlFrom, insertColumnSnippet, insertValuesSnippet, selectSqlFrom, whereSnippet, orderSnippet, pageSnippet, updateFromSnippet, setSnippet, deleteFromSnippet);
    }

    public MysqlSqlSourceGenerator(EasyBatisConfiguration easyMyBatisConfiguration) {
        super(easyMyBatisConfiguration);
    }

    public MysqlSqlSourceGenerator(EasyBatisConfiguration easyMyBatisConfiguration, SqlPlaceholder sqlPlaceholder) {
        super(easyMyBatisConfiguration, sqlPlaceholder);
        this.getConditionalRegistry().register(NotLike.class, new NotLikeConditional(this));
        this.getConditionalRegistry().register(NotIn.class, new NotInConditional(this));
    }
}
