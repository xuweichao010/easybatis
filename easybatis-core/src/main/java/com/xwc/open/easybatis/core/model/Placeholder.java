package com.xwc.open.easybatis.core.model;

import lombok.Data;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/7/26
 * 描述：占位符信息
 */
@Data
public class Placeholder {

    /**
     * 参数路径
     */
    private String paramPath;


    /**
     * 占位符
     */
    private String holder;
}
