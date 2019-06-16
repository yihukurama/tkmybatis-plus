package com.yihukurama.tkmybatisplus.app.exception;

/**
 * 说明： 普通的异常提示
 * @author yihukurma
 * @date Created in 下午 3:13 2019/6/16 0016
 *  modified by autor in 下午 3:13 2019/6/16 0016
 */
public class TipsException extends Exception {
    private static final long serialVersionUID = 1L;

    public TipsException(String message) {
        super(message);
    }

    public TipsException(String message, Throwable e) {
        super(message, e);
    }
}
