package com.yuwubao.zytexpress.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.widget.HeaderBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/8/18
 * e-mail: phlxplus@163.com
 * description: 手动输入单号
 */

public class InputActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.input)
    EditText input;

    private String inputString;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_input;
    }

    @Override
    protected void init() {
        setHeader();
    }

    private void setHeader() {
        title.setTitle("输入二维码／条形码");
    }

    @OnClick({R.id.back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if (!isFastDoubleClick()) {
                    inputString = input.getText().toString().trim();
                    if (TextUtils.isEmpty(inputString)) {
                        UIHelper.showMessage(c, "请输入二维码或条形码");
                        return;
                    }
                    showDialog();
                }
                break;
        }
    }

    void showDialog() {

        AlertDialog dialog = new AlertDialog.Builder(c).create();
        dialog.setTitle("签收提示");
        dialog.setMessage("请逐个签收货物");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UIHelper.showMessage(c, "取消");
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UIHelper.showMessage(c, "好的");
            }
        });
        dialog.show();
    }


    private long lastClickTime = 0;

    /**
     * 防止重复点击 间隔2秒
     *
     * @return true代表点击过快，false点击正常。
     */
    boolean isFastDoubleClick() {

        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime;
        if (time > 0 && time < 2000) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }
}
