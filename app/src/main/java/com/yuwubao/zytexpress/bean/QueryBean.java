package com.yuwubao.zytexpress.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Peng on 2017/3/23
 * e-mail: phlxplus@163.com
 * description: 查询商品
 */

public class QueryBean extends BaseBean implements Serializable {

    /**
     * result : {"id":69,"orderId":50,"orderNo":"ZYT4220170000005","subFaceOrderNo":"SBD0009-1","itemCode":"电冰箱",
     * "itemName":"电冰箱","groosWeight":0,"volume":0.617,"packageUnit":null,"length":null,"weight":49,"height":null,
     * "color":null,"price":null,"cargoValue":null,"note":"","quantity":null,"acceptNum":0,"packageQuantity":null,
     * "productCode":"SN7832627554782","oneCode":null,"status":70,"carNo":"CAR4782","oldOrderNo":"","scanType":"",
     * "statusName":"理论出库"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * id : 69
         * orderId : 50
         * orderNo : ZYT4220170000005
         * subFaceOrderNo : SBD0009-1
         * itemCode : 电冰箱
         * itemName : 电冰箱
         * groosWeight : 0
         * volume : 0.617
         * packageUnit : null
         * length : null
         * weight : 49
         * height : null
         * color : null
         * price : null
         * cargoValue : null
         * note :
         * quantity : null
         * acceptNum : 0
         * packageQuantity : null
         * productCode : SN7832627554782
         * oneCode : null
         * status : 70
         * carNo : CAR4782
         * oldOrderNo :
         * scanType :
         * statusName : 理论出库
         */

        private int id;
        private int orderId;
        private String orderNo;
        private String subFaceOrderNo;
        private String itemCode;
        private String itemName;
        private double groosWeight;
        private double volume;
        private Object packageUnit;
        private Object length;
        private double weight;
        private Object height;
        private Object color;
        private Object price;
        private Object cargoValue;
        private String note;
        private Object quantity;
        private int acceptNum;
        private Object packageQuantity;
        private String productCode;
        private Object oneCode;
        @SerializedName("status")
        private int statusX;
        private String carNo;
        private String oldOrderNo;
        private String scanType;
        private String statusName;
        private String itemNo;

        public String getItemNo() {
            return itemNo;
        }

        public void setItemNo(String itemNo) {
            this.itemNo = itemNo;
        }

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

        public String getSubFaceOrderNo() {
            return subFaceOrderNo;
        }

        public void setSubFaceOrderNo(String subFaceOrderNo) {
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

        public double getGroosWeight() {
            return groosWeight;
        }

        public void setGroosWeight(double groosWeight) {
            this.groosWeight = groosWeight;
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

        public Object getLength() {
            return length;
        }

        public void setLength(Object length) {
            this.length = length;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public Object getHeight() {
            return height;
        }

        public void setHeight(Object height) {
            this.height = height;
        }

        public Object getColor() {
            return color;
        }

        public void setColor(Object color) {
            this.color = color;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getCargoValue() {
            return cargoValue;
        }

        public void setCargoValue(Object cargoValue) {
            this.cargoValue = cargoValue;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Object getQuantity() {
            return quantity;
        }

        public void setQuantity(Object quantity) {
            this.quantity = quantity;
        }

        public int getAcceptNum() {
            return acceptNum;
        }

        public void setAcceptNum(int acceptNum) {
            this.acceptNum = acceptNum;
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

        public Object getOneCode() {
            return oneCode;
        }

        public void setOneCode(Object oneCode) {
            this.oneCode = oneCode;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
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

        public String getScanType() {
            return scanType;
        }

        public void setScanType(String scanType) {
            this.scanType = scanType;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }
    }
}
