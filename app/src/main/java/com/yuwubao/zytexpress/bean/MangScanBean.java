package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/3/20
 * e-mail: phlxplus@163.com
 * description: 盲扫
 */

public class MangScanBean extends BaseBean {

    /**
     * result : {"content":[{"itemCode":"KUP001010101010100004","itemName":"彩电-49寸-D49A620U 黑色",
     * "quantity":4,"LENGTH":0,"HEIGHT":1},{"itemCode":"TB114586319-01-01",
     * "itemName":"美的洗碗机WQP8-3905-CN黑色","quantity":1,"LENGTH":0,"HEIGHT":0},
     * {"itemCode":"TB114596893-01-01","itemName":"松下洗衣机XQG70-EA7221 白色","quantity":1,"LENGTH":0,
     * "HEIGHT":0},{"itemCode":"TB114597309-01-01","itemName":"荣事达洗衣机WT810SOR 亮灰色","quantity":1,
     * "LENGTH":0,"HEIGHT":0}],"totalPages":1,"totalElements":4,"number":1,"size":10,
     * "first":true,"last":true}
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
         * content : [{"itemCode":"KUP001010101010100004","itemName":"彩电-49寸-D49A620U 黑色",
         * "quantity":4,"LENGTH":0,"HEIGHT":1},{"itemCode":"TB114586319-01-01",
         * "itemName":"美的洗碗机WQP8-3905-CN黑色","quantity":1,"LENGTH":0,"HEIGHT":0},
         * {"itemCode":"TB114596893-01-01","itemName":"松下洗衣机XQG70-EA7221 白色","quantity":1,
         * "LENGTH":0,"HEIGHT":0},{"itemCode":"TB114597309-01-01","itemName":"荣事达洗衣机WT810SOR
         * 亮灰色","quantity":1,"LENGTH":0,"HEIGHT":0}]
         * totalPages : 1
         * totalElements : 4
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
             * itemCode : KUP001010101010100004
             * itemName : 彩电-49寸-D49A620U 黑色
             * quantity : 4
             * LENGTH : 0
             * HEIGHT : 1
             */

            private String itemCode;
            private String itemName;
            private int quantity;
            private int LENGTH;
            private int HEIGHT;

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

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getLENGTH() {
                return LENGTH;
            }

            public void setLENGTH(int LENGTH) {
                this.LENGTH = LENGTH;
            }

            public int getHEIGHT() {
                return HEIGHT;
            }

            public void setHEIGHT(int HEIGHT) {
                this.HEIGHT = HEIGHT;
            }
        }
    }
}
