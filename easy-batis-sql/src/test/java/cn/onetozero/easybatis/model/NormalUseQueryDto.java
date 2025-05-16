package cn.onetozero.easybatis.model;

import cn.onetozero.easy.annotations.conditions.Between;
import cn.onetozero.easy.annotations.conditions.Equal;
import lombok.Data;

/**
 * 类描述：用于单元测试对象查询
 * @author  徐卫超 (cc)
 * @since 2023/2/1 9:37
 */

@Data
public class NormalUseQueryDto {
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
    @Equal(dynamic = true)
    private String name;

    /**
     * 用户年龄
     */
    @Between(of = "ageTo")
    private Integer age;

    /**
     * 用户年龄
     */
    private Integer ageTo;

    /**
     * 职位 1-总监 2-经理 1-主管 3-销售 4-行政 5-技术员 6-财务
     */
    @Equal(dynamic = true)
    private Integer job;



    public static NormalUseQueryDto createAllConditionQueryObject() {
        NormalUseQueryDto query = new NormalUseQueryDto();
        query.orgCode = "200";
        query.orgName = "东汉";
        query.name = "曹操";
        query.age = 30;
        query.ageTo = 70;
        query.job = 1;
        return query;
    }


}
