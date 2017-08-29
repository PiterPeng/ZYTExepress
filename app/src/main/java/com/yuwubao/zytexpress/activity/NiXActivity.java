package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.NiXbean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/8/22
 * e-mail: phlxplus@163.com
 * description: 逆向代提货列表
 */

public class NiXActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.title)
    HeaderBar title;

    private CommonAdapter adapter;
    List<NiXbean.ResultBean.ContentBean> been;
    int currentPage = 1;
    int pageSize = 10;
    private boolean isLoadMoreClose = false;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_nix;
    }

    @Override
    protected void init() {
        setTitle();
        setSwipe();
        initAdapter();
//        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        initData();
    }

    private void initData() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            OkHttpUtils.get()//
                    .tag(this)//
                    .url(Urls.RE_ORDER_SIGN)//
                    .addParams("userId", user.getId() + "")//
                    .addParams(AppConfig.CURRENT_PAGE, currentPage + "")//
                    .addParams(AppConfig.PAGE_SIZE, pageSize + "")//
                    .build()//
                    .execute(new AppGsonCallback<NiXbean>(new RequestModel(c)) {

                        @Override
                        public void onResponseOK(NiXbean response, int id) {
                            super.onResponseOK(response, id);
                            if (currentPage == 1) {
                                been.clear();
                            }
                            List<NiXbean.ResultBean.ContentBean> temp = response.getResult().getContent();
                            if (pageSize > temp.size()) {
                                isLoadMoreClose = true;
                            } else {
                                swipeToLoadLayout.setLoadMoreEnabled(true);
                            }
                            been.addAll(temp);
                            adapter.notifyDataSetChanged();
                            currentPage++;
                        }

                        @Override
                        public void onAfter(int id) {
                            super.onAfter(id);
                            if (swipeToLoadLayout.isRefreshing()) {
                                swipeToLoadLayout.setRefreshing(false);
                            } else if (swipeToLoadLayout.isLoadingMore()) {
                                swipeToLoadLayout.setLoadingMore(false);
                                if (isLoadMoreClose) {
                                    swipeToLoadLayout.setLoadMoreEnabled(false);
                                }
                            }
                        }
                    });
        }
    }

    private void setTitle() {
        title.setTitle("逆向提货列表");
        UIHelper.showMessage(c, "点击某一项进入扫描界面。");
    }

    private void initAdapter() {
        been = new ArrayList<>();
        adapter = new CommonAdapter<NiXbean.ResultBean.ContentBean>(c, R.layout.item_nix, been) {
            @Override
            protected void convert(ViewHolder holder, NiXbean.ResultBean.ContentBean bean, int position) {
                if (bean != null) {
                    holder.setText(R.id.no, "订单号：" + bean.getOrderNo());
                    holder.setText(R.id.name, "商品名称：" + bean.getItemName());
                }
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_ZHISAO);
                intent.putExtra(AppConfig.ORDER_ID, been.get(position).getId());
                intent.putExtra(AppConfig.IS_NIXIANG, true);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        EmptyWrapper emptyWrapper = new EmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.item_empty);
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        swipeTarget.setAdapter(emptyWrapper);

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
//        swipeToLoadLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeToLoadLayout.setRefreshing(true);
//            }
//        });
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        initData();
    }

    @Override
    public void onLoadMore() {
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
