package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/3/31
 * e-mail: phlxplus@163.com
 * description: 三级导航 公用bean
 */

public class Count3Bean extends BaseBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * shipperName : 武汉电商RDC
         * quantity : 2
         * volume : 0.2503
         * weight : 44.5
         */

        private String shipperName;
        private int quantity;
        private double volume;
        private double weight;

        public String getCustomerName() {
            return shipperName;
        }

        public void setCustomerName(String customerName) {
            this.shipperName = customerName;
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

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
}
