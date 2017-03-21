package com.yuwubao.zytexpress.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * function
 * Created by mhdt on 2016/12/3.12:38
 * update by:
 */
public abstract class BaseFragement extends Fragment {
    private View mView;
    public Context c;
    protected LayoutInflater mInflater;
    protected Bundle savedInstanceState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.c = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mInflater = inflater;
        this.savedInstanceState = savedInstanceState;
        mView = mInflater.inflate(getContentResourseId(), null);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    protected abstract int getContentResourseId();

    protected abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    /**
     * 替换碎片
     *
     * @param baseFragement
     */
    protected void replaceFragment(int id, Fragment baseFragement) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(id, baseFragement);
        ft.commit();
    }

    protected <T extends View> T find(int resId) {
        return (T) mView.findViewById(resId);
    }

    protected void setText(int resId, String content) {
        TextView tv = find(resId);
        tv.setText(content);
    }

    public void JumpToActivity(Class<?> cls) {
        JumpToActivity(cls, null);
    }

    public void JumpToActivity(Class<?> cls, Intent data) {
        Intent intent = new Intent(c, cls);
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
        Intent intent = new Intent(c, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//
        // 设置跳转标志为如此Activity存在则把其从任务堆栈中取出放到最上方
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivityForResult(intent, requestCode);
        setOverridePendingTransition();
    }

    public void setOverridePendingTransition() {
        getActivity().overridePendingTransition(0,0);
        // default
//        overridePendingTransition(android.R.anim.fade_in,
//                android.R.anim.fade_in);
//        overridePendingTransition(android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
//        overridePendingTransition(R.anim.fade, R.anim.hold);
        // overridePendingTransition(R.anim.opt_slide_in, R.anim.opt_slide_out);
        // overridePendingTransition(R.anim.opt_zoom_in, R.anim.opt_zoom_out);
    }
}
