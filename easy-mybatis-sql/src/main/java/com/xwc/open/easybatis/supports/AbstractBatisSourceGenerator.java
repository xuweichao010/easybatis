package com.xwc.open.easybatis.supports;

import com.xwc.open.easybatis.snippet.column.DefaultSelectColumnSnippet;
import com.xwc.open.easybatis.snippet.column.SelectColumnSnippet;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 15:05
 */
public abstract class AbstractBatisSourceGenerator implements SqlSourceGenerator {

    private final SqlPlaceholder sqlPlaceholder;

    private final BatisPlaceholder batisPlaceholder;

    private final SelectColumnSnippet selectColumnSnippet;

    private final ConditionalRegistry conditionalRegistry;

    public AbstractBatisSourceGenerator(SqlPlaceholder sqlPlaceholder, BatisPlaceholder batisPlaceholder, SelectColumnSnippet selectColumnSnippet, ConditionalRegistry conditionalRegistry) {
        this.sqlPlaceholder = sqlPlaceholder;
        this.batisPlaceholder = batisPlaceholder;
        this.selectColumnSnippet = selectColumnSnippet;
        this.conditionalRegistry = conditionalRegistry;
    }

    public AbstractBatisSourceGenerator(SqlPlaceholder sqlPlaceholder) {
        this.sqlPlaceholder = sqlPlaceholder;
        this.selectColumnSnippet = new DefaultSelectColumnSnippet(this);
        this.batisPlaceholder = new MybatisPlaceholder();
        this.conditionalRegistry = new DefaultConditionalRegistry();
    }

    public AbstractBatisSourceGenerator() {
        this.selectColumnSnippet = new DefaultSelectColumnSnippet(this);
        this.sqlPlaceholder = new DefaultSqlPlaceholder();
        this.batisPlaceholder = new MybatisPlaceholder();
        this.conditionalRegistry = new DefaultConditionalRegistry();
    }

    public SelectColumnSnippet getSelectColumnSnippet() {
        return selectColumnSnippet;
    }

    public SqlPlaceholder getSqlPlaceholder() {
        return sqlPlaceholder;
    }

    public BatisPlaceholder getBatisPlaceholder() {
        return batisPlaceholder;
    }


    public ConditionalRegistry getConditionalRegistry() {
        return conditionalRegistry;
    }


}
