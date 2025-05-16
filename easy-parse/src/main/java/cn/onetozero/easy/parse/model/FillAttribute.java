package cn.onetozero.easy.parse.model;

import cn.onetozero.easy.annotations.enums.FillType;
import lombok.*;

/**
 * 类描述：
 *
 * @author 徐卫超 (cc)
 * @since 2022/11/24 14:48
 */
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
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

}
