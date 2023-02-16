package cn.onetozero.easybatis.exceptions;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/12/2 10:49
 */
public class ParamCheckException extends CheckException {
    public ParamCheckException() {
    }

    public ParamCheckException(String message) {
        super(message);
    }

    public ParamCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamCheckException(Throwable cause) {
        super(cause);
    }

    public ParamCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
