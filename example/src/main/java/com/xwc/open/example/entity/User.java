package com.xwc.open.example.entity;

import com.xwc.open.esbatis.anno.table.Id;
import com.xwc.open.esbatis.anno.table.Table;
import com.xwc.open.esbatis.enums.IdType;

import java.io.Serializable;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/27  10:17
 * 业务：
 * 功能：
 */
@Table("t_user")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4279599274719815691L;
    @Id(type = IdType.UUID)
    private String id;

    private String orgCode;

    private String orgName;

    private String name;

    private Integer age;

    private Integer job;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", orgName='" + orgName + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", job=" + job +
                '}';
    }
}
