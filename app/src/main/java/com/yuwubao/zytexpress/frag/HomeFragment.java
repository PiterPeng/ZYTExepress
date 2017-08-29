package com.yuwubao.zytexpress.frag;

import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.OMSActivity;
import com.yuwubao.zytexpress.activity.QianShouActivity;
import com.yuwubao.zytexpress.activity.TMSActivity;
import com.yuwubao.zytexpress.activity.WarehouseActivity;
import com.yuwubao.zytexpress.bean.BannerBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.utils.ImageLoaderKit;
import com.yuwubao.zytexpress.widget.BGABanner;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/8
 * e-mail: phlxplus@163.com
 * description: 主页
 */

public class HomeFragment extends BaseFragement implements BGABanner.OnItemClickListener, BGABanner.Adapter {
    @BindView(R.id.banner)
    BGABanner banner;
    List<String> urls;
    public static final int JUMP_TYPE_PICK_UP = 0x01;//提货
    public static final int JUMP_TYPE_CAR_LIST = 0x02;//装车

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_home;
    }

    @Override
    protected void init() {
        initData();
    }

    private void setBanner() {
        banner.setData(urls, null);
        banner.setAdapter(this);
        banner.setOnItemClickListener(this);
    }

    private void initData() {
        urls = new ArrayList<>();
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.BANNER_URLS)//
                .build()//
                .execute(new AppGsonCallback<BannerBean>(new RequestModel(c).setShowProgress(false)) {
                    @Override
                    public void onResponseOK(BannerBean response, int id) {
                        super.onResponseOK(response, id);
                        List<String> resultUrls = response.getResult();
                        urls.clear();
                        urls.addAll(resultUrls);
                        setBanner();
                    }
                });
    }

    /**
     * 订单信息管理（OMS)
     */
    @OnClick(R.id.rl_th)
    public void onTihuoClick() {
        JumpToActivity(OMSActivity.class);
    }

    /**
     * 货物流转管理（TMS）
     */
    @OnClick(R.id.rl_td)
    public void onTiedanClick() {
        JumpToActivity(TMSActivity.class);
    }

    /**
     * 订单签收管理（SMS）
     */
    @OnClick(R.id.rl_zc)
    public void onZhuangCheClick() {
        JumpToActivity(QianShouActivity.class);
    }

    /**
     * 仓库吞吐管理（WMS）
     */
    @OnClick(R.id.rl_qt)
    public void onCkeckClick() {
        JumpToActivity(WarehouseActivity.class);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {

    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        ImageLoader.getInstance().displayImage(urls.get(position), (ImageView) view, ImageLoaderKit.normalLoadOption);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
