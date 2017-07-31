package com.yuwubao.zytexpress.frag;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.DisPatchAndCountActivity;
import com.yuwubao.zytexpress.activity.ProjectSelectActivity;
import com.yuwubao.zytexpress.activity.StickScanActivity;
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

    /**
     * 待提货列表
     */
    @OnClick(R.id.rl_th)
    public void onTihuoClick() {
        jump(JUMP_TYPE_PICK_UP);
    }

    /**
     * 调度列表
     */
    @OnClick(R.id.rl_td)
    public void onTiedanClick() {
        JumpToActivity(DisPatchAndCountActivity.class);
    }

    /**
     * 待装车列表
     */
    @OnClick(R.id.rl_zc)
    public void onZhuangCheClick() {
//        JumpToActivity(IntoCarListActivity.class);
        jump(JUMP_TYPE_CAR_LIST);
    }

    /**
     * 查询 复核
     */
    @OnClick(R.id.rl_qt)
    public void onCkeckClick() {
        JumpToActivity(StickScanActivity.class);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {

    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        ImageLoader.getInstance().displayImage((String) model, (ImageView) view, ImageLoaderKit.normalLoadOption);
    }

    private void jump(int type) {
        Intent intent = new Intent(c, ProjectSelectActivity.class);
        intent.putExtra("jumpType", type);
        c.startActivity(intent);
    }
}
