package cn.onetozero.easybatis.entity;

import cn.onetozero.easy.annotations.models.Id;
import cn.onetozero.easy.annotations.models.Table;
import cn.onetozero.easy.annotations.enums.IdType;
import lombok.Data;

import java.util.Random;
import java.util.UUID;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/12 17:43
 */
@Data
@Table("t_user")
public class NormalUser extends BaseEntity {

    /**
     * 用户id
     */
    @Id(type = IdType.INPUT)
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


    public static NormalUser randomUser() {
        Random random = new Random();
        NormalUser normalUser = new NormalUser();
        normalUser.setId(UUID.randomUUID().toString().replace("-", ""));
        normalUser.orgCode = "xxxx" + random.nextInt(300);
        normalUser.orgName = "测试分组" + random.nextInt(300);
        normalUser.name = "xxx" + new Random().nextInt(10000);
        normalUser.age = random.nextInt(100);
        normalUser.job = random.nextInt(7);
        normalUser.setValid(1);
        return normalUser;
    }


}
