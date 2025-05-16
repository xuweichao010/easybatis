package cn.onetozero.easybatis.entity;

import lombok.Data;

import java.util.Date;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/12 17:44
 */

@Data
public class BaseEntity {

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

    /**
     * 是否有效 0:有效 1:无效
     */
    private Integer valid;
}
