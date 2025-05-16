package cn.onetozero.easybatis.snippet.column;

import cn.onetozero.easy.parse.model.ModelAttribute;
import cn.onetozero.easy.parse.model.OperateMethodMeta;

import java.util.List;

/**
 * 类描述：当用户不定义的时候 框架默认处理 Insert 语句中 INSERT INTO tableName 语句后面的 (col1,col2,col3) 这部分语句的片段
 * @author  徐卫超 (cc)
 * @since 2023/1/16 14:07
 */
public interface SelectColumnSnippet {

    String columns(OperateMethodMeta operateMethodMeta, List<ModelAttribute> columnAttribute);
}
