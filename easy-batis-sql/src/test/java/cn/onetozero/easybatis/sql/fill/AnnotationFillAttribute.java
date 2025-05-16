package cn.onetozero.easybatis.sql.fill;

import cn.onetozero.easybatis.fill.FillAttributeHandler;
import cn.onetozero.easybatis.fill.FillWrapper;

import java.util.Date;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/3 13:26
 */
public class AnnotationFillAttribute implements FillAttributeHandler {


    @Override
    public void insertFill(String identification, String fillAttribute, FillWrapper fillWrapper) {
        if (identification.equals("createTime") || identification.equals("updateTime")) {
            fillWrapper.setValue(fillAttribute, new Date());
        } else if (identification.equals("createId")) {
            fillWrapper.setValue("createId", "-1");
        } else if (identification.equals("createName")) {
            fillWrapper.setValue("createName", "createName");
        }
    }

    @Override
    public void updateFill(String identification, String fillAttribute, FillWrapper fillWrapper) {
        if (identification.equals("updateTime")) {
            fillWrapper.setValue(fillAttribute, new Date());
        } else if (identification.equals("updateId")) {
            fillWrapper.setValue("updateId", "-2");
        } else if (identification.equals("updateName")) {
            fillWrapper.setValue("updateName", "updateName");
        }
    }
}
