package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/3/29
 * e-mail: phlxplus@163.com
 * description:
 */

public class Count2Bean extends BaseBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * quantity : 9
         * volume : 1.878374
         * weight : 227
         */

        private int quantity;
        private double volume;
        private double weight;

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
