//package com.xwc.open.easy.mybatis.binding;
//
//import com.xwc.open.easy.mybatis.SqlConfiguration;
//import com.xwc.open.easy.mybatis.exceptions.NotFoundException;
//
//import java.lang.annotation.Annotation;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 类描述：条件注册器
// * 作者：徐卫超 (cc)
// * 时间 2022/12/2 10:26
// */
//public class SqlSnippetRegistry {
//    private final SqlConfiguration config;
//    private final Map<Class<? extends Annotation>, SqlSnippet> snippetMap = new HashMap<>();
//    {
//
//    }
//
//    public SqlSnippetRegistry(SqlConfiguration config) {
//        this.config = config;
//    }
//
//    public void registry(Class<? extends Annotation> clazz, SqlSnippet snippet) {
//        snippetMap.put(clazz, snippet);
//    }
//
//    public SqlSnippet get(Class<? extends Annotation> clazz) {
//        SqlSnippet sqlSnippet = snippetMap.get(clazz);
//        if (sqlSnippet == null) {
//            throw new NotFoundException("没有找到对应的片段处理器：" + clazz);
//        }
//        return sqlSnippet;
//    }
//}
