package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/3/21
 * e-mail: phlxplus@163.com
 * description:
 */

public class DispatchBean extends BaseBean {

    /**
     * result : {"content":[{"itemName":"未知","quantity":4,"volume":0.6572,"width":3.32},
     * {"itemName":"电视","quantity":4,"volume":0.6572,"width":3.32}],"totalPages":1,
     * "totalElements":2,"number":1,"size":10,"first":true,"last":true}
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
         * content : [{"itemName":"未知","quantity":4,"volume":0.6572,"width":3.32},
         * {"itemName":"电视","quantity":4,"volume":0.6572,"width":3.32}]
         * totalPages : 1
         * totalElements : 2
         * number : 1
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
             * itemName : 未知
             * quantity : 4
             * volume : 0.6572
             * width : 3.32
             */

            private String itemName;
            private int quantity;
            private double volume;
            private double width;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public double getVolume() {
                return volume;
            }

            public void setVolume(double volume) {
                this.volume = volume;
            }

            public double getWidth() {
                return width;
            }

            public void setWidth(double width) {
                this.width = width;
            }
        }
    }
}
