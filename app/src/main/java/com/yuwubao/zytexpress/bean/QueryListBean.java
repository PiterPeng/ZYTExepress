package com.yuwubao.zytexpress.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Peng on 2017/4/24
 * e-mail: phlxplus@163.com
 * description:
 */

public class QueryListBean extends BaseBean {

    /**
     * result : {"content":[{"orderId":3842,"orderNo":"WWBDC170422000587C001","status":"待提货","quantity":1,
     * "itemCode":"G220200021","itemName":"KF-25G/EL13(天阔) 室内机","subFaceOrderNo":null,"faceNo":""},{"orderId":3843,
     * "orderNo":"WWBDC170422000584C001","status":"待提货","quantity":1,"itemCode":"G352200005","itemName":"KFR-35W45BP
     * (配3米铜连接管) 室外机","subFaceOrderNo":null,"faceNo":""},{"orderId":3842,"orderNo":"WWBDC170422000587C001",
     * "status":"待提货","quantity":1,"itemCode":"1240257575","itemName":"KF-25W0331(配3米铜连接管) 室外机",
     * "subFaceOrderNo":null,"faceNo":""},{"orderId":3843,"orderNo":"WWBDC170422000584C001","status":"待提货",
     * "quantity":1,"itemCode":"G252200098","itemName":"KFRd-35G/PO13BpA 室内机 变频","subFaceOrderNo":null,"faceNo":""},
     * {"orderId":3844,"orderNo":"WWBDC170422000583C001","status":"待提货","quantity":1,"itemCode":"G322000024",
     * "itemName":"KFR-26W48BP(配3米铜连接管) 室外机 变频","subFaceOrderNo":null,"faceNo":""},{"orderId":3845,
     * "orderNo":"WWBDC170422000586C001","status":"待提货","quantity":1,"itemCode":"G352200005","itemName":"KFR-35W45BP
     * (配3米铜连接管) 室外机","subFaceOrderNo":null,"faceNo":""},{"orderId":3844,"orderNo":"WWBDC170422000583C001",
     * "status":"待提货","quantity":1,"itemCode":"G247100029","itemName":"KFRd-26G/PO13BpA 室内机 变频",
     * "subFaceOrderNo":null,"faceNo":""},{"orderId":3845,"orderNo":"WWBDC170422000586C001","status":"待提货",
     * "quantity":1,"itemCode":"G252200098","itemName":"KFRd-35G/PO13BpA 室内机 变频","subFaceOrderNo":null,"faceNo":""},
     * {"orderId":3846,"orderNo":"WWBDC170422000585C001","status":"待提货","quantity":1,"itemCode":"1240257578",
     * "itemName":"KFR-25W0331(配3米铜连接管) 室外机","subFaceOrderNo":null,"faceNo":""},{"orderId":3846,
     * "orderNo":"WWBDC170422000585C001","status":"待提货","quantity":1,"itemCode":"G220200016",
     * "itemName":"KFRd-25G/EL13(天阔) 室内机","subFaceOrderNo":null,"faceNo":""}],"totalPages":4,"totalElements":40,
     * "number":0,"size":10,"first":true,"last":false}
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
         * content : [{"orderId":3842,"orderNo":"WWBDC170422000587C001","status":"待提货","quantity":1,
         * "itemCode":"G220200021","itemName":"KF-25G/EL13(天阔) 室内机","subFaceOrderNo":null,"faceNo":""},
         * {"orderId":3843,"orderNo":"WWBDC170422000584C001","status":"待提货","quantity":1,"itemCode":"G352200005",
         * "itemName":"KFR-35W45BP(配3米铜连接管) 室外机","subFaceOrderNo":null,"faceNo":""},{"orderId":3842,
         * "orderNo":"WWBDC170422000587C001","status":"待提货","quantity":1,"itemCode":"1240257575",
         * "itemName":"KF-25W0331(配3米铜连接管) 室外机","subFaceOrderNo":null,"faceNo":""},{"orderId":3843,
         * "orderNo":"WWBDC170422000584C001","status":"待提货","quantity":1,"itemCode":"G252200098",
         * "itemName":"KFRd-35G/PO13BpA 室内机 变频","subFaceOrderNo":null,"faceNo":""},{"orderId":3844,
         * "orderNo":"WWBDC170422000583C001","status":"待提货","quantity":1,"itemCode":"G322000024",
         * "itemName":"KFR-26W48BP(配3米铜连接管) 室外机 变频","subFaceOrderNo":null,"faceNo":""},{"orderId":3845,
         * "orderNo":"WWBDC170422000586C001","status":"待提货","quantity":1,"itemCode":"G352200005",
         * "itemName":"KFR-35W45BP(配3米铜连接管) 室外机","subFaceOrderNo":null,"faceNo":""},{"orderId":3844,
         * "orderNo":"WWBDC170422000583C001","status":"待提货","quantity":1,"itemCode":"G247100029",
         * "itemName":"KFRd-26G/PO13BpA 室内机 变频","subFaceOrderNo":null,"faceNo":""},{"orderId":3845,
         * "orderNo":"WWBDC170422000586C001","status":"待提货","quantity":1,"itemCode":"G252200098",
         * "itemName":"KFRd-35G/PO13BpA 室内机 变频","subFaceOrderNo":null,"faceNo":""},{"orderId":3846,
         * "orderNo":"WWBDC170422000585C001","status":"待提货","quantity":1,"itemCode":"1240257578",
         * "itemName":"KFR-25W0331(配3米铜连接管) 室外机","subFaceOrderNo":null,"faceNo":""},{"orderId":3846,
         * "orderNo":"WWBDC170422000585C001","status":"待提货","quantity":1,"itemCode":"G220200016",
         * "itemName":"KFRd-25G/EL13(天阔) 室内机","subFaceOrderNo":null,"faceNo":""}]
         * totalPages : 4
         * totalElements : 40
         * number : 0
         * size : 10
         * first : true
         * last : false
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
             * orderId : 3842
             * orderNo : WWBDC170422000587C001
             * status : 待提货
             * quantity : 1
             * itemCode : G220200021
             * itemName : KF-25G/EL13(天阔) 室内机
             * subFaceOrderNo : null
             * faceNo :
             */

            private int orderId;
            private String orderNo;
            @SerializedName("status")
            private String statusX;
            private int quantity;
            private String itemCode;
            private String itemName;
            private Object subFaceOrderNo;
            private String faceNo;

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getStatusX() {
                return statusX;
            }

            public void setStatusX(String statusX) {
                this.statusX = statusX;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
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

            public Object getSubFaceOrderNo() {
                return subFaceOrderNo;
            }

            public void setSubFaceOrderNo(Object subFaceOrderNo) {
                this.subFaceOrderNo = subFaceOrderNo;
            }

            public String getFaceNo() {
                return faceNo;
            }

            public void setFaceNo(String faceNo) {
                this.faceNo = faceNo;
            }
        }
    }
}
