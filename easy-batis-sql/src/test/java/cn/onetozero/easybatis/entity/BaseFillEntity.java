package cn.onetozero.easybatis.entity;

import cn.onetozero.easy.annotations.models.FillColumn;
import cn.onetozero.easy.annotations.enums.FillType;
import lombok.Data;

import java.util.Date;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/12 17:46
 */
@Data
public class BaseFillEntity {
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
     * 创建时间
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
}
