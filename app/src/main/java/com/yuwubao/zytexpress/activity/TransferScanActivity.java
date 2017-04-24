package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/4/24
 * e-mail: phlxplus@163.com
 * description: 中转扫描
 */

public class TransferScanActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    CommonAdapter adapter;
    List<String> typeList;
    String code_face;
    private String userId;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_car_list;
    }

    @Override
    protected void init() {
        initData();
        setHeader();
        setSwipe();
        setmAdapter();
    }

    private void setHeader() {
        title.setTitle(getString(R.string.transfer_scan));
    }

    private void setSwipe() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);

        SwipeToLoadLayoutHelper.enableSpeed(swipeToLoadLayout);
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);

                    }
                }
            }
        });
    }

    private void setmAdapter() {
        adapter = new CommonAdapter<String>(c, R.layout.simple_list, typeList) {
            @Override
            protected void convert(ViewHolder holder, String str, int position) {
                holder.setText(R.id.content, str);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_TRANSFER);
                intent.putExtra(AppConfig.CODE_FACE, typeList.get(position));
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                finish();

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.setAdapter(adapter);
    }

    private void initData() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        typeList = new ArrayList<>();
        typeList.add("中转");
        typeList.add("已分拨");
        typeList.add("已揽件");
        UIHelper.showMessage(c, "点击扫描类型进入相应扫描界面");
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
