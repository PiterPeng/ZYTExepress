package com.yuwubao.zytexpress.frag;

import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.widget.HeaderBar;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/8
 * e-mail: phlxplus@163.com
 * description: 商城
 */

public class ShopStoreFragment extends BaseFragement {
    @BindView(R.id.mLinearLayout)
    RelativeLayout mLinearLayout;
    @BindView(R.id.title)
    HeaderBar title;

    AgentWeb webView;
    private static final String ADRESS = "http://www.zetomall.com";

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_include;
    }

    @Override
    protected void init() {
        webView = AgentWeb//
                .with(this)//传入Activity
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为
                // RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()//
                .go(ADRESS);

    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager
            .ReceivedTitleCallback() {


        @Override
        public void onReceivedTitle(WebView view, String sss) {
            title.setTitle(sss);
        }
    };
}
