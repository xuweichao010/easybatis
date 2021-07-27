//package com.xwc.open.easybatis.core.mysql.snippet;
//
//import com.xwc.open.easybatis.core.enums.ConditionType;
//import com.xwc.open.easybatis.core.model.MethodMeta;
//import com.xwc.open.easybatis.core.model.ParamMapping;
//import com.xwc.open.easybatis.core.model.table.IdMapping;
//import com.xwc.open.easybatis.core.model.table.LogicMapping;
//import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;
//import com.xwc.open.easybatis.core.support.snippet.SelectConditionSnippet;
//import com.xwc.open.easybatis.core.support.snippet.UpdateConditionSnippet;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 作者：徐卫超 cc
// * 时间：2020/12/25
// * 描述：
// */
//public class DefaultUpdateConditionSnippet implements UpdateConditionSnippet, MyBatisOrSqlTemplate {
//    private SelectConditionSnippet selectConditionSnippet;
//
//    public DefaultUpdateConditionSnippet(SelectConditionSnippet selectConditionSnippet) {
//        this.selectConditionSnippet = selectConditionSnippet;
//    }
//
//    @Override
//    public String apply(MethodMeta methodMeta) {
//        List<ParamMapping> paramList = methodMeta.getParamMetaList();
//        List<ParamMapping> setParamList = paramList.stream()
//                .filter(paramMeta1 -> paramMeta1.getCondition() == ConditionType.SET_PARAM).collect(Collectors.toList());
//        if (setParamList.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            ParamMapping paramMeta = null;
//            if (methodMeta.getParamMetaList().size() > 1) {
//                paramMeta = methodMeta.getParamMetaList().stream().filter(ParamMapping::isEntity).collect(Collectors.toList()).get(0);
//            }
//            IdMapping id = methodMeta.getTableMetadata().getId();
//            sb.append(" `").append(id.getColumn()).append("` = ")
//                    .append(this.mybatisParam(id.getField(), paramMeta == null ? null : paramMeta.getParamName()));
//            LogicMapping logic = methodMeta.getTableMetadata().getLogic();
//            if (logic != null) {
//                sb.append(" AND `").append(logic.getColumn()).append("` = ")
//                        .append(this.mybatisParam(logic.getField(), paramMeta == null ? null : paramMeta.getParamName()));
//            }
//            return sb.toString();
//        } else {
//            return selectConditionSnippet.apply(methodMeta);
//        }
//    }
//}
