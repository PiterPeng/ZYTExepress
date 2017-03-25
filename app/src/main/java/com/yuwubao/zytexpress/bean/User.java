package com.yuwubao.zytexpress.bean;

import com.simple.util.db.annotation.SimpleColumn;
import com.simple.util.db.annotation.SimpleId;
import com.simple.util.db.annotation.SimpleTable;

/**
 * 用户
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
@SimpleTable(name = "t_user")
public class User {

    /**
     * id : 12
     * name : guest
     * pwd : guest
     * account : guest
     * companyId : 10
     * createBy :
     * createTime : 2017-03-10 03:27:13
     * companyName :
     */
    @SimpleId
    @SimpleColumn(name = "id")
    private int id;
    @SimpleColumn(name = "name")
    private String name;
    @SimpleColumn(name = "pwd")
    private String pwd;
    @SimpleColumn(name = "account")
    private String account;
    @SimpleColumn(name = "companyId")
    private int companyId;
    @SimpleColumn(name = "createBy")
    private String createBy;
    @SimpleColumn(name = "createTime")
    private String createTime;
    @SimpleColumn(name = "companyName")
    private String companyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
