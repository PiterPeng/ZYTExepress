package com.yuwubao.zytexpress.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Peng on 2017/9/20
 * e-mail: phlxplus@163.com
 * description:
 */

public class PaiJianBean extends BaseBean {


    /**
     * result : {"content":[{"orderNo":"444","acceptTime":"2017-09-15 14:09:56","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-15 14:07:04","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"555","acceptTime":"2017-09-15 14:12:01","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-15 14:10:56","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"666","acceptTime":"2017-09-15 14:46:39","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-15 14:45:25","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"777","acceptTime":"2017-09-15 14:53:27","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-15 14:51:54","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"888","acceptTime":"2017-09-18 10:04:47","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-18 10:04:21","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"999","acceptTime":"2017-09-18 11:12:29","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-18 11:11:34","status":30,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"aaa","acceptTime":"2017-09-18 14:27:39","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-18 14:27:04","status":30,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"bbb","acceptTime":"2017-09-18 14:41:52","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-18 14:41:33","status":30,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"cc11","acceptTime":"2017-09-20 09:27:23","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-20 09:26:50","status":30,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"},{"orderNo":"cc12","acceptTime":"2017-09-20 09:32:52","projectName":"42001(TCL/B2C湖北省配项目)",
     * "requireArrivalDate":"2017-09-20 09:31:41","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017",
     * "address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓",
     * "area":"武穴市"}],"totalPages":2,"totalElements":15,"number":0,"size":10,"first":true,"last":false}
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
         * content : [{"orderNo":"444","acceptTime":"2017-09-15 14:09:56","projectName":"42001(TCL/B2C湖北省配项目)",
         * "requireArrivalDate":"2017-09-15 14:07:04","status":100,"level":0,"contactName":"伍菊芬",
         * "mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,
         * "shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"555","acceptTime":"2017-09-15 14:12:01",
         * "projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-15 14:10:56","status":100,"level":0,
         * "contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,
         * "weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"666","acceptTime":"2017-09-15
         * 14:46:39","projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-15 14:45:25","status":100,
         * "level":0,"contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇",
         * "volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"777",
         * "acceptTime":"2017-09-15 14:53:27","projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-15
         * 14:51:54","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市
         * .武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"},
         * {"orderNo":"888","acceptTime":"2017-09-18 10:04:47","projectName":"42001(TCL/B2C湖北省配项目)",
         * "requireArrivalDate":"2017-09-18 10:04:21","status":100,"level":0,"contactName":"伍菊芬",
         * "mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,
         * "shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"999","acceptTime":"2017-09-18 11:12:29",
         * "projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-18 11:11:34","status":30,"level":0,
         * "contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇","volume":0.12,
         * "weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"aaa","acceptTime":"2017-09-18
         * 14:27:39","projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-18 14:27:04","status":30,
         * "level":0,"contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇",
         * "volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"bbb",
         * "acceptTime":"2017-09-18 14:41:52","projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-18
         * 14:41:33","status":30,"level":0,"contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市
         * .梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"cc11",
         * "acceptTime":"2017-09-20 09:27:23","projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-20
         * 09:26:50","status":30,"level":0,"contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市.武穴市
         * .梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"},{"orderNo":"cc12",
         * "acceptTime":"2017-09-20 09:32:52","projectName":"42001(TCL/B2C湖北省配项目)","requireArrivalDate":"2017-09-20
         * 09:31:41","status":100,"level":0,"contactName":"伍菊芬","mobile":"13476606017","address":"湖北省黄冈市武穴市湖北省.黄冈市
         * .武穴市.梅川镇梅川镇","volume":0.12,"weight":11.7,"quantity":1,"shipperName":"武汉RDC总仓","area":"武穴市"}]
         * totalPages : 2
         * totalElements : 15
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
             * orderNo : 444
             * acceptTime : 2017-09-15 14:09:56
             * projectName : 42001(TCL/B2C湖北省配项目)
             * requireArrivalDate : 2017-09-15 14:07:04
             * status : 100
             * level : 0
             * contactName : 伍菊芬
             * mobile : 13476606017
             * address : 湖北省黄冈市武穴市湖北省.黄冈市.武穴市.梅川镇梅川镇
             * volume : 0.12
             * weight : 11.7
             * quantity : 1
             * shipperName : 武汉RDC总仓
             * area : 武穴市
             */

            private String orderNo;
            private String acceptTime;
            private String projectName;
            private String requireArrivalDate;
            @SerializedName("status")
            private int statusX;
            private int level;
            private String contactName;
            private String mobile;
            private String address;
            private double volume;
            private double weight;
            private int quantity;
            private String shipperName;
            private String area;

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getAcceptTime() {
                return acceptTime;
            }

            public void setAcceptTime(String acceptTime) {
                this.acceptTime = acceptTime;
            }

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }

            public String getRequireArrivalDate() {
                return requireArrivalDate;
            }

            public void setRequireArrivalDate(String requireArrivalDate) {
                this.requireArrivalDate = requireArrivalDate;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
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

            public double getVolume() {
                return volume;
            }

            public void setVolume(double volume) {
                this.volume = volume;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getShipperName() {
                return shipperName;
            }

            public void setShipperName(String shipperName) {
                this.shipperName = shipperName;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }
        }
    }
}
