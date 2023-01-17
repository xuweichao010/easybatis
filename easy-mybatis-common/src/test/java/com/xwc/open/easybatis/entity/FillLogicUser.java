package com.xwc.open.easybatis.entity;

import com.xwc.open.easy.parse.annotations.Id;
import com.xwc.open.easy.parse.annotations.Table;
import com.xwc.open.easy.parse.enums.IdType;
import lombok.Data;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 17:43
 */
@Data
@Table("t_user")
public class FillLogicUser extends BaseFillAndLogicEntity {

    /**
     * 用户id
     */
    @Id(type = IdType.UUID)
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
    /**
     * 数据类型
     */
    private Integer dataType = 2;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 职位 1-总监 2-经理 1-主管 3-销售 4-行政 5-技术员 6-财务
     */
    private Integer job;

}
