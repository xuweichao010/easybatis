package com.xwc.entity;

import com.xwc.esbatis.anno.condition.Equal;
import com.xwc.esbatis.anno.condition.In;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/26  16:01
 * 业务：
 * 功能：
 */
public class UserQuery {
    @Equal
    private String account;        //登录账号

    @In
    private List<String> email;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
