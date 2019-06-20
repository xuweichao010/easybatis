package com.xwc.esbatis.interfaces;

import java.util.Date;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/13  11:11
 * 业务：审计功能
 * 功能：时间审计
 */
public interface AuditTime {
    Date getCreateTime();
    Date getUpdateTime();
}
