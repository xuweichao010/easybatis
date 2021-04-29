package com.xwc.open.easybatis.crud;

import com.xwc.open.easybatis.core.anno.condition.filter.ASC;
import com.xwc.open.easybatis.core.anno.condition.filter.Offset;
import com.xwc.open.easybatis.core.anno.condition.filter.RightLike;
import com.xwc.open.easybatis.core.anno.condition.filter.Start;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/14
 * 描述：
 */
public class UserFilter {

    @RightLike
    private String orgCode;

    @ASC
    private Integer id;

    @Start
    private Integer start;

    @Offset
    private Integer offset;


}
