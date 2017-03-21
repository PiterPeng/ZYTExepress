package com.yuwubao.zytexpress.db;

import android.content.Context;

import com.simple.util.db.operation.SimpleDbHelper;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.bean.User;


/**
 * 数据库的帮助类
 *
 * @author mhdt
 * @version 1.0
 * @created 2016-7-29 上午10:44:35
 * @update
 */
public class DBHelper extends SimpleDbHelper {

    private static final String DBNAME = AppConfig.APP_DBNAME;
    private static final int DBVERSION = AppConfig.APP_DBVERSION;
    private static final Class<?>[] clazz = {User.class};

    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION, clazz);
    }

}