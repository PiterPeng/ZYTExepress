package com.yuwubao.zytexpress.bean;

/**
 * Created by Peng on 2017/9/20
 * e-mail: phlxplus@163.com
 * description:
 */

public class DetailBean {

    private String title, content;

    public DetailBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
