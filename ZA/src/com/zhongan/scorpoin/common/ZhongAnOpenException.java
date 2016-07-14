package com.zhongan.scorpoin.common;

/**
 * 客户端异常
 * 
 * @author linwusheng 2015年11月5日 下午9:50:44
 */
public class ZhongAnOpenException extends Exception {

    private static final long serialVersionUID = -2741149959687543581L;

    private String            errCode;

    private String            errMsg;

    public ZhongAnOpenException() {
        super();
    }

    public ZhongAnOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZhongAnOpenException(String message) {
        super(message);
    }

    public ZhongAnOpenException(Throwable cause) {
        super(cause);
    }

    public ZhongAnOpenException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ZhongAnOpenException(String errCode, String errMsg, Throwable cause) {
        super(errCode + ":" + errMsg, cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
