//package com.xwc.open.easybatis.core.mysql.snippet;
//
//import com.xwc.open.easybatis.core.enums.ConditionType;
//import com.xwc.open.easybatis.core.excp.EasyBatisException;
//import com.xwc.open.easybatis.core.model.MethodMeta;
//import com.xwc.open.easybatis.core.model.ParamMapping;
//import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;
//import com.xwc.open.easybatis.core.support.snippet.PageSnippet;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * 作者：徐卫超 cc
// * 时间：2021/1/5
// * 描述：分页
// */
//public class DefaultPageSnippet implements PageSnippet, MyBatisOrSqlTemplate {
//    Set<ConditionType> orderCondition = Stream.of(ConditionType.OFFSET, ConditionType.START).collect(Collectors.toSet());
//
//    @Override
//    public String apply(MethodMeta methodMeta) {
//        ArrayList<ParamMapping> page = new ArrayList<>();
//        for (ParamMapping paramMeta : methodMeta.getParamMetaList()) {
//            if (orderCondition.contains(paramMeta.getCondition())) {
//                page.add(paramMeta);
//            } else {
//                if (paramMeta.isMultiCondition()) {
//                    paramMeta.getChildList().forEach(item -> {
//                        if (orderCondition.contains(item.getCondition())) {
//                            page.add(item);
//                        }
//                    });
//                }
//            }
//        }
//        if (page.isEmpty()) {
//            return null;
//        } else if (page.size() > 2) {
//            throw new EasyBatisException("过多的分页注解");
//        } else {
//            StringBuilder sb = new StringBuilder();
//            boolean multi = methodMeta.getParamMetaList().size() > 1;
//            List<ParamMapping> startList = page.stream().filter(paramMeta -> paramMeta.getCondition() == ConditionType.START).collect(Collectors.toList());
//            if (startList.size() != 1) {
//                throw new EasyBatisException("分页错误 无法匹配下标数据");
//            }
//            ParamMapping start = startList.get(0);
//            sb.append(this.mybatisParam(start.getParamName(), multi ? start.getParentParamName() : null));
//            List<ParamMapping> offsetList = page.stream().filter(paramMeta -> paramMeta.getCondition() == ConditionType.OFFSET).collect(Collectors.toList());
//            if (!offsetList.isEmpty()) {
//                ParamMapping offset = offsetList.get(0);
//                sb.append(", ").append(this.mybatisParam(offset.getParamName(), multi ? offset.getParentParamName() : null));
//            }
//            return sb.toString();
//        }
//    }
//}
