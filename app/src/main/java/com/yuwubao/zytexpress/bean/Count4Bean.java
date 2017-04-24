package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/4/24
 * e-mail: phlxplus@163.com
 * description:
 */

public class Count4Bean extends BaseBean {

    /**
     * result : [{"totle":40,"lastTake":null,"takeNum":null}]
     * returnCode : null
     */

    private Object returnCode;
    private List<ResultBean> result;

    public Object getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Object returnCode) {
        this.returnCode = returnCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * totle : 40
         * lastTake : null
         * takeNum : null
         */

        private int totle;
        private int lastTake;
        private int takeNum;

        public int getTotle() {
            return totle;
        }

        public void setTotle(int totle) {
            this.totle = totle;
        }

        public int getLastTake() {
            return lastTake;
        }

        public void setLastTake(int lastTake) {
            this.lastTake = lastTake;
        }

        public int getTakeNum() {
            return takeNum;
        }

        public void setTakeNum(int takeNum) {
            this.takeNum = takeNum;
        }
    }
}
