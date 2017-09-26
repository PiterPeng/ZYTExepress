package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/9/20
 * e-mail: phlxplus@163.com
 * description:
 */

public class InOrOutListBean extends BaseBean {

    /**
     * message : null
     * result : {"scanNum":{"total":25397000,"point":40000,"un_point":25357000,"in_out":0},
     * "pages":{"content":[{"id":6,"po":"E47AZ74                  ","sn":"SN465465","customerPartNo":"62012A400-015-H
     * ","quantity":"40000"}],"last":true,"totalElements":1,"totalPages":1,"sort":null,"numberOfElements":1,
     * "first":true,"size":10,"number":0}}
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
         * scanNum : {"total":25397000,"point":40000,"un_point":25357000,"in_out":0}
         * pages : {"content":[{"id":6,"po":"E47AZ74                  ","sn":"SN465465",
         * "customerPartNo":"62012A400-015-H           ","quantity":"40000"}],"last":true,"totalElements":1,
         * "totalPages":1,"sort":null,"numberOfElements":1,"first":true,"size":10,"number":0}
         */

        private ScanNumBean scanNum;
        private PagesBean pages;

        public ScanNumBean getScanNum() {
            return scanNum;
        }

        public void setScanNum(ScanNumBean scanNum) {
            this.scanNum = scanNum;
        }

        public PagesBean getPages() {
            return pages;
        }

        public void setPages(PagesBean pages) {
            this.pages = pages;
        }

        public static class ScanNumBean {
            /**
             * total : 25397000
             * point : 40000
             * un_point : 25357000
             * in_out : 0
             */

            private int total;
            private int point;
            private int un_point;
            private int in_out;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public int getUn_point() {
                return un_point;
            }

            public void setUn_point(int un_point) {
                this.un_point = un_point;
            }

            public int getIn_out() {
                return in_out;
            }

            public void setIn_out(int in_out) {
                this.in_out = in_out;
            }
        }

        public static class PagesBean {
            /**
             * content : [{"id":6,"po":"E47AZ74                  ","sn":"SN465465","customerPartNo":"62012A400-015-H
             * ","quantity":"40000"}]
             * last : true
             * totalElements : 1
             * totalPages : 1
             * sort : null
             * numberOfElements : 1
             * first : true
             * size : 10
             * number : 0
             */

            private boolean last;
            private int totalElements;
            private int totalPages;
            private Object sort;
            private int numberOfElements;
            private boolean first;
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

            public int getNumberOfElements() {
                return numberOfElements;
            }

            public void setNumberOfElements(int numberOfElements) {
                this.numberOfElements = numberOfElements;
            }

            public boolean isFirst() {
                return first;
            }

            public void setFirst(boolean first) {
                this.first = first;
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
                 * id : 6
                 * po : E47AZ74
                 * sn : SN465465
                 * customerPartNo : 62012A400-015-H
                 * quantity : 40000
                 */

                private int id;
                private String po;
                private String sn;
                private String customerPartNo;
                private String quantity;
                private String whBinCode;

                public String getWhBinCode() {
                    return whBinCode;
                }

                public void setWhBinCode(String whBinCode) {
                    this.whBinCode = whBinCode;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPo() {
                    return po;
                }

                public void setPo(String po) {
                    this.po = po;
                }

                public String getSn() {
                    return sn;
                }

                public void setSn(String sn) {
                    this.sn = sn;
                }

                public String getCustomerPartNo() {
                    return customerPartNo;
                }

                public void setCustomerPartNo(String customerPartNo) {
                    this.customerPartNo = customerPartNo;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }
            }
        }
    }
}
