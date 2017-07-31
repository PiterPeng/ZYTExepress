package com.yuwubao.zytexpress.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Peng on 2017/4/26
 * e-mail: phlxplus@163.com
 * description:
 */

public class HistoryBean extends BaseBean {

    /**
     * result : {"content":[{"id":12199,"orderNo":"HB-WWBDC170425001845C001","customerOrderNo":"SORD20170425489974",
     * "shipperName":"武汉RDC总仓","requireDeliveryDate":"2017-04-25 15:59:13","requireArrivalDate":"2017-04-25
     * 15:59:13","acceptTime":"2017-04-25 15:59:13","itemCode":"G221300070","itemName":"KFRd-35G/FC23  室内机",
     * "packageUnit":"台","color":"","quantity":1,"packageQuantity":"","acceptNum":0,"stopDay":1,"volume":0.09,
     * "groosWeight":12,"status":"待提货"}],"totalPages":1,"totalElements":1,"number":0,"size":10,"first":true,"last":true}
     * returnCode : null
     */

    private ResultBean result;
    private Object returnCode;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Object getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Object returnCode) {
        this.returnCode = returnCode;
    }

    public static class ResultBean {
        /**
         * content : [{"id":12199,"orderNo":"HB-WWBDC170425001845C001","customerOrderNo":"SORD20170425489974",
         * "shipperName":"武汉RDC总仓","requireDeliveryDate":"2017-04-25 15:59:13","requireArrivalDate":"2017-04-25
         * 15:59:13","acceptTime":"2017-04-25 15:59:13","itemCode":"G221300070","itemName":"KFRd-35G/FC23  室内机",
         * "packageUnit":"台","color":"","quantity":1,"packageQuantity":"","acceptNum":0,"stopDay":1,"volume":0.09,
         * "groosWeight":12,"status":"待提货"}]
         * totalPages : 1
         * totalElements : 1
         * number : 0
         * size : 10
         * first : true
         * last : true
         */

        private int totalPages;
        private int totalElements;
        private int number;
        private int size;
        private boolean first;
        private boolean last;
        private List<ContentBean> content;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * id : 12199
             * orderNo : HB-WWBDC170425001845C001
             * customerOrderNo : SORD20170425489974
             * shipperName : 武汉RDC总仓
             * requireDeliveryDate : 2017-04-25 15:59:13
             * requireArrivalDate : 2017-04-25 15:59:13
             * acceptTime : 2017-04-25 15:59:13
             * itemCode : G221300070
             * itemName : KFRd-35G/FC23  室内机
             * packageUnit : 台
             * color :
             * quantity : 1
             * packageQuantity :
             * acceptNum : 0
             * stopDay : 1
             * volume : 0.09
             * groosWeight : 12
             * status : 待提货
             */

            private int id;
            private String orderNo;
            private String customerOrderNo;
            private String shipperName;
            private String requireDeliveryDate;
            private String requireArrivalDate;
            private String acceptTime;
            private String itemCode;
            private String itemName;
            private String packageUnit;
            private String color;
            private int quantity;
            private String packageQuantity;
            private int acceptNum;
            private int stopDay;
            private double volume;
            private double groosWeight;
            @SerializedName("status")
            private String statusX;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getCustomerOrderNo() {
                return customerOrderNo;
            }

            public void setCustomerOrderNo(String customerOrderNo) {
                this.customerOrderNo = customerOrderNo;
            }

            public String getShipperName() {
                return shipperName;
            }

            public void setShipperName(String shipperName) {
                this.shipperName = shipperName;
            }

            public String getRequireDeliveryDate() {
                return requireDeliveryDate;
            }

            public void setRequireDeliveryDate(String requireDeliveryDate) {
                this.requireDeliveryDate = requireDeliveryDate;
            }

            public String getRequireArrivalDate() {
                return requireArrivalDate;
            }

            public void setRequireArrivalDate(String requireArrivalDate) {
                this.requireArrivalDate = requireArrivalDate;
            }

            public String getAcceptTime() {
                return acceptTime;
            }

            public void setAcceptTime(String acceptTime) {
                this.acceptTime = acceptTime;
            }

            public String getItemCode() {
                return itemCode;
            }

            public void setItemCode(String itemCode) {
                this.itemCode = itemCode;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getPackageUnit() {
                return packageUnit;
            }

            public void setPackageUnit(String packageUnit) {
                this.packageUnit = packageUnit;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getPackageQuantity() {
                return packageQuantity;
            }

            public void setPackageQuantity(String packageQuantity) {
                this.packageQuantity = packageQuantity;
            }

            public int getAcceptNum() {
                return acceptNum;
            }

            public void setAcceptNum(int acceptNum) {
                this.acceptNum = acceptNum;
            }

            public int getStopDay() {
                return stopDay;
            }

            public void setStopDay(int stopDay) {
                this.stopDay = stopDay;
            }

            public double getVolume() {
                return volume;
            }

            public void setVolume(double volume) {
                this.volume = volume;
            }

            public double getGroosWeight() {
                return groosWeight;
            }

            public void setGroosWeight(double groosWeight) {
                this.groosWeight = groosWeight;
            }

            public String getStatusX() {
                return statusX;
            }

            public void setStatusX(String statusX) {
                this.statusX = statusX;
            }
        }
    }
}
