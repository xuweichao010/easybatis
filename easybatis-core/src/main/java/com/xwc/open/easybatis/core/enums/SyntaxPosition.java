package com.xwc.open.easybatis.core.enums;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/7/25 17:43
 */
public enum SyntaxPosition {
    SELECT(10001),
    UPDATE(10002),
    DELETE(10003),
    INSERT(1004),
    SET(20001),
    FROM(20002),
    CONDITION(30001),
    GROUP(40001),
    HAVING(40002),
    ORDER(50001),
    OFFSET(60001),
    LIMIT(60002);

    private int index;

    SyntaxPosition(int index) {
        this.index = index;
    }
}
