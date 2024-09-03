package cn.onetozero.easy.parse.model;

/**
 * 类描述：用于存储数据库逻辑删除的字段
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:50
 */
public class LogicAttribute extends ModelAttribute{

    private String valid;

    private String invalid;




    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }


}
