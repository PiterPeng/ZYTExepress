package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/4/26
 * e-mail: phlxplus@163.com
 * description:
 */

public class HistoryCountBean extends BaseBean {

    /**
     * result : [{"takeNum":1,"carNum":1,"labeNum":null,"checkNum":null}]
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
         * takeNum : 1
         * carNum : 1
         * labeNum : null
         * checkNum : null
         */

        private int takeNum;
        private int carNum;
        private int labeNum;
        private int checkNum;

        public int getTakeNum() {
            return takeNum;
        }

        public void setTakeNum(int takeNum) {
            this.takeNum = takeNum;
        }

        public int getCarNum() {
            return carNum;
        }

        public void setCarNum(int carNum) {
            this.carNum = carNum;
        }

        public int getLabeNum() {
            return labeNum;
        }

        public void setLabeNum(int labeNum) {
            this.labeNum = labeNum;
        }

        public int getCheckNum() {
            return checkNum;
        }

        public void setCheckNum(int checkNum) {
            this.checkNum = checkNum;
        }
    }
}
