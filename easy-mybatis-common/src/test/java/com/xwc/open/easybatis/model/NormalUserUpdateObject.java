package com.xwc.open.easybatis.model;

import com.xwc.open.easybatis.annotaions.conditions.Equal;
import lombok.Data;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 18:00
 */
@Data
public class NormalUserUpdateObject {

    @Equal
    private String id;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 用户名
     */
    private String name;

    public static NormalUserUpdateObject createName(String id, String name, String orgCode, String orgName) {
        NormalUserUpdateObject tar = new NormalUserUpdateObject();
        tar.setId(id);
        tar.name = name;
        tar.orgCode = orgCode;
        tar.orgName = orgName;
        return tar;
    }


}
