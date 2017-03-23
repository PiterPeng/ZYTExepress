package com.yuwubao.zytexpress.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Peng on 2017/3/23
 * e-mail: phlxplus@163.com
 * description: 查询商品
 */

public class QueryBean extends BaseBean {

    /**
     * result : {"id":26,"orderId":23,"orderNo":"FH20170308000254826","subFaceOrderNo":null,
     * "itemCode":"KUP001010101010100004","itemName":"彩电-49寸-D49A620U 黑色","grossWeight":22,
     * "volume":0.1643,"packageUnit":null,"length":1.32,"width":0.83,"height":0.15,"color":null,
     * "price":0,"cargoValue":2699,"remarks":null,"qty":1,"packageQuantity":null,
     * "productCode":"99999","oneCode":"","status":10,"acceptNum":0,"carNo":"","oldOrderNo":""}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 26
         * orderId : 23
         * orderNo : FH20170308000254826
         * subFaceOrderNo : null
         * itemCode : KUP001010101010100004
         * itemName : 彩电-49寸-D49A620U 黑色
         * grossWeight : 22
         * volume : 0.1643
         * packageUnit : null
         * length : 1.32
         * width : 0.83
         * height : 0.15
         * color : null
         * price : 0
         * cargoValue : 2699
         * remarks : null
         * qty : 1
         * packageQuantity : null
         * productCode : 99999
         * oneCode :
         * status : 10
         * acceptNum : 0
         * carNo :
         * oldOrderNo :
         */

        private int id;
        private int orderId;
        private String orderNo;
        private Object subFaceOrderNo;
        private String itemCode;
        private String itemName;
        private int grossWeight;
        private double volume;
        private Object packageUnit;
        private double length;
        private double width;
        private double height;
        private Object color;
        private int price;
        private int cargoValue;
        private Object remarks;
        private int qty;
        private Object packageQuantity;
        private String productCode;
        private String oneCode;
        @SerializedName("status")
        private int statusX;
        private int acceptNum;
        private String carNo;
        private String oldOrderNo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public Object getSubFaceOrderNo() {
            return subFaceOrderNo;
        }

        public void setSubFaceOrderNo(Object subFaceOrderNo) {
            this.subFaceOrderNo = subFaceOrderNo;
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

        public int getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(int grossWeight) {
            this.grossWeight = grossWeight;
        }

        public double getVolume() {
            return volume;
        }

        public void setVolume(double volume) {
            this.volume = volume;
        }

        public Object getPackageUnit() {
            return packageUnit;
        }

        public void setPackageUnit(Object packageUnit) {
            this.packageUnit = packageUnit;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public Object getColor() {
            return color;
        }

        public void setColor(Object color) {
            this.color = color;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCargoValue() {
            return cargoValue;
        }

        public void setCargoValue(int cargoValue) {
            this.cargoValue = cargoValue;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public Object getPackageQuantity() {
            return packageQuantity;
        }

        public void setPackageQuantity(Object packageQuantity) {
            this.packageQuantity = packageQuantity;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getOneCode() {
            return oneCode;
        }

        public void setOneCode(String oneCode) {
            this.oneCode = oneCode;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public int getAcceptNum() {
            return acceptNum;
        }

        public void setAcceptNum(int acceptNum) {
            this.acceptNum = acceptNum;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getOldOrderNo() {
            return oldOrderNo;
        }

        public void setOldOrderNo(String oldOrderNo) {
            this.oldOrderNo = oldOrderNo;
        }
    }
}
