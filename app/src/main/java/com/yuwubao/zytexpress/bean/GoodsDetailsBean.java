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
     * result : [{"id":1,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TBY112094502-01-01","itemName":"小天鹅洗衣机TD80-Mute160WDX白色","groosWeight":null,
     * "volume":0.51,"packageUnit":null,"length":null,"width":null,"height":null,"color":null,
     * "price":null,"cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":20,"acceptNum":0,"carNo":"CX1644",
     * "oldOrderNo":"ZYT4220170000001","scanMode":2},{"id":2,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB113916368-01-01",
     * "itemName":"小天鹅洗衣机TG80-1229EDS","groosWeight":null,"volume":0.408,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "note":null,"quantity":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000002","scanMode":2},
     * {"id":3,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB113562787-01-01","itemName":"奇帅洗衣机XQB42-426活力蓝","groosWeight":null,
     * "volume":0.21,"packageUnit":null,"length":null,"width":null,"height":null,"color":null,
     * "price":null,"cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":20,"acceptNum":0,"carNo":"CX1644",
     * "oldOrderNo":"ZYT4220170000003","scanMode":2},{"id":4,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114598779-01-01",
     * "itemName":"创维电视32X3黑色","groosWeight":null,"volume":0.059,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "note":null,"quantity":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":20,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000004","scanMode":2},
     * {"id":5,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114599402-01-01","itemName":"熊猫电视LE32F88S黑","groosWeight":null,
     * "volume":0.078,"packageUnit":null,"length":null,"width":null,"height":null,"color":null,
     * "price":null,"cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":20,"acceptNum":0,"carNo":"CX1644",
     * "oldOrderNo":"ZYT4220170000005","scanMode":2},{"id":6,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114597309-01-01",
     * "itemName":"荣事达洗衣机WT810SOR 亮灰色","groosWeight":null,"volume":0.426,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "note":null,"quantity":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000006","scanMode":2},
     * {"id":7,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114596893-01-01","itemName":"松下洗衣机XQG70-EA7221 白色","groosWeight":null,
     * "volume":0.411,"packageUnit":null,"length":null,"width":null,"height":null,"color":null,
     * "price":null,"cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":10,"acceptNum":0,"carNo":"CX1644",
     * "oldOrderNo":"ZYT4220170000007","scanMode":2},{"id":8,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TBY110910632-01-01",
     * "itemName":"美的热水器JSQ27-14WH5D天燃气","groosWeight":null,"volume":0.087,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "note":null,"quantity":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000008","scanMode":2},
     * {"id":9,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114586320-03-01","itemName":"TCL空调KFRd-35G/JC13(WIFI) 室内机",
     * "groosWeight":null,"volume":0.09,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"note":null,"quantity":1,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"CX1644","oldOrderNo":"ZYT4220170000009","scanMode":2},{"id":10,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114586320-03-01",
     * "itemName":"TCL空调KFR-35W0333","groosWeight":null,"volume":0.194,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "note":null,"quantity":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000009","scanMode":2},
     * {"id":11,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114586320-03-02","itemName":"TCL空调KFR-35W45BP(配3米铜连接管) 室外机",
     * "groosWeight":null,"volume":0.184,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"note":null,"quantity":1,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"CX1644","oldOrderNo":"ZYT4220170000011","scanMode":2},{"id":12,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114586320-03-02",
     * "itemName":"TCL空调KFRd-35G/EL23BpA 白+花纹","groosWeight":null,"volume":0.09,
     * "packageUnit":null,"length":null,"width":null,"height":null,"color":null,"price":null,
     * "cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,"productCode":null,
     * "oneCode":null,"status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000011",
     * "scanMode":2},{"id":13,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114586320-03-03","itemName":"TCL空调KFR-35W45BP(配3米铜连接管) 室外机",
     * "groosWeight":null,"volume":0.184,"packageUnit":null,"length":null,"width":null,
     * "height":null,"color":null,"price":null,"cargoValue":null,"note":null,"quantity":1,
     * "packageQuantity":null,"productCode":null,"oneCode":null,"status":10,"acceptNum":0,
     * "carNo":"CX1644","oldOrderNo":"ZYT4220170000013","scanMode":2},{"id":14,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114586320-03-03",
     * "itemName":"TCL空调KFRd-35G/EL23BpA 白+花纹","groosWeight":null,"volume":0.09,
     * "packageUnit":null,"length":null,"width":null,"height":null,"color":null,"price":null,
     * "cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,"productCode":null,
     * "oneCode":null,"status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000013",
     * "scanMode":2},{"id":15,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114586588-01-01","itemName":"TCL洗衣机XQG65-Q100芭蕾白","groosWeight":null,
     * "volume":0.322,"packageUnit":null,"length":null,"width":null,"height":null,"color":null,
     * "price":null,"cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":10,"acceptNum":0,"carNo":"CX1644",
     * "oldOrderNo":"ZYT4220170000015","scanMode":2},{"id":16,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114586319-01-01",
     * "itemName":"美的洗碗机WQP8-3905-CN黑色","groosWeight":null,"volume":0.295,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "note":null,"quantity":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000016","scanMode":2},
     * {"id":17,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114588826-01-01","itemName":"星星冷柜BD/BC-106E","groosWeight":null,
     * "volume":0.39,"packageUnit":null,"length":null,"width":null,"height":null,"color":null,
     * "price":null,"cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":10,"acceptNum":0,"carNo":"CX1644",
     * "oldOrderNo":"ZYT4220170000017","scanMode":2},{"id":18,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114586168-01-01",
     * "itemName":"万家乐燃气灶JZY-IQL83B   20Y液化气黑色钢","groosWeight":null,"volume":0.043,
     * "packageUnit":null,"length":null,"width":null,"height":null,"color":null,"price":null,
     * "cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,"productCode":null,
     * "oneCode":null,"status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000018",
     * "scanMode":2},{"id":19,"orderId":17,"orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,
     * "itemCode":"TB114586168-01-01","itemName":"万家乐油烟机CXW-200-DG13(R)不锈钢","groosWeight":null,
     * "volume":0.218,"packageUnit":null,"length":null,"width":null,"height":null,"color":null,
     * "price":null,"cargoValue":null,"note":null,"quantity":1,"packageQuantity":null,
     * "productCode":null,"oneCode":null,"status":10,"acceptNum":0,"carNo":"CX1644",
     * "oldOrderNo":"ZYT4220170000018","scanMode":2},{"id":20,"orderId":17,
     * "orderNo":"HB-ZYT4220170000001","subFaceOrderNo":null,"itemCode":"TB114586136-01-01",
     * "itemName":"美的热水器F60-15WB5(Y) 白色","groosWeight":null,"volume":0.216,"packageUnit":null,
     * "length":null,"width":null,"height":null,"color":null,"price":null,"cargoValue":null,
     * "note":null,"quantity":1,"packageQuantity":null,"productCode":null,"oneCode":null,
     * "status":10,"acceptNum":0,"carNo":"CX1644","oldOrderNo":"ZYT4220170000020","scanMode":2}]
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * orderId : 17
         * orderNo : HB-ZYT4220170000001
         * subFaceOrderNo : null
         * itemCode : TBY112094502-01-01
         * itemName : 小天鹅洗衣机TD80-Mute160WDX白色
         * groosWeight : null
         * volume : 0.51
         * packageUnit : null
         * length : null
         * width : null
         * height : null
         * color : null
         * price : null
         * cargoValue : null
         * note : null
         * quantity : 1
         * packageQuantity : null
         * productCode : null
         * oneCode : null
         * status : 20
         * acceptNum : 0
         * carNo : CX1644
         * oldOrderNo : ZYT4220170000001
         * scanMode : 2
         */

        private int id;
        private int orderId;
        private String orderNo;
        private Object subFaceOrderNo;
        private String itemCode;
        private String itemName;
        private Object groosWeight;
        private double volume;
        private Object packageUnit;
        private Object length;
        private Object width;
        private Object height;
        private Object color;
        private Object price;
        private Object cargoValue;
        private Object note;
        private int quantity;
        private Object packageQuantity;
        private Object productCode;
        private Object oneCode;
        @SerializedName("status")
        private int statusX;
        private int acceptNum;
        private String carNo;
        private String oldOrderNo;
        private int scanMode;

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

        public Object getGroosWeight() {
            return groosWeight;
        }

        public void setGroosWeight(Object groosWeight) {
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

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
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

        public int getScanMode() {
            return scanMode;
        }

        public void setScanMode(int scanMode) {
            this.scanMode = scanMode;
        }
    }
}
