package com.yuwubao.zytexpress.bean;

/**
 * Created by Peng on 2017/3/21
 * e-mail: phlxplus@163.com
 * description:
 */

public class StatusBean {

    /**
     * message : null
     * status : 0
     * result : true
     */

    private Object message;
    private int status;
    private boolean result;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
