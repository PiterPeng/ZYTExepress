package com.yuwubao.zytexpress.bean;

/**
 * Created by Peng on 2017/9/24
 * e-mail: phlxplus@163.com
 * description:
 */

public class ScanModeBean {

    /**
     * userId : 111
     * id : 3
     * item : {"scan_sn":"","scan_69":"","scan_buy":"","scan_sale":""}
     */

    private int userId;
    private int id;
    private int index;
    private String scan_sn;
    private String scan_69;
    private String scan_buy;
    private String scan_sale;

    public ScanModeBean(int userId, int id,int index, String scan_sn, String scan_69, String scan_buy, String
            scan_sale) {
        this.userId = userId;
        this.id = id;
        this.index = index;
        this.scan_sn = scan_sn;
        this.scan_69 = scan_69;
        this.scan_buy = scan_buy;
        this.scan_sale = scan_sale;
    }

    public String getScan_sn() {
        return scan_sn;
    }

    public void setScan_sn(String scan_sn) {
        this.scan_sn = scan_sn;
    }

    public String getScan_69() {
        return scan_69;
    }

    public void setScan_69(String scan_69) {
        this.scan_69 = scan_69;
    }

    public String getScan_buy() {
        return scan_buy;
    }

    public void setScan_buy(String scan_buy) {
        this.scan_buy = scan_buy;
    }

    public String getScan_sale() {
        return scan_sale;
    }

    public void setScan_sale(String scan_sale) {
        this.scan_sale = scan_sale;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
