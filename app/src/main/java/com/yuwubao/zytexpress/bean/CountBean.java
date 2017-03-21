package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/3/20
 * e-mail: phlxplus@163.com
 * description:
 */

public class CountBean extends BaseBean {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * totle : 8
         * takeNum : 0
         * lastTake : 8
         * carNum : 0
         * lastCar : 8
         */

        private int totle;
        private int takeNum;
        private int lastTake;
        private int carNum;
        private int lastCar;

        public int getTotle() {
            return totle;
        }

        public void setTotle(int totle) {
            this.totle = totle;
        }

        public int getTakeNum() {
            return takeNum;
        }

        public void setTakeNum(int takeNum) {
            this.takeNum = takeNum;
        }

        public int getLastTake() {
            return lastTake;
        }

        public void setLastTake(int lastTake) {
            this.lastTake = lastTake;
        }

        public int getCarNum() {
            return carNum;
        }

        public void setCarNum(int carNum) {
            this.carNum = carNum;
        }

        public int getLastCar() {
            return lastCar;
        }

        public void setLastCar(int lastCar) {
            this.lastCar = lastCar;
        }
    }
}
