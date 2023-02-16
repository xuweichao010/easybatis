package cn.onetozero.easybatis.samples.entity;

import cn.onetozero.easy.parse.annotations.FillColumn;
import cn.onetozero.easy.parse.annotations.Logic;
import cn.onetozero.easy.parse.enums.FillType;
import lombok.Data;

import java.util.Date;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 17:46
 */
@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    @FillColumn(type = FillType.INSERT)
    private Date createTime;

    /**
     * 创建用户id
     */
    @FillColumn(type = FillType.INSERT)
    private String createId;

    /**
     * 创建用户名
     */
    @FillColumn(type = FillType.INSERT)
    private String createName;

    /**
     * 更新时间
     */
    @FillColumn(type = FillType.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新用户id
     */
    @FillColumn(type = FillType.UPDATE)
    private String updateId;

    /**
     * 更新用户名
     */
    @FillColumn(type = FillType.UPDATE)
    private String updateName;

    @Logic
    private Integer valid;
}
