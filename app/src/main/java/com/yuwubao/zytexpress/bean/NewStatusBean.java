package com.yuwubao.zytexpress.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Peng on 2017/4/8
 * e-mail: phlxplus@163.com
 * description:
 */

public class NewStatusBean extends BaseBean implements Serializable {

    /**
     * message : null
     * result : [{"id":741,"code":"asdf","name":"测试数据1","oneCode":"6931338485044"},{"id":631,
     * "code":"KUP001010101010100132","name":"彩电-55寸-D55A630U 黑色","oneCode":"6931338485044"}]
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * id : 741
         * code : asdf
         * name : 测试数据1
         * oneCode : 6931338485044
         */

        private int id;
        private String code;
        private String name;
        private String oneCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOneCode() {
            return oneCode;
        }

        public void setOneCode(String oneCode) {
            this.oneCode = oneCode;
        }
    }
}
