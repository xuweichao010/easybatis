package cn.onetozero.easybatis.exceptions;

import cn.onetozero.easy.parse.exceptions.EasyException;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/12 15:20
 */
public class EasyMybatisException extends EasyException {

    public EasyMybatisException() {
        super();
    }

    public EasyMybatisException(String message) {
        super(message);
    }

    public EasyMybatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyMybatisException(Throwable cause) {
        super(cause);
    }

    protected EasyMybatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
