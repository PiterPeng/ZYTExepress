package com.yuwubao.zytexpress.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Peng on 2017/3/16
 * e-mail: phlxplus@163.com
 * description: 指定扫描
 */

public class GoodsDetailsBean extends BaseBean {

    /**
     * message : null
     * result : {"content":[{"id":2,"orderId":14,"orderNo":"HB-ZYT4220170000010",
     * "subFaceOrderNo":null,"itemCode":"无锡小天鹅股份有限公司-内销","itemName":"无锡小天鹅股份有限公司-内销",
     * "grossWeight":66,"volume":0.433388,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"","oldOrderNo":"ZYT4220170000001"},{"id":3,"orderId":14,
     * "orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,"itemCode":"合肥美的洗衣机有限公司-内销",
     * "itemName":"合肥美的洗衣机有限公司-内销","grossWeight":68,"volume":0.433388,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":"ZYT4220170000002"},{"id":4,
     * "orderId":14,"orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,
     * "itemCode":"合肥美的洗衣机有限公司-内销","itemName":"合肥美的洗衣机有限公司-内销","grossWeight":68,
     * "volume":0.433388,"packageUnit":null,"length":null,"width":null,"height":null,
     * "color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":10,"acceptNum":0,"carNo":"",
     * "oldOrderNo":"ZYT4220170000003"},{"id":6,"orderId":6,"orderNo":"ZYT4220170000005",
     * "subFaceOrderNo":null,"itemCode":"合肥美的电冰箱有限公司-内销","itemName":"合肥美的电冰箱有限公司-内销",
     * "grossWeight":49,"volume":0.617,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"","oldOrderNo":null},{"id":7,"orderId":7,"orderNo":"ZYT4220170000006",
     * "subFaceOrderNo":null,"itemCode":"合肥美的洗衣机有限公司-内销","itemName":"合肥美的洗衣机有限公司-内销",
     * "grossWeight":33,"volume":0.331934,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"","oldOrderNo":null},{"id":8,"orderId":8,"orderNo":"ZYT4220170000007",
     * "subFaceOrderNo":null,"itemCode":"广东美的制冷设备有限公司","itemName":"广东美的制冷设备有限公司",
     * "grossWeight":46,"volume":0.309309,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"remarks":null,"qty":2,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"","oldOrderNo":null},{"id":9,"orderId":14,"orderNo":"HB-ZYT4220170000010",
     * "subFaceOrderNo":null,"itemCode":"合肥美的洗衣机有限公司-内销","itemName":"合肥美的洗衣机有限公司-内销",
     * "grossWeight":33,"volume":0.338778,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"","oldOrderNo":"ZYT4220170000008"},{"id":10,"orderId":10,
     * "orderNo":"ZYT4220170000009","subFaceOrderNo":null,"itemCode":"合肥美的电冰箱有限公司-内销",
     * "itemName":"合肥美的电冰箱有限公司-内销","grossWeight":33,"volume":0.374398,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":null},{"id":11,"orderId":14,
     * "orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,"itemCode":"合肥美的电冰箱有限公司-内销",
     * "itemName":"合肥美的电冰箱有限公司-内销","grossWeight":67,"volume":0.702563,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":"ZYT4220170000010"},{"id":12,
     * "orderId":14,"orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,
     * "itemCode":"合肥美的电冰箱有限公司-内销","itemName":"合肥美的电冰箱有限公司-内销","grossWeight":49,"volume":0.617,
     * "packageUnit":null,"length":null,"width":null,"height":null,"color":null,"price":null,
     * "cargoValue":null,"remarks":null,"qty":1,"packageQuantity":null,"productCode":null,
     * "oneCode":null,"status":10,"acceptNum":0,"carNo":"","oldOrderNo":"ZYT4220170000011"}],
     * "last":false,"totalPages":12,"totalElements":113,"number":0,"size":10,"sort":null,
     * "first":true,"numberOfElements":10}
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
         * content : [{"id":2,"orderId":14,"orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,
         * "itemCode":"无锡小天鹅股份有限公司-内销","itemName":"无锡小天鹅股份有限公司-内销","grossWeight":66,
         * "volume":0.433388,"packageUnit":null,"length":null,"width":null,"height":null,
         * "color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
         * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
         * "carNo":"","oldOrderNo":"ZYT4220170000001"},{"id":3,"orderId":14,
         * "orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,"itemCode":"合肥美的洗衣机有限公司-内销",
         * "itemName":"合肥美的洗衣机有限公司-内销","grossWeight":68,"volume":0.433388,"packageUnit":null,
         * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
         * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
         * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":"ZYT4220170000002"},{"id":4,
         * "orderId":14,"orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,
         * "itemCode":"合肥美的洗衣机有限公司-内销","itemName":"合肥美的洗衣机有限公司-内销","grossWeight":68,
         * "volume":0.433388,"packageUnit":null,"length":null,"width":null,"height":null,
         * "color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
         * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
         * "carNo":"","oldOrderNo":"ZYT4220170000003"},{"id":6,"orderId":6,
         * "orderNo":"ZYT4220170000005","subFaceOrderNo":null,"itemCode":"合肥美的电冰箱有限公司-内销",
         * "itemName":"合肥美的电冰箱有限公司-内销","grossWeight":49,"volume":0.617,"packageUnit":null,
         * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
         * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
         * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":null},{"id":7,"orderId":7,
         * "orderNo":"ZYT4220170000006","subFaceOrderNo":null,"itemCode":"合肥美的洗衣机有限公司-内销",
         * "itemName":"合肥美的洗衣机有限公司-内销","grossWeight":33,"volume":0.331934,"packageUnit":null,
         * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
         * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
         * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":null},{"id":8,"orderId":8,
         * "orderNo":"ZYT4220170000007","subFaceOrderNo":null,"itemCode":"广东美的制冷设备有限公司",
         * "itemName":"广东美的制冷设备有限公司","grossWeight":46,"volume":0.309309,"packageUnit":null,
         * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
         * "remarks":null,"qty":2,"packageQuantity":null,"productCode":null,"oneCode":null,
         * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":null},{"id":9,"orderId":14,
         * "orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,"itemCode":"合肥美的洗衣机有限公司-内销",
         * "itemName":"合肥美的洗衣机有限公司-内销","grossWeight":33,"volume":0.338778,"packageUnit":null,
         * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
         * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
         * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":"ZYT4220170000008"},{"id":10,
         * "orderId":10,"orderNo":"ZYT4220170000009","subFaceOrderNo":null,
         * "itemCode":"合肥美的电冰箱有限公司-内销","itemName":"合肥美的电冰箱有限公司-内销","grossWeight":33,
         * "volume":0.374398,"packageUnit":null,"length":null,"width":null,"height":null,
         * "color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
         * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
         * "carNo":"","oldOrderNo":null},{"id":11,"orderId":14,"orderNo":"HB-ZYT4220170000010",
         * "subFaceOrderNo":null,"itemCode":"合肥美的电冰箱有限公司-内销","itemName":"合肥美的电冰箱有限公司-内销",
         * "grossWeight":67,"volume":0.702563,"packageUnit":null,"length":null,"width":null,
         * "height":null,"color":null,"price":null,"cargoValue":null,"remarks":null,"qty":1,
         * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
         * "carNo":"","oldOrderNo":"ZYT4220170000010"},{"id":12,"orderId":14,
         * "orderNo":"HB-ZYT4220170000010","subFaceOrderNo":null,"itemCode":"合肥美的电冰箱有限公司-内销",
         * "itemName":"合肥美的电冰箱有限公司-内销","grossWeight":49,"volume":0.617,"packageUnit":null,
         * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
         * "remarks":null,"qty":1,"packageQuantity":null,"productCode":null,"oneCode":null,
         * "status":10,"acceptNum":0,"carNo":"","oldOrderNo":"ZYT4220170000011"}]
         * last : false
         * totalPages : 12
         * totalElements : 113
         * number : 0
         * size : 10
         * sort : null
         * first : true
         * numberOfElements : 10
         */

