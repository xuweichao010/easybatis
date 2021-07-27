//package com.xwc.open.easybatis.core.mysql.snippet;
//
//import com.xwc.open.easybatis.core.excp.EasyBatisException;
//import com.xwc.open.easybatis.core.model.MethodMeta;
//import com.xwc.open.easybatis.core.model.ParamMapping;
//import com.xwc.open.easybatis.core.model.table.Mapping;
//import com.xwc.open.easybatis.core.support.snippet.InsertValueSnippet;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 作者：徐卫超 cc
// * 时间：2020/12/24
// * 描述：
// */
//public class DefaultInsertValueSnippet implements InsertValueSnippet {
//
//    @Override
//    public String apply(MethodMeta methodMeta) {
//        List<ParamMapping> paramList = methodMeta.getParamMetaList();
//        List<ParamMapping> entityList = methodMeta.getParamMetaList().stream().filter(ParamMapping::isEntity)
//                .collect(Collectors.toList());
//        ParamMapping entityParam = entityList.get(0);
//        if (methodMeta.entityParam() == null) {
//            throw new EasyBatisException("有 " + entityList.size() + " 个实体对象，导致无法创建SQL");
//        }
//        // 处理一个参数
//        if (paramList.size() == 1) {
//            String valueSnippet = this.insertValueSnippet(methodMeta.insertColumnList(), entityParam.isList()?"item":null);
//            if (entityParam.isList()) {
//                return this.insertBatchForeach(valueSnippet, null);
//            }
//            return valueSnippet;
//        } else { // 处理多个参数
//            if (entityParam.isList()) {
//                return this.insertBatchForeach(this.insertValueSnippet(methodMeta.insertColumnList(), "item"), entityParam.getParamName());
//            } else {
//                return this.insertValueSnippet(methodMeta.insertColumnList(), entityParam.getParamName());
//            }
//        }
//    }
//
//    private String fieldColumn(String field, String prefix) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("#{");
//        if (prefix != null) {
//            sb.append(prefix).append(".");
//        }
//        return sb.append(field).append("}").toString();
//    }
//
//    public String insertValueSnippet(List<Mapping> list, String prefix) {
//        return "(" + list.stream().map(column -> this.fieldColumn(column.getField(), prefix)).collect(Collectors.joining(", ")) + ")";
//    }
//}
