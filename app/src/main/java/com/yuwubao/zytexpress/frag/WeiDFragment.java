package com.yuwubao.zytexpress.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.InOrOutListBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/9/21
 * e-mail: phlxplus@163.com
 * description: 未点
 */

public class WeiDFragment extends BaseFragement implements OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.in_total)
    TextView inTotal;
    @BindView(R.id.in_weiD)
    TextView inWeiD;

    private boolean isLoadMoreClose = false;
    List<InOrOutListBean.ResultBean.PagesBean.ContentBean> contentBeen;
    CommonAdapter adapter;

    int currentPage = 1;
    String userId;
    int pageSize = 10;
    int id, scanId;
    int scanType = -1;//扫描类型

    EmptyWrapper emptyWrapper;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            id = intent.getExtras().getInt("id");
            onRefresh();
        }
    };
    private int type;//0 出库 1 入库

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_weidian;
    }

    @Override
    protected void init() {
        resolverIntent();
        setSwipe();
        setComAdapter();
        c.registerReceiver(receiver, new IntentFilter("YiDianId"));
    }

    private void resolverIntent() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        type = getArguments().getInt("type");
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

    @Override
    public void onResume() {
        super.onResume();
        //进入页面自动刷新
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    private void setComAdapter() {
        contentBeen = new ArrayList<>();
        adapter = new CommonAdapter<InOrOutListBean.ResultBean.PagesBean.ContentBean>(c, R.layout.item_frag_weidian,
                contentBeen) {
            @Override
            protected void convert(ViewHolder holder, final InOrOutListBean.ResultBean.PagesBean.ContentBean o, int
                    position) {
                holder.setText(R.id.in_SN, "SN：" + o.getSn());
                holder.setText(R.id.in_PO, "PO号：" + o.getPo());
                holder.setText(R.id.in_liao_hao, "料号：" + o.getCustomerPartNo());
                holder.setText(R.id.in_shu_liang, "数量：" + o.getQuantity());
            }
        };
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        emptyWrapper = new EmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.item_empty);
        swipeTarget.setAdapter(emptyWrapper);
    }

    public void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.ISSCAN)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("id", id + "")//
                .addParams("status", "0")//状态 0 未点 1已点
                .addParams(AppConfig.CURRENT_PAGE, currentPage + "")//
                .addParams(AppConfig.PAGE_SIZE, pageSize + "")//
                .build()//
                .execute(new AppGsonCallback<InOrOutListBean>(new RequestModel(c)) {
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

                    @Override
                    public void onResponseOK(InOrOutListBean response, int id) {
                        super.onResponseOK(response, id);
                        if (currentPage == 1) {
                            contentBeen.clear();
                        }
                        InOrOutListBean.ResultBean.ScanNumBean bean = response.getResult().getScanNum();
                        if (bean != null) {
                            inTotal.setText(bean.getTotal() + "");
                            inWeiD.setText(bean.getUn_point() + "");
                        }
                        List<InOrOutListBean.ResultBean.PagesBean.ContentBean> temp = response.getResult().getPages()
                                .getContent();
                        if (temp != null) {
                            if (pageSize > temp.size()) {
                                isLoadMoreClose = true;
                            } else {
                                swipeToLoadLayout.setLoadMoreEnabled(true);
                            }
                            contentBeen.addAll(temp);
                        }
                        emptyWrapper.notifyDataSetChanged();
                        currentPage++;
                    }
                });
    }

    @Override
    public void onLoadMore() {
        initData();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        initData();
    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        currentPage = 1;
        c.unregisterReceiver(receiver);
        super.onDestroy();
    }
}