        private boolean last;
        private int totalPages;
        private int totalElements;
        private int number;
        private int size;
        private Object sort;
        private boolean first;
        private int numberOfElements;
        private List<ContentBean> content;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

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

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * id : 2
             * orderId : 14
             * orderNo : HB-ZYT4220170000010
             * subFaceOrderNo : null
             * itemCode : 无锡小天鹅股份有限公司-内销
             * itemName : 无锡小天鹅股份有限公司-内销
             * grossWeight : 66
             * volume : 0.433388
             * packageUnit : null
             * length : null
             * width : null
             * height : null
             * color : null
             * price : null
             * cargoValue : null
             * remarks : null
             * qty : 1
             * packageQuantity : null
             * productCode : null
             * oneCode : null
             * status : 10
             * acceptNum : 0
             * carNo :
             * oldOrderNo : ZYT4220170000001
             * "scanMode": 2 //扫描模式 1 盲扫 2 指定扫描
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
            private Object length;
            private Object width;
            private Object height;
            private Object color;
            private Object price;
            private Object cargoValue;
            private Object remarks;
            private int qty;
            private Object packageQuantity;
            private Object productCode;
            private Object oneCode;
            @SerializedName("status")
            private int statusX;
            private int acceptNum;
            private String carNo;
            private String oldOrderNo;
            private String scanMode;

            public String getScanMode() {
                return scanMode;
            }

            public void setScanMode(String scanMode) {
                this.scanMode = scanMode;
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

            public Object getLength() {
                return length;
            }

            public void setLength(Object length) {
                this.length = length;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
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

            public Object getProductCode() {
                return productCode;
            }

            public void setProductCode(Object productCode) {
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
}
