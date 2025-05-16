package cn.onetozero.easybatis.model;

import cn.onetozero.easy.annotations.conditions.Equal;
import lombok.Data;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/2 13:22
 */
@Data
public class NormalUserDeleteObject {

    private String id;

    /**
     * 机构编码
     */
    @Equal("org_code")
    private String orgCodeAlias;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 用户名
     */
    private String name;

    public static NormalUserDeleteObject convert(String id, String name, String orgCodeAlias, String orgName) {
        NormalUserDeleteObject deleteObject = new NormalUserDeleteObject();
        deleteObject.setId(id);
        deleteObject.name = name;
        deleteObject.orgCodeAlias = orgCodeAlias;
        deleteObject.orgName = orgName;
        return deleteObject;
    }
}
