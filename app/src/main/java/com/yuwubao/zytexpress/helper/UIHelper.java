package com.yuwubao.zytexpress.helper;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuwubao.zytexpress.R;


/**
 * 应用程序Activity帮助类
 *
 * @author mhdt
 * @version 1.0
 * @created 2014-1-2
 */
public class UIHelper {

    private static Toast toast;

    public static void showMessage(Context context, int stringId) {
        showMessage(context, context.getString(stringId));
    }


    /**
     * @Name: ShowMessage
     * @Description: 弹出提示信息
     * @Author: 黄俊彬
     * @Version: V1.00
     * @Create 2013-8-9
     * @Parameters: context 上下文 meesage提示的信息
     * @Return: 无
     */
    public static void showMessage(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static ProgressDialog showAppProgressBar(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
        return progressDialog;
    }

    public static void dismissAppProgressBar(ProgressDialog progressDialog) {
        if (progressDialog != null) progressDialog.dismiss();
    }

    /**
     * 显示提示文本框
     *
     * @param toast 需要显示的提示语句
     */
    public static void setMyCustomToastVisibility(TextView view, String toast) {
        view.setText(toast);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏提示框
     */
    public static void setMyCustomToastInvisible(TextView view) {
        view.setVisibility(View.INVISIBLE);
    }

    private static Dialog dialog;

    /**
     * 自定义dialog,风格参照UI设计图。
     *
     * @param context     上下文
     * @param titleValus  标题
     * @param submitValus 按钮文字
     * @param okListener  点击事件
     */
    public static void showMyCustomDialog(final Context context, String titleValus, String submitValus, final View
            .OnClickListener okListener, final View.OnClickListener cancelListener) {
//        if (dialog == null) {
        dialog = new Dialog(context, R.style.dialogActivity);
        View view = LayoutInflater.from(context).inflate(R.layout.my_custom_dialog_layout, null);
        TextView title = (TextView) view.findViewById(R.id.tv_custom_title);
        TextView submit = (TextView) view.findViewById(R.id.tv_custom_ok);
        ImageView delete = (ImageView) view.findViewById(R.id.iv_delete);
        title.setText(titleValus);
        submit.setText(submitValus);
        dialog.setContentView(view);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okListener != null) {
                    okListener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
//        } else {
//            dialog.show();
//        }
    }

    public static void showCodeDialog(final Context context, final String code) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_code, null);
        final EditText editText = (EditText) view.findViewById(R.id.edit_text);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("验证").setNegativeButton("取消", null)
                .setPositiveButton("确定", null).setView(view).setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    UIHelper.showMessage(context, "输入不能为空");
                    return;
                }
                if (!TextUtils.equals(content, code)) {
                    UIHelper.showMessage(context, "验证码错误");
                    return;
                }
                UIHelper.showMessage(context, "操作成功！");
                alertDialog.dismiss();
                ((Activity) context).finish();
            }
        });

    }
}