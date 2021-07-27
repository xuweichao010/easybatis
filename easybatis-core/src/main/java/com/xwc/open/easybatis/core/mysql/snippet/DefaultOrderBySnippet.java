//package com.xwc.open.easybatis.core.mysql.snippet;
//
//import com.xwc.open.easybatis.core.enums.ConditionType;
//import com.xwc.open.easybatis.core.enums.DynamicType;
//import com.xwc.open.easybatis.core.excp.EasyBatisException;
//import com.xwc.open.easybatis.core.model.MethodMeta;
//import com.xwc.open.easybatis.core.model.ParamMapping;
//import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;
//import com.xwc.open.easybatis.core.support.snippet.OrderBySnippet;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * 作者：徐卫超 cc
// * 时间：2020/12/25
// * 描述：更新语句更新条件SQL片段构建
// */
//public class DefaultOrderBySnippet implements OrderBySnippet, MyBatisOrSqlTemplate {
//    Set<ConditionType> orderCondition = Stream.of(ConditionType.ORDER_BY_ASC, ConditionType.ORDER_BY_DESC, ConditionType.ORDER_BY).collect(Collectors.toSet());
//
//    @Override
//    public String apply(MethodMeta methodMeta) {
//        List<ParamMapping> orderList = new ArrayList<>();
//        for (ParamMapping paramMeta : methodMeta.getParamMetaList()) {
//            if (orderCondition.contains(paramMeta.getCondition())) {
//                orderList.add(paramMeta);
//            } else if (paramMeta.getChildList() != null && !paramMeta.getChildList().isEmpty()) {
//                for (ParamMapping meta : paramMeta.getChildList()) {
//                    if (orderCondition.contains(paramMeta.getCondition())) {
//                        orderList.add(meta);
//                    }
//                }
//            }
//        }
//        boolean multi = methodMeta.getParamMetaList().size() > 1;
//        StringBuilder sb = new StringBuilder();
//        List<ParamMapping> collect = orderList.stream().filter(paramMeta -> paramMeta.getCondition() == ConditionType.EQUAL).collect(Collectors.toList());
//        if (collect.isEmpty()) {
//            orderList.forEach(orderCondition -> {
//                String prefix = multi && orderCondition.hasParent() ? orderCondition.getParentParamName() : null;
//                String condition = this.columnName(orderCondition) + " " + orderCondition.getCondition().expression();
//                String orderBy;
//                if (orderCondition.getDynamicType() == DynamicType.DYNAMIC) {
//                    orderBy = this.dynamicSetIf(this.paramName(orderCondition.getParamName(), prefix), condition);
//                } else {
//                    orderBy = this.guiseDynamicConditionIf(condition);
//                }
//                sb.append(orderBy);
//            });
//        } else {
//            if (collect.size() != 1) {
//                throw new EasyBatisException("无法处理两个orderBy");
//            } else {
//                ParamMapping paramMeta = collect.get(0);
//                if (paramMeta.getDynamicType() == DynamicType.DYNAMIC) {
//                    sb.append("${").append(this.paramName(paramMeta.getParamName(), multi && paramMeta.hasParent() ? paramMeta.getParentParamName() : null)).append("}");
//                } else {
//                    sb.append(paramMeta.getColumnName());
//                }
//            }
//        }
//        return sb.toString();
//    }
//}
