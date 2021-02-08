package com.xwc.open.easybatis.spring.boot;

import com.xwc.open.easybatis.core.enums.IdType;

import lombok.Data;


@Data
public class EasybatisProperties {
    /**
     * 如果 tablePrefix 有值就默认开启根据类名映射标明
     */
    private String tablePrefix;
    /**
     * 全局的ID类型
     */
    private IdType idType;

    /**
     * 显示创建SQL的日志
     */
    private boolean generatorSqlLogger = false;
}
