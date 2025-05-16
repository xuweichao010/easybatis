package cn.onetozero.easybatis.enums;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/12/2 14:47
 */
public enum Position {

    COMMAND(10001),
    SET(20001),
    FROM(20002),
    CONDITION(30001),
    GROUP(40001),
    HAVING(40002),
    ORDER(50001),
    OFFSET(60001),
    LIMIT(60002);

    public final int index;

    Position(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
