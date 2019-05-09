package com.xwc.esbatis.anno.enums;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  17:18
 * 业务：管理；表征接口生成的方式
 */
public enum SqlOperationType {
    BASE_SELECT_ONE, //创建一主键查询语句
    BASE_QUERY_SELECT, //根据条件查询List
    BASE_INSERT, //创建一个标准的插入语句
    BASE_UPDATE, //创建一个标准的更新语句
    BASE_PARAM_UPDATE,
    BASE_DELETE, //创建一个标准的删除语句
    BASE_PARAM_SELECT, //创建一个静态的sql
}
