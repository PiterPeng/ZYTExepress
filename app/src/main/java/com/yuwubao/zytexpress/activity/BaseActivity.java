package com.yuwubao.zytexpress.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yuwubao.zytexpress.AppManager;
import com.yuwubao.zytexpress.utils.SpeechTool;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * 基类界面，所有Activtiy必须继承此类
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//沉浸式状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        c = this;
        setContentView(getContentResourseId());
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
        ButterKnife.bind(this);
        init();
    }

    protected abstract int getContentResourseId();

    protected abstract void init();

    protected void showVioce(String alter) {
        if (true) {
            SpeechTool tool = new SpeechTool(getApplication());
            tool.speak(alter);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }


    public void JumpToActivity(Class<?> cls) {
        JumpToActivity(cls, null);
    }


    public void JumpToActivity(Class<?> cls, Intent data) {
        Intent intent = new Intent(this, cls);
        // 设置跳转标志为如此Activity存在则把其从任务堆栈中取出放到最上方
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (data != null)
            intent.putExtras(data);
        startActivity(intent);
        setOverridePendingTransition();
    }


    public void JumpToActivityForResult(Class<?> cls, int requestCode) {
        JumpToActivityForResult(cls, null, requestCode);
    }


    public void JumpToActivityForResult(Class<?> cls, Object obj,
                                        int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//
        // 设置跳转标志为如此Activity存在则把其从任务堆栈中取出放到最上方
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivityForResult(intent, requestCode);
        setOverridePendingTransition();
    }

    public void setOverridePendingTransition() {
        overridePendingTransition(0, 0);
        // default
//        overridePendingTransition(android.R.anim.fade_in,
//                android.R.anim.fade_in);
//        overridePendingTransition(android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
//        overridePendingTransition(R.anim.fade, R.anim.hold);
        // overridePendingTransition(R.anim.opt_slide_in, R.anim.opt_slide_out);
        // overridePendingTransition(R.anim.opt_zoom_in, R.anim.opt_zoom_out);
    }

    /**
     * 替换碎片
     *
     * @param baseFragement
     */
    protected void replaceFragment(int id, Fragment baseFragement) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, baseFragement);
        ft.commit();
    }

    protected <T extends View> T find(int resId) {
        return (T) findViewById(resId);
    }

    protected void setText(int resId, String content) {
        TextView tv = find(resId);
        tv.setText(content);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }

}
