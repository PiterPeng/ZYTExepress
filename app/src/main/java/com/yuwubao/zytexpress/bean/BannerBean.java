package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/8/23
 * e-mail: phlxplus@163.com
 * description:
 */

public class BannerBean extends BaseBean {


    /**
     * message : null
     * result : ["yuwubao.oss-cn-shanghai.aliyuncs.com/2017-08-22/b3a42aa58d074084ad54fa96ffb5afb9.png","yuwubao
     * .oss-cn-shanghai.aliyuncs.com/2017-08-22/5152bc4260714aa1a0fb0dbe762c0c5d.png"]
     * returnCode : null
     */

    private Object returnCode;
    private List<String> result;

    public Object getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Object returnCode) {
        this.returnCode = returnCode;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
