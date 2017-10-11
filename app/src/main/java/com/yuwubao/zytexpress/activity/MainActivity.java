package com.yuwubao.zytexpress.activity;

import android.app.Service;
import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.AppManager;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.DataBase;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.frag.BaseFragement;
import com.yuwubao.zytexpress.frag.HistoryFragment;
import com.yuwubao.zytexpress.frag.HomeFragment;
import com.yuwubao.zytexpress.frag.MineFragment;
import com.yuwubao.zytexpress.frag.ShopStoreFragment;
import com.yuwubao.zytexpress.frag.StickScanFragment;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.widget.CustomViewPager;
import com.yuwubao.zytexpress.widget.HeaderBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.pages)
    CustomViewPager pages;
    @BindView(R.id.hb_main)
    HeaderBar headerBar;
    List<BaseFragement> rootPages;
    int defaultPageIndex = 0;
    int level;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initDatas();
        setUpTop();
        setUpBottom();
        setUpPage();
    }

    private void initDatas() {
        DataBase.saveBoolean("starType", true);
        AudioManager audioMgr = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        int maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            level = user.getCompanyLevel();
        }
    }

    private void setUpTop() {
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
                .FLAG_FULLSCREEN);
        headerBar.setTitle(getString(R.string.app_name));
        headerBar.hiddenLeft(true);
    }

    void setUpBottom() {
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setBackgroundResource(R.color.white);
        if (level > 1) {//网点登录
            bottomNavigationBar.addItem(createItem(R.drawable.bottom_icon_home, getString(R.string.tab_travel)))
                    .addItem(createItem(R.drawable.bottom_icon_writein, getString(R.string.tab_news))).addItem
                    (createItem(R.drawable.bottom_icon_user, getString(R.string.tab_services)))
                    .setFirstSelectedPosition(defaultPageIndex).initialise();
        } else {
            bottomNavigationBar.addItem(createItem(R.drawable.bottom_icon_home, getString(R.string.tab_travel)))
                    .addItem(createItem(R.drawable.bottom_icon_sign, getString(R.string.tab_interaction))).addItem
                    (createItem(R.drawable.bottom_icon_writein, getString(R.string.tab_news))).addItem(createItem(R
                    .drawable.bottom_icon_history, getString(R.string.tab_history))).addItem(createItem(R.drawable
                    .bottom_icon_user, getString(R.string.tab_services))).setFirstSelectedPosition(defaultPageIndex)
                    .initialise();
        }
    }


    private BottomNavigationItem createItem(int iconId, String name) {
        return new BottomNavigationItem(iconId, name).setActiveColorResource(R.color.base_blue).setInActiveColor(R
                .color.viewfinder_mask);
    }

    void setUpPage() {
        rootPages = new ArrayList<>();
        if (level > 1) {//网点登录
            rootPages.add(new HomeFragment());
            rootPages.add(new ShopStoreFragment());
            rootPages.add(new MineFragment());
        } else {
            rootPages.add(new HomeFragment());
            rootPages.add(new StickScanFragment());
            rootPages.add(new ShopStoreFragment());
            rootPages.add(new HistoryFragment());
            rootPages.add(new MineFragment());
        }
        pages.setNoScroll(true);
        pages.setOffscreenPageLimit(rootPages.size());
        pages.setAdapter(rootAdapter);
        pages.setCurrentItem(defaultPageIndex);
    }


    FragmentPagerAdapter rootAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return rootPages.get(position);
        }

        @Override
        public int getCount() {
            return rootPages.size();
        }
    };

    @Override
    public void onTabSelected(int position) {
        pages.setCurrentItem(position);
        if (level > 1) {//网点登录
        } else {
            headerBar.setVisibility(position == 1 || position == 2 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - AppConfig.lastPressTime <= AppConfig.exitduration) {
            AppManager.getAppManager().AppExit();
        } else {
            AppConfig.lastPressTime = currentTime;
            DataBase.saveBoolean("starType", false);
            UIHelper.showMessage(this, getString(R.string.quit_app));
        }
    }
}
