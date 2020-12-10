package com.xwc.open.easybatis.interfaces;

import com.xwc.open.easybatis.support.MethodMeta;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:23
 * 备注：
 */
public interface SQLContext {

    /**
     * 查询语句构建
     *
     * @param methodMetaData 方法元信息
     * @return
     */
    String select(MethodMeta methodMetaData);

    /**
     * 插入语句构建
     *
     * @param methodMetaData 方法元信息
     * @return
     */
    String insert(MethodMeta methodMetaData);


    /**
     * 更新语句构建
     *
     * @param methodMetaData 方法元信息
     * @return
     */
    String update(MethodMeta methodMetaData);


    /**
     * 删除语句构建
     *
     * @param methodMetaData 方法元信息
     * @return
     */
    String delete(MethodMeta methodMetaData);
}
