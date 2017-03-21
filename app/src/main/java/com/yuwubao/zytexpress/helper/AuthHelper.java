package com.yuwubao.zytexpress.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.yuwubao.zytexpress.AppManager;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.BaseActivity;
import com.yuwubao.zytexpress.db.dao.UserDao;


/**
 * 身份的帮助类
 * Created by mhdt on 2016/11/7.
 * update
 */

public class AuthHelper {
    public static AlertDialog offLineDialog;

    /**
     * 账号异地登录
     *
     * @param c
     */
    public static void kickOut(final Context c) {
        kickOut(c, null, null);
    }

    /**
     * 账号异地登录
     *
     * @param c
     */
    public static void kickOut(final Context c, String title, String msg) {
        if (offLineDialog == null) {
            offLineDialog = new AlertDialog.Builder(c).setTitle(c.getString(R.string.out_line))
                    .setMessage
                            (c.getString(R.string.out_line_notify))
                    .setCancelable(false)
                    .setPositiveButton(c.getString(R.string.login), new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AuthHelper.LoginOff((BaseActivity) c);
                            dialog.dismiss();
                        }
                    }).create();
        }

        if (!TextUtils.isEmpty(title))
            offLineDialog.setTitle(title);

        if (!TextUtils.isEmpty(msg)) {
            offLineDialog.setMessage(msg);
        }

        if (!offLineDialog.isShowing()) {
            offLineDialog.show();
        }
    }

    /**
     * 注销
     * <p>
     * update by mhdt
     */
    public static void LoginOff(BaseActivity baseActivity) {
        // 清除用户
        UserDao.getInstance().delete();
        // 关闭所有界面
        AppManager.getAppManager().finishAllActivity();
//        baseActivity.JumpToActivity(LoginActivity.class);
    }
}
