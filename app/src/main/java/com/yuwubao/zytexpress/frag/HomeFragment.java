package com.yuwubao.zytexpress.frag;

import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.DispatchActivity;
import com.yuwubao.zytexpress.activity.PickUpActivity;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.utils.ImageLoaderKit;
import com.yuwubao.zytexpress.widget.BGABanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/8
 * e-mail: phlxplus@163.com
 * description: 主页
 */

public class HomeFragment extends BaseFragement implements BGABanner.OnItemClickListener,
        BGABanner.Adapter {
    @BindView(R.id.banner)
    BGABanner banner;
    List<String> urls;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_home;
    }

    @Override
    protected void init() {
        initData();
        setBanner();
    }

    private void setBanner() {
        banner.setData(urls, null);
        banner.setAdapter(this);
        banner.setOnItemClickListener(this);
    }

    private void initData() {
        urls = new ArrayList<>();
        urls.add(AppConfig.BANNER_IAMGE_URL_01);
        urls.add(AppConfig.BANNER_IAMGE_URL_02);
        urls.add(AppConfig.BANNER_IAMGE_URL_03);
        urls.add(AppConfig.BANNER_IAMGE_URL_04);
    }

    @OnClick(R.id.rl_th)
    public void onTihuoClick() {
        JumpToActivity(PickUpActivity.class);
    }

    @OnClick(R.id.rl_td)
    public void onTiedanClick() {
        JumpToActivity(DispatchActivity.class);
    }

    @OnClick(R.id.rl_zc)
    public void onZhuangCheClick() {
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        UIHelper.showMessage(c, "点击了第" + position + "张图片");
    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        ImageLoader.getInstance().displayImage((String) model, (ImageView) view, ImageLoaderKit
                .normalLoadOption);
    }
}
