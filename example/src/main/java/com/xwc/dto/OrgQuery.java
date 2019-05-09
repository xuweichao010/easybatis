package com.xwc.dto;

import com.xwc.esbatis.anno.condition.enhance.Equal;
import com.xwc.esbatis.anno.condition.enhance.In;
import com.xwc.esbatis.anno.condition.enhance.RightLike;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/9  14:50
 * 业务：
 * 功能：
 */
public class OrgQuery {

    @RightLike
    private String code;

    @Equal
    private String name;

    @In
    private List<Integer> sons;
}
