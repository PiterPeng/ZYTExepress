package com.yuwubao.zytexpress.frag;

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
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.Count4Bean;
import com.yuwubao.zytexpress.bean.QueryListBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/4/24
 * e-mail: phlxplus@163.com
 * description: 签收—>统计
 */

public class Count2Fragment extends BaseFragement implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    View headerView;
    HeaderAndFooterWrapper wrapper;
    int currentPage = 1;
    int pageSize = 10;
    private String userId;
    private CommonAdapter<QueryListBean.ResultBean.ContentBean> adapter;
    private List<QueryListBean.ResultBean.ContentBean> queryListBean;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_mangsao;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        setSwipe();
        initAdapter();
        addHeader();
    }

    private void addHeader() {
        wrapper = new HeaderAndFooterWrapper(adapter);
        headerView = LayoutInflater.from(c).inflate(R.layout.header_count2, null);
        wrapper.addHeaderView(headerView);
        swipeTarget.setAdapter(wrapper);
    }

    private void initAdapter() {
        queryListBean = new ArrayList<>();
        adapter = new CommonAdapter<QueryListBean.ResultBean.ContentBean>(c, R.layout.item_recyclerview_query,
                queryListBean) {
            @Override
            protected void convert(ViewHolder holder, QueryListBean.ResultBean.ContentBean bean, int position) {
                try {
                    holder.setText(R.id.name, bean.getItemName());//名字
                    holder.setText(R.id.type, bean.getItemCode());//类型
                    holder.setText(R.id.count, String.valueOf(bean.getQuantity()));//件数
                    holder.setText(R.id.subNo, bean.getSubFaceOrderNo() + "");//子单号
                    holder.setText(R.id.status, bean.getStatusX());//状态
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
    }

    private boolean isLoadmoreColose = false;

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .url(Urls.QUERY_COUNT)//
                .build()//
                .execute(new AppGsonCallback<Count4Bean>(new RequestModel(c).setShowProgress(false)) {
                    @Override
                    public void onResponseOK(Count4Bean response, int id) {
                        super.onResponseOK(response, id);
                        if (response.getResult() != null) {
                            Count4Bean.ResultBean resultBean = response.getResult().get(0);
                            if (resultBean != null) {
                                if (headerView != null) {
                                    TextView total = (TextView) headerView.findViewById(R.id.tv_total);
                                    TextView yiTB = (TextView) headerView.findViewById(R.id.tv_yiTB);
                                    TextView yiFH = (TextView) headerView.findViewById(R.id.tv_yiFH);
                                    total.setText(resultBean.getTotle() + "");
                                    yiTB.setText(resultBean.getLastTake() + "");
                                    yiFH.setText(resultBean.getTakeNum() + "");
                                }
                            }
                        }
                    }
                });
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.QUERY_LIST)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams(AppConfig.CURRENT_PAGE, currentPage + "")//
                .addParams(AppConfig.PAGE_SIZE, pageSize + "")//
                .build()//
                .execute(new AppGsonCallback<QueryListBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(QueryListBean response, int id) {
                        super.onResponseOK(response, id);
                        if (currentPage == 1) {
                            queryListBean.clear();
                        }
                        List<QueryListBean.ResultBean.ContentBean> temp = response.getResult().getContent();
                        if (pageSize > temp.size()) {
                            isLoadmoreColose = true;
                        } else {
                            swipeToLoadLayout.setLoadMoreEnabled(true);
                        }
                        queryListBean.addAll(temp);
                        adapter.notifyDataSetChanged();
                        wrapper.notifyDataSetChanged();
                        currentPage++;
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        if (swipeToLoadLayout.isRefreshing()) {
                            swipeToLoadLayout.setRefreshing(false);
                        } else if (swipeToLoadLayout.isLoadingMore()) {
                            swipeToLoadLayout.setLoadingMore(false);
                            if (isLoadmoreColose) {
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                            }
                        }
                    }
                });
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
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
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
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(this);
        currentPage = 1;
        super.onDestroyView();
    }
}
