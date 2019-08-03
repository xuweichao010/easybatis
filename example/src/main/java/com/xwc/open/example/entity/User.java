package com.xwc.open.example.entity;

import com.xwc.open.esbatis.anno.table.Id;
import com.xwc.open.esbatis.anno.table.Table;

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
    /**
     * 用户ID
     */
    @Id
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String name;
    /**
     * 性别;0-男性;1-女性
     */
    private Integer gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 身份证号
     */
    private String identityNumber;
    /**
     * 职位类型
     */
    private Integer jobType;
    /**
     * 职位级别
     */
    private Integer jobLevel;
    /**
     * 机构名
     */
    private String orgName;
    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 同步标识
     */
    private String syncId;

    /**
     * 用户类型;1-超级管理员;2-管理员;3-普通用户
     */
    private Integer type = 3;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(Integer jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
