package com.yuwubao.zytexpress.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.yuwubao.zytexpress.R;


/**
 * 加载更多帮助类
 * Created by mhdt on 2016/11/7.
 * update
 */
public class LoadViewPullHelper extends FrameLayout {
    private String TAG = "LoadViewHelper";
    private Context c;
    private View faileView, emptyView;

    public LoadViewPullHelper(Context context) {
        super(context);
        init();
    }

    public LoadViewPullHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadViewPullHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        c = getContext();
        LayoutInflater.from(c).inflate(R.layout.widget_loadpullhelper, this, true);
        faileView = findViewById(R.id.failedView);
        emptyView = findViewById(R.id.emptyView);
    }

    public void loadFailed() {
        this.setVisibility(View.VISIBLE);
        faileView.setVisibility(View.VISIBLE);
        if (emptyView != null)
            emptyView.setVisibility(View.GONE);
    }

    public void loadEmpty() {
        this.setVisibility(View.VISIBLE);
        faileView.setVisibility(View.GONE);
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        this.setVisibility(View.GONE);
    }
}
