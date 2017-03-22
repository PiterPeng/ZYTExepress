package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/3/22
 * e-mail: phlxplus@163.com
 * description:
 */

public class IntoCarListBean extends BaseBean {


    /**
     * result : {"content":[{"id":1,"productCode":null,"orderNo":"HB-ZYT4220170000001",
     * "itemCode":"TBY112094502-01-01","itemName":"小天鹅洗衣机TD80-Mute160WDX白色",
     * "updateTime":"2017-03-16 09:38:22.22+0000"},{"id":3,"productCode":null,
     * "orderNo":"HB-ZYT4220170000001","itemCode":"TB113562787-01-01",
     * "itemName":"奇帅洗衣机XQB42-426活力蓝","updateTime":"2017-03-16 09:38:22.22+0000"},{"id":4,
     * "productCode":null,"orderNo":"HB-ZYT4220170000001","itemCode":"TB114598779-01-01",
     * "itemName":"创维电视32X3黑色","updateTime":"2017-03-16 09:38:22.22+0000"},{"id":5,
     * "productCode":null,"orderNo":"HB-ZYT4220170000001","itemCode":"TB114599402-01-01",
     * "itemName":"熊猫电视LE32F88S黑","updateTime":"2017-03-16 09:38:22.22+0000"},{"id":17,
     * "productCode":"3921744976826","orderNo":"HB-ZYT4220170000001",
     * "itemCode":"TB114588826-01-01","itemName":"星星冷柜BD/BC-106E","updateTime":"2017-03-21
     * 09:30:50.50+0000"}],"totalPages":1,"totalElements":5,"number":1,"size":10,"first":true,
     * "last":true}
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
         * content : [{"id":1,"productCode":null,"orderNo":"HB-ZYT4220170000001",
         * "itemCode":"TBY112094502-01-01","itemName":"小天鹅洗衣机TD80-Mute160WDX白色",
         * "updateTime":"2017-03-16 09:38:22.22+0000"},{"id":3,"productCode":null,
         * "orderNo":"HB-ZYT4220170000001","itemCode":"TB113562787-01-01",
         * "itemName":"奇帅洗衣机XQB42-426活力蓝","updateTime":"2017-03-16 09:38:22.22+0000"},{"id":4,
         * "productCode":null,"orderNo":"HB-ZYT4220170000001","itemCode":"TB114598779-01-01",
         * "itemName":"创维电视32X3黑色","updateTime":"2017-03-16 09:38:22.22+0000"},{"id":5,
         * "productCode":null,"orderNo":"HB-ZYT4220170000001","itemCode":"TB114599402-01-01",
         * "itemName":"熊猫电视LE32F88S黑","updateTime":"2017-03-16 09:38:22.22+0000"},{"id":17,
         * "productCode":"3921744976826","orderNo":"HB-ZYT4220170000001",
         * "itemCode":"TB114588826-01-01","itemName":"星星冷柜BD/BC-106E","updateTime":"2017-03-21
         * 09:30:50.50+0000"}]
         * totalPages : 1
         * totalElements : 5
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
             * id : 1
             * productCode : null
             * orderNo : HB-ZYT4220170000001
             * itemCode : TBY112094502-01-01
             * itemName : 小天鹅洗衣机TD80-Mute160WDX白色
             * updateTime : 2017-03-16 09:38:22.22+0000
             */

            private int id;
            private Object productCode;
            private String orderNo;
            private String itemCode;
            private String itemName;
            private String updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getProductCode() {
                return productCode;
            }

            public void setProductCode(Object productCode) {
                this.productCode = productCode;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
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

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
