package com.yuwubao.zytexpress.bean;

import java.util.List;

/**
 * Created by Peng on 2017/9/22
 * e-mail: phlxplus@163.com
 * description:
 */

public class InTableBean extends BaseBean{

    /**
     * message : null
     * result : [{"id":4,"name":"vestibulum"},{"id":5,"name":"Quisque"},{"id":8,"name":"id"},{"id":10,"name":"Ut"},
     * {"id":11,"name":"vel,"},{"id":15,"name":"Cras"},{"id":16,"name":"interdum."},{"id":19,"name":"ac"},{"id":20,
     * "name":"at"},{"id":21,"name":"pede."},{"id":22,"name":"odio."},{"id":23,"name":"a"},{"id":24,"name":"nisi"},
     * {"id":28,"name":"ridiculus"},{"id":29,"name":"vulputate,"},{"id":30,"name":"nascetur"},{"id":31,"name":"massa
     * ."},{"id":32,"name":"Lorem"},{"id":42,"name":"vulputate"},{"id":44,"name":"penatibus"},{"id":46,
     * "name":"consequat,"},{"id":49,"name":"faucibus."},{"id":50,"name":"mi."},{"id":51,"name":"velit."},{"id":53,
     * "name":"neque"},{"id":56,"name":"risus."},{"id":57,"name":"id,"},{"id":58,"name":"Mauris"},{"id":61,
     * "name":"malesuada"},{"id":64,"name":"eu,"},{"id":67,"name":"dapibus"},{"id":68,"name":"non,"},{"id":72,
     * "name":"felis,"},{"id":73,"name":"purus"},{"id":74,"name":"venenatis"},{"id":75,"name":"pretium"},{"id":77,
     * "name":"consequat"},{"id":81,"name":"amet,"},{"id":82,"name":"lacinia"},{"id":88,"name":"leo."},{"id":90,
     * "name":"Nam"},{"id":91,"name":"pellentesque"},{"id":92,"name":"Fusce"},{"id":93,"name":"habitant"},{"id":99,
     * "name":"metus."}]
     * returnCode : null
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
         * id : 4
         * name : vestibulum
         */

        private int id;
        private String name;

        public ResultBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
