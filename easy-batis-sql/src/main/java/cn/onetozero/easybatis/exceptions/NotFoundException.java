package cn.onetozero.easybatis.exceptions;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/12/2 10:49
 */
public class NotFoundException extends EasyMybatisException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
