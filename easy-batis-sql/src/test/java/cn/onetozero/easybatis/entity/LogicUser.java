package cn.onetozero.easybatis.entity;

import cn.onetozero.easy.parse.annotations.Id;
import cn.onetozero.easy.parse.annotations.Table;
import cn.onetozero.easy.parse.enums.IdType;
import lombok.Data;

import java.util.Random;
import java.util.UUID;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 17:43
 */
@Data
@Table("t_user")
public class LogicUser extends BaseLogicEntity {

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



    public static LogicUser randomUser() {
        Random random = new Random();
        LogicUser logicUser = new LogicUser();
        logicUser.setId(UUID.randomUUID().toString().replace("-", ""));
        logicUser.orgCode = "xxxx" + random.nextInt(300);
        logicUser.orgName = "测试分组" + random.nextInt(300);
        logicUser.name = "xxx" + new Random().nextInt(10000);
        logicUser.age = random.nextInt(100);
        logicUser.job = random.nextInt(7);
        logicUser.setValid(1);
        return logicUser;
    }
}
