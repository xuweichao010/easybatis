package com.xwc.open.easybatis.start.entity;

import com.xwc.open.easybatis.core.anno.table.Logic;
import com.xwc.open.easybatis.core.anno.table.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/21
 * 描述：逻辑删除演示
 */

@Data
@Table("t_user")
@EqualsAndHashCode(callSuper = true)
public class LogicUser extends User {

    @Logic(valid = 1, invalid = 0)
    private int valid;
}
