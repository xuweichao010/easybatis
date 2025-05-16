package cn.onetozero.easybatis.supports;

import cn.onetozero.easybatis.snippet.column.DefaultSelectColumnSnippet;
import cn.onetozero.easybatis.snippet.column.SelectColumnSnippet;
import lombok.Getter;

/**
 * 类描述：
 *
 * @author 徐卫超 (cc)
 * @since 2023/1/12 15:05
 */
@Getter
public abstract class AbstractBatisSourceGenerator implements SqlSourceGenerator {

    private final SqlPlaceholder sqlPlaceholder;

    private final BatisPlaceholder batisPlaceholder;

    private final SelectColumnSnippet selectColumnSnippet;

    private final ConditionalRegistry conditionalRegistry;

    public AbstractBatisSourceGenerator(SqlPlaceholder sqlPlaceholder, BatisPlaceholder batisPlaceholder,
                                        SelectColumnSnippet selectColumnSnippet,
                                        ConditionalRegistry conditionalRegistry) {
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
}
