package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.IntoCarListBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/22
 * e-mail: phlxplus@163.com
 * description: 待装车列表
 */

public class IntoCarListActivity extends BaseActivity implements OnRefreshListener,
        OnLoadMoreListener {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    List<IntoCarListBean.ResultBean.ContentBean> contentBeen;
    CommonAdapter adapter;
    HeaderAndFooterWrapper wrapper;
    TextView textView;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_car_list;
    }

    @Override
    protected void init() {
        setHeader();
        setSwipe();
        setmAdapter();
        addHeader();
        initData();
    }

    private void addHeader() {
        wrapper = new HeaderAndFooterWrapper(adapter);
        View headerView = LayoutInflater.from(c).inflate(R.layout.header_car_list, null);
        textView = (TextView) headerView.findViewById(R.id.total);
        wrapper.addHeaderView(headerView);
        swipeTarget.setAdapter(wrapper);
    }

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.INTO_CAR_LIST)//
                .build()//
                .execute(new AppGsonCallback<IntoCarListBean>(new
                        RequestModel(c)) {
                    @Override
                    public void onResponseOK(IntoCarListBean response, int id) {
                        super.onResponseOK(response, id);
                        textView.setText("商品总数：" + String.valueOf(response.getResult()
                                .getTotalElements
                                        ()));
                        List<IntoCarListBean.ResultBean.ContentBean> temp = response.getResult()
                                .getContent();
                        contentBeen.clear();
                        contentBeen.addAll(temp);
                        wrapper.notifyDataSetChanged();
                    }
                });
    }

    private void setHeader() {
        title.setTitle(getString(R.string.into_car));
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

    private void setmAdapter() {
        contentBeen = new ArrayList<>();
        adapter = new CommonAdapter<IntoCarListBean.ResultBean.ContentBean>(c, R.layout
                .item_car_list, contentBeen) {
            @Override
            protected void convert(ViewHolder holder, IntoCarListBean.ResultBean.ContentBean
                    details, int position) {
                holder.setText(R.id.order_No, details.getOrderNo());
                holder.setText(R.id.number, details.getItemCode());
                holder.setText(R.id.name, details.getItemName());
                holder.setText(R.id.time, details.getUpdateTime());
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CAR);
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_ZHISAO);
                intent.putExtra(AppConfig.ORDER_ID, contentBeen.get(position + 1).getId());
                JumpToActivity(CaptureActivity.class, intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int
                    position) {
                return false;
            }
        });
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
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
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
