package com.xwc.esbatis.assistant;

import com.xwc.esbatis.meta.EntityMate;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/13  9:43
 * 业务：
 * 功能：
 */
public class providerCache {
    public static ConcurrentHashMap<String, EntityMate> entityMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, EntityMate> queryMap = new ConcurrentHashMap<>();
}
