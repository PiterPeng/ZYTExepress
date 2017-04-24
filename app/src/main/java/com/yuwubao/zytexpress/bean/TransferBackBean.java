package com.yuwubao.zytexpress.bean;

/**
 * Created by Peng on 2017/4/24
 * e-mail: phlxplus@163.com
 * description:
 */

public class TransferBackBean extends BaseBean {

    /**
     * result : true
     * returnCode : null
     */

    private boolean result;
    private Object returnCode;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Object returnCode) {
        this.returnCode = returnCode;
    }
}
