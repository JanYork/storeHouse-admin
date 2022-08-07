package com.storehouse.entity;

/**
 * JWT结果存放类
 *
 * @author JanYork
 */
public class JwtResult {
    private boolean success;
    private int errNum;
    private String message;

    public JwtResult(int errNum, boolean success, String message) {
        this.success = success;
        this.errNum = errNum;
        this.message = message;
    }

    /* 封装暂未使用
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    */
}
