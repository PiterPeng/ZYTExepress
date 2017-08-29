package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/8/22
 * e-mail: phlxplus@163.com
 * description:
 */

public class NiXbean extends BaseBean {

    /**
     * message : null
     * result : {"content":[{"id":174248,"orderNo":"eeeee","itemName":"KFRd-72L/DY12(阿里云)(WIFI) 室内机 定频"},
     * {"id":174249,"orderNo":"eeeee","itemName":"KFR-72W12(配4米铜连接管) 室外机"}],"last":true,"totalElements":2,
     * "totalPages":1,"sort":null,"first":true,"numberOfElements":2,"size":10,"number":0}
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
         * content : [{"id":174248,"orderNo":"eeeee","itemName":"KFRd-72L/DY12(阿里云)(WIFI) 室内机 定频"},{"id":174249,
         * "orderNo":"eeeee","itemName":"KFR-72W12(配4米铜连接管) 室外机"}]
         * last : true
         * totalElements : 2
         * totalPages : 1
         * sort : null
         * first : true
         * numberOfElements : 2
         * size : 10
         * number : 0
         */

        private boolean last;
        private int totalElements;
        private int totalPages;
        private Object sort;
        private boolean first;
        private int numberOfElements;
        private int size;
        private int number;
        private List<ContentBean> content;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * id : 174248
             * orderNo : eeeee
             * itemName : KFRd-72L/DY12(阿里云)(WIFI) 室内机 定频
             */

            private int id;
            private String orderNo;
            private String itemName;

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

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }
        }
    }
}
