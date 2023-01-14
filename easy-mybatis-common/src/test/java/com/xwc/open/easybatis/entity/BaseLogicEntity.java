package com.xwc.open.easybatis.entity;

import com.xwc.open.easy.parse.annotations.FillColumn;
import com.xwc.open.easy.parse.annotations.Logic;
import com.xwc.open.easy.parse.enums.FillType;
import lombok.Data;

import java.util.Date;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 17:46
 */
@Data
public class BaseLogicEntity {
    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 创建用户id
     */

    private String createId;

    /**
     * 创建用户名
     */

    private String createName;

    /**
     * 创建时间
     */

    private Date updateTime;

    /**
     * 更新用户id
     */

    private String updateId;

    /**
     * 更新用户名
     */
    private String updateName;

    @Logic()
    private int valid;
}
