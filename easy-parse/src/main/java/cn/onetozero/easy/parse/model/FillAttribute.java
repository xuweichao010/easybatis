package cn.onetozero.easy.parse.model;

import cn.onetozero.easy.parse.enums.FillType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:48
 */
public class FillAttribute extends ModelAttribute {

    /**
     * 填充类型  取值有：ADD、MODIFY、CHANGE
     */
    private FillType type;

    /**
     * 填充的标识 默认使用的是属性名称
     */
    private String identification;


    public boolean isUpdateFill() {
        return this.type == FillType.UPDATE || this.type == FillType.INSERT_UPDATE;
    }

    public FillType getType() {
        return type;
    }

    public void setType(FillType type) {
        this.type = type;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
