package com.xwc.esbatis.anno.enums;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:02
 * 业务：
 * 功能：
 */
public enum ConditionEnum {
    EQUEL("等于", " AND %s = %s "),

    NOT_EQUEL("不等于", " AND %s != %s "),

    GT("大于号", " AND %s < %s "),

    LT("小于号", " AND %s > %s "),

    GTQ("大于等于", " AND %s <= %s "),

    LTQ("小于等于", " AND %s >= %s "),

    LIKE("全模糊匹配", " AND %s LIKE CONCAT('%%', %s, '%%') "),

    LEFT_LIKE("左模糊匹配", " AND %s LIKE CONCAT('%%', %s) "),

    RIGHT_LIKE("右模糊匹配", " AND %s LIKE CONCAT( %s, '%%') "),

    NOT_LIKE("全模糊匹配", " AND %s NOT LIKE CONCAT('%%', %s, '%%') "),

    NOT_LEFT_LIKE("左模糊匹配", " AND %s  NOT LIKE CONCAT('%%', %s) "),

    NOT_RIGHT_LIKE("右模糊匹配", " AND %s NOT LIKE CONCAT(%s, '%%') "),

    IN("包含", " AND %s IN <foreach item='item' index='index' collection='%s'  open='(' separator=',' close=')'> #{item} </foreach>"),

    NOT_IN("不包含", " AND %s NOT IN <foreach item='item' index='index' collection='%s'  open='(' separator=',' close=')'> #{item} </foreach>"),

    GROUP_BY("分组", "  GROUP By "),

    OEDER_ASC("排序", " ORDER BY %s asc"),

    OEDER_DESC("排序", " ORDER BY %s desc"),

    LIMIT_START("分页", " LIMIT "),

    LIMIT_OFFSET("分页", " , "),

    IS_NULL("为空", " AND %s IS NULL"),

    NOT_NULL("不为空", " AND %s IS NOT NULL"),

    SET("赋值", "%s = %s");

    private String symbol;
    private String description;

    ConditionEnum(String description, String symbol) {
        this.symbol = symbol;
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }


    public String getDescription() {
        return description;
    }


}
