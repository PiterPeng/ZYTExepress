package com.yuwubao.zytexpress.activity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.yuwubao.zytexpress.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/7
 * e-mail: phlxplus@163.com
 * description: 登录
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        setTop();
    }

    private void setTop() {
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

    }

    @OnClick(R.id.bt_login)
    public void onClick() {
        JumpToActivity(MainActivity.class);

    }
}
