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

    private String name;

    @In
    private List<Integer> sons;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSons() {
        return sons;
    }

    public void setSons(List<Integer> sons) {
        this.sons = sons;
    }
}
