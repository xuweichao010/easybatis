package cn.onetozero.easybatis.model;

import cn.onetozero.easy.annotations.conditions.Equal;
import lombok.Data;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/1 18:00
 */
@Data
public class NormalUserUpdateObject {

    @Equal
    private String id;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 用户名
     */
    private String name;

    public static NormalUserUpdateObject createName(String id, String name, String orgCode, String orgName) {
        NormalUserUpdateObject tar = new NormalUserUpdateObject();
        tar.setId(id);
        tar.name = name;
        tar.orgCode = orgCode;
        tar.orgName = orgName;
        return tar;
    }


}
