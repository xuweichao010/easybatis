package com.xwc.esbatis.interfaces;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/13  11:11
 * 业务：审计功能
 * 功能：用户审计
 */
public interface AuditUser {
    Object getCreateUser();
    Object getUpdateUser();
    Object getCreateUserId();
    Object getUpdateUserId();
}
