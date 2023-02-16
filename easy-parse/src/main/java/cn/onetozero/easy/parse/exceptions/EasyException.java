package cn.onetozero.easy.parse.exceptions;

/**
 * 类描述：easy解析中的基础异常
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:36
 */
public class EasyException extends RuntimeException {

    public EasyException() {
        super();
    }

    public EasyException(String message) {
        super(message);
    }

    public EasyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyException(Throwable cause) {
        super(cause);
    }

    protected EasyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
