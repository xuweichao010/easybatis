package com.xwc.open.example.entity;

import com.xwc.open.easybatis.anno.table.Id;
import com.xwc.open.easybatis.anno.table.Table;
import com.xwc.open.easybatis.enums.IdType;
import com.xwc.open.easybatis.handler.EnumHandler;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/27  10:17
 * 业务：测试UUID实体
 * 功能：
 */
@Table("t_user")
@Data
public class UserEnum implements Serializable {
    private static final long serialVersionUID = -4279599274719815691L;
    @Id(type = IdType.UUID)
    private String id;

    private String name;

    private Valid valid;


    public  enum Valid implements EnumHandler {
        VALID(1, "有效"),
        INVALID(2, "无效");

        private int value;
        private String desc;

        Valid(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public int value() {
            return value;
        }

    }
}
