package com.yuwubao.zytexpress.activity;

import android.view.Window;
import android.view.WindowManager;

import com.yuwubao.zytexpress.R;

/**
 * Created by Peng on 2017/3/7
 * e-mail: phlxplus@163.com
 * description: 登录
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        setTop();
    }

    private void setTop() {
        //隐藏状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
    }
}
