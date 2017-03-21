package com.yuwubao.zytexpress.bean;

import java.io.Serializable;

/**
 * Created by mhdt on 2016/11/6.
 * update
 */

public class BaseBean implements Serializable {
    private int status;//
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
