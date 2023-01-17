package com.xwc.open.easybatis;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.TableMeta;

/**
 * 类描述：创建一个sql语句
 * 作者：徐卫超 (cc)
 * 时间 2022/12/2 10:20
 */
public interface EasyBatisSourceGenerator {

    /**
     * 查询语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String select(OperateMethodMeta operateMethodMeta);

    /**
     * 插入语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String insert(OperateMethodMeta operateMethodMeta);


    /**
     * 更新语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String update(OperateMethodMeta operateMethodMeta);


    /**
     * 删除语句构建
     *
     * @param operateMethodMeta 方法元信息
     * @return
     */
    String delete(OperateMethodMeta operateMethodMeta);


}
