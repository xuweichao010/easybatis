package com.xwc.esbatis.interfaces;

import com.xwc.esbatis.anno.Count;
import com.xwc.esbatis.anno.Distinct;
import com.xwc.esbatis.meta.ColumMate;
import com.xwc.esbatis.meta.FilterColumMate;

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
     * 构建汇总片段
     */
    StringBuilder builderCountOrDistinct(StringBuilder colums, Count count, Distinct distinct);


    /**
     * 构建查询属性片段
     */
    StringBuilder builderColum(List<ColumMate> list, String cloums);

    /**
     * 构建一个修改属性片段
     */
    StringBuilder builderSet(List<ColumMate> set);

    /**
     * 构建插入数据片段
     */
    StringBuilder builderInsert(List<ColumMate> list);

    /**
     * 构建批量插入数据片段
     */
    StringBuilder builderInsertBatch(List<ColumMate> list);

    /**
     * 构建一个动态查询条件片段
     */
    StringBuilder builderDynamicQuery(List<FilterColumMate> list);


    /**
     * 构建一个查询条件片段
     *
     * @param list
     * @return
     */
    StringBuilder builderQuery(List<FilterColumMate> list);


    /**
     * 构建一个排序的片段
     *
     * @param mate
     * @return
     */
    StringBuilder builderOrderBy(FilterColumMate mate);


    /**
     * 构建一个分组的片段
     *
     * @param mate
     * @return
     */
    StringBuilder builderGroupBy(FilterColumMate mate);

    /**
     * 构建一个分页的片段
     *
     * @param start
     * @param offset
     * @return
     */
    StringBuilder builderPage(FilterColumMate start, FilterColumMate offset);


}
