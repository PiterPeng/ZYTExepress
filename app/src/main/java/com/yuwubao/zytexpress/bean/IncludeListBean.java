package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/3/29
 * e-mail: phlxplus@163.com
 * description:
 */

public class IncludeListBean extends BaseBean {

    /**
     * result : [{"id":12,"code":"KJHIUJH-098","name":"TCL电视"},{"id":41,"code":"TB114586320-03-03",
     * "name":"TCL空调KFR-35W45BP(配3米铜连接管) 室外机"}]
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 12
         * code : KJHIUJH-098
         * name : TCL电视
         */

        private int id;
        private String code;
        private String name;

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
    }
}
