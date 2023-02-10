package com.xwc.open.easybatis.exceptions;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 15:19
 */
public class EasyMybatisUnsupportedOperationException extends EasyMybatisException {

    public EasyMybatisUnsupportedOperationException() {
        super("EasyMybatis还未支持该操作");
    }

    public EasyMybatisUnsupportedOperationException(String message) {
        super(message);
    }

    public EasyMybatisUnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyMybatisUnsupportedOperationException(Throwable cause) {
        super(cause);
    }

    protected EasyMybatisUnsupportedOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
