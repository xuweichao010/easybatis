//package com.xwc.open.easybatis.core.mysql.snippet;
//
//import com.xwc.open.easybatis.core.enums.ConditionType;
//import com.xwc.open.easybatis.core.model.MethodMeta;
//import com.xwc.open.easybatis.core.model.ParamMapping;
//import com.xwc.open.easybatis.core.model.table.Mapping;
//import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;
//import com.xwc.open.easybatis.core.support.snippet.UpdateSetSnippet;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 作者：徐卫超 cc
// * 时间：2020/12/25
// * 描述：更新语句更新列片段SQL构建
// */
//public class DefaultUpdateColumnSnippet implements UpdateSetSnippet, MyBatisOrSqlTemplate {
//    public String apply(MethodMeta methodMeta) {
//        List<ParamMapping> paramList = methodMeta.getParamMetaList();
//        List<ParamMapping> setParamList = paramList.stream().filter(paramMeta1 -> paramMeta1.getCondition() == ConditionType.SET_PARAM).collect(Collectors.toList());
//        if (setParamList.isEmpty()) {
//            String prefix = methodMeta.getParamMetaList().size() > 1 ? methodMeta.entityParam().getParamName() : null;
//            if (!methodMeta.hasDynamic()) {
//                return methodMeta.updateColumnList().stream().map(param -> this.setParam(param, prefix)).collect(Collectors.joining(", "));
//            } else {
//                return methodMeta.updateColumnList().stream().map(param ->
//                        this.dynamicSetIf(paramName(param.getField(), prefix), this.setParam(param, prefix))
//                ).collect(Collectors.joining());
//            }
//        } else {
//            return setParamList.stream().map(param -> this.setParam(param.getColumnName(), param.getParamName())).collect(Collectors.joining(", "));
//        }
//    }
//
//    public String setParam(Mapping columnMeta, String prefix) {
//        return "`" + columnMeta.getColumn() + "` = " + this.mybatisParam(columnMeta.getField(), prefix);
//    }
//
//    public String setParam(String column, String field) {
//        return "`" + column + "` = " + this.mybatisParam(field, null);
//    }
//}
