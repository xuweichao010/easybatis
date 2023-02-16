package cn.onetozero.easybatis.fill;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/3 11:15
 */
public interface FillAttributeHandler {

    default void fillBefore() {
    }

    default void fillAfter() {
    }


    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param fillWrapper 元对象
     */
    void insertFill(String identification, String fillAttribute, FillWrapper fillWrapper);

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param fillWrapper 元对象
     */
    void updateFill(String identification, String fillAttribute, FillWrapper fillWrapper);

}
