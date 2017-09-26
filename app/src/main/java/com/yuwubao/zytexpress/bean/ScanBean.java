package com.yuwubao.zytexpress.bean;

/**
 * Created by Peng on 2017/9/24
 * e-mail: phlxplus@163.com
 * description:
 */

public class ScanBean extends BaseBean {


    /**
     * result : {"type":"SNBUY","index":3,"value":"SN+买方料号","item":{"scan_sn":"","scan_69":"","scan_buy":"",
     * "scan_sale":""}}
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
         * type : SNBUY
         * index : 3
         * value : SN+买方料号
         * item : {"scan_sn":"","scan_69":"","scan_buy":"","scan_sale":""}
         */

        private String type;
        private int index;
        private String value;
        private ItemBean item;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * scan_sn :
             * scan_69 :
             * scan_buy :
             * scan_sale :
             */

            private String scan_sn;
            private String scan_69;
            private String scan_buy;
            private String scan_sale;

            public String getScan_sn() {
                return scan_sn;
            }

            public void setScan_sn(String scan_sn) {
                this.scan_sn = scan_sn;
            }

            public String getScan_69() {
                return scan_69;
            }

            public void setScan_69(String scan_69) {
                this.scan_69 = scan_69;
            }

            public String getScan_buy() {
                return scan_buy;
            }

            public void setScan_buy(String scan_buy) {
                this.scan_buy = scan_buy;
            }

            public String getScan_sale() {
                return scan_sale;
            }

            public void setScan_sale(String scan_sale) {
                this.scan_sale = scan_sale;
            }
        }
    }
}
