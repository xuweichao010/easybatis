package com.xwc.open.esbatis.interfaces;

import com.xwc.open.esbatis.anno.condition.Count;
import com.xwc.open.esbatis.anno.condition.Distinct;
import com.xwc.open.esbatis.meta.Attribute;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/8  18:42
 * 业务：
 * 功能：定义相关sql片段的实现逻辑
 */
public interface SqlAssistant {

    String TMPLATE_IF = "<if test='%s != null' > %s </if>";
    String ALL_COLUMS = "*";


    /**
     * 构建需要查询字段
     */
    StringBuilder builderCountOrDistinct(Count count, Distinct distinct, List<Attribute> list);


    /**
     * 构建过滤语句
     */
    StringBuilder builderColum(List<Attribute> list, String cloums);


    /**
     * 构建一个修改片段
     */
    StringBuilder builderSet(List<Attribute> set);

    /**
     * 构建插入片段
     */
    StringBuilder builderInsert(List<Attribute> list);


    /**
     * 构建批量插入数据片段
     */
    StringBuilder builderInsertBatch(List<Attribute> list);



    /**
     * 构建一个排序的片段
     *
     * @param mate
     * @return
     */
    StringBuilder builderOrderBy(Attribute mate);



    /**
     * 构建一个分页的片段
     *
     * @param start
     * @param offset
     * @return
     */
    StringBuilder builderPage(Attribute start, Attribute offset);


}
