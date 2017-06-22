package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/6/22
 * e-mail: phlxplus@163.com
 * description:
 */

public class ProjectListBack extends BaseBean {

    /**
     * result : [{"id":1,"customerName":"速必达","projectName":"B2C省配项目","tradeStyle":70,"serviceType":0,"payStyle":55,
     * "mobile":"234233345234","address":"23232345234","sendSMS":0,"takeMode":58,"category":0,"zipCode":"",
     * "scanType":66,"scanMode":119,"faceType":0,"deptCode":"WHZYT"},{"id":2,"customerName":"安得",
     * "projectName":"B2C武汉城配","tradeStyle":70,"serviceType":0,"payStyle":54,"mobile":"d2134","address":"12345d",
     * "sendSMS":0,"takeMode":59,"category":0,"zipCode":"","scanType":67,"scanMode":120,"faceType":0,
     * "deptCode":"WHZYT"}]
     * returnCode : null
     */

    private Object returnCode;
    private List<ResultBean> result;

    public Object getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Object returnCode) {
        this.returnCode = returnCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * customerName : 速必达
         * projectName : B2C省配项目
         * tradeStyle : 70
         * serviceType : 0
         * payStyle : 55
         * mobile : 234233345234
         * address : 23232345234
         * sendSMS : 0
         * takeMode : 58
         * category : 0
         * zipCode :
         * scanType : 66
         * scanMode : 119
         * faceType : 0
         * deptCode : WHZYT
         */

        private int id;
        private String customerName;
        private String projectName;
        private int tradeStyle;
        private int serviceType;
        private int payStyle;
        private String mobile;
        private String address;
        private int sendSMS;
        private int takeMode;
        private int category;
        private String zipCode;
        private int scanType;
        private int scanMode;
        private int faceType;
        private String deptCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public int getTradeStyle() {
            return tradeStyle;
        }

        public void setTradeStyle(int tradeStyle) {
            this.tradeStyle = tradeStyle;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public int getPayStyle() {
            return payStyle;
        }

        public void setPayStyle(int payStyle) {
            this.payStyle = payStyle;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getSendSMS() {
            return sendSMS;
        }

        public void setSendSMS(int sendSMS) {
            this.sendSMS = sendSMS;
        }

        public int getTakeMode() {
            return takeMode;
        }

        public void setTakeMode(int takeMode) {
            this.takeMode = takeMode;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public int getScanType() {
            return scanType;
        }

        public void setScanType(int scanType) {
            this.scanType = scanType;
        }

        public int getScanMode() {
            return scanMode;
        }

        public void setScanMode(int scanMode) {
            this.scanMode = scanMode;
        }

        public int getFaceType() {
            return faceType;
        }

        public void setFaceType(int faceType) {
            this.faceType = faceType;
        }

        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }
    }
}
