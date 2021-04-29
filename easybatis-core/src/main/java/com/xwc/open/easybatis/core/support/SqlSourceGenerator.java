package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.model.MethodMeta;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:23
 * 备注：
 */
public interface SqlSourceGenerator {

    /**
     * 查询语句构建
     *
     * @param methodMeta 方法元信息
     * @return
     */
    String select(MethodMeta methodMeta);

    /**
     * 插入语句构建
     *
     * @param methodMeta 方法元信息
     * @return
     */
    String insert(MethodMeta methodMeta);


    /**
     * 更新语句构建
     *
     * @param methodMeta 方法元信息
     * @return
     */
    String update(MethodMeta methodMeta);


    /**
     * 删除语句构建
     *
     * @param methodMeta 方法元信息
     * @return
     */
    String delete(MethodMeta methodMeta);
}
