package com.yuwubao.zytexpress.frag;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.HistoryBean;
import com.yuwubao.zytexpress.bean.HistoryCountBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/31
 * e-mail: phlxplus@163.com
 * description: 货物统计
 */

public class HistoryFragment extends BaseFragement implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.tv_dth)
    TextView tvDth;
    @BindView(R.id.v_dth)
    View vDth;
    @BindView(R.id.tv_dzc)
    TextView tvDzc;
    @BindView(R.id.v_dzc)
    View vDzc;
    @BindView(R.id.tv_dtb)
    TextView tvDtb;
    @BindView(R.id.v_dtb)
    View vDtb;
    @BindView(R.id.tv_dfh)
    TextView tvDfh;
    @BindView(R.id.v_dfh)
    View vDfh;
    @BindView(R.id.total_dth)
    TextView totalDth;
    @BindView(R.id.total_dzc)
    TextView totalDzc;
    @BindView(R.id.total_dtb)
    TextView totalDtb;
    @BindView(R.id.total_dfh)
    TextView totalDfh;
    private String type;
    List<HistoryBean.ResultBean.ContentBean> resultBeen;
    CommonAdapter adapter;
    private TextView[] textViews;
    private View[] views;
    private String userId;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_history;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        setBottomCount();
        setSwipe();
        initView();
        setComAdapter();
    }

    private void setBottomCount() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.HISTORY_COUNT)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("type", "1")//
                .build()//
                .execute(new AppGsonCallback<HistoryCountBean>(new RequestModel(c).setShowProgress(false)) {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        swipeToLoadLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponseOK(HistoryCountBean response, int id) {
                        super.onResponseOK(response, id);
                        if (response.getResult() != null) {
                            HistoryCountBean.ResultBean resultBean = response.getResult().get(0);
                            totalDth.setText("待提货\n（" + resultBean.getTakeNum() + "）");
                            totalDzc.setText("待装车\n（" + resultBean.getCarNum() + "）");
                            totalDtb.setText("待贴标\n（" + resultBean.getLabeNum() + "）");
                            totalDfh.setText("待复核\n（" + resultBean.getCheckNum() + "）");
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

    private void initView() {
        type = "1";
        textViews = new TextView[]{tvDth, tvDzc, tvDtb, tvDfh};
        views = new View[]{vDth, vDzc, vDtb, vDfh};
    }

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.HISTORY_LIST)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("type", type)//
                .build()//
                .execute(new AppGsonCallback<HistoryBean>(new RequestModel(c)) {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        swipeToLoadLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponseOK(HistoryBean response, int id) {
                        super.onResponseOK(response, id);
                        List<HistoryBean.ResultBean.ContentBean> temp = response.getResult().getContent();
                        resultBeen.clear();
                        resultBeen.addAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setComAdapter() {
        resultBeen = new ArrayList<>();
        adapter = new CommonAdapter<HistoryBean.ResultBean.ContentBean>(c, R.layout.item_recyclerview_history,
                resultBeen) {
            @Override
            protected void convert(ViewHolder holder, HistoryBean.ResultBean.ContentBean o, int position) {
                holder.setText(R.id.shipperName, o.getItemName());
                holder.setText(R.id.type, o.getItemCode());
                holder.setText(R.id.quantity, String.valueOf(o.getQuantity()));
                holder.setText(R.id.day, String.valueOf(o.getStopDay()));
                holder.setText(R.id.status, o.getStatusX());
            }

        };
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        swipeTarget.setAdapter(adapter);
    }


    /**
     * 初始化
     */
    private void clearAndShowIndex(int index) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setTextColor(getResources().getColor(R.color.base_white));
            views[i].setVisibility(View.INVISIBLE);
        }
        textViews[index].setTextColor(getResources().getColor(R.color.white));
        views[index].setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMore() {
        initData();
    }

    @Override
    public void onRefresh() {
        initData();
        setBottomCount();
    }

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroyView();
    }

    private int currentIndex;

    @OnClick({R.id.ll_dth, R.id.ll_dzc, R.id.ll_dtb, R.id.ll_dfh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dth:
                switchClick(1);
                break;
            case R.id.ll_dzc:
                switchClick(2);
                break;
            case R.id.ll_dtb:
                switchClick(3);
                break;
            case R.id.ll_dfh:
                switchClick(4);
                break;
        }
    }

    private void switchClick(int index) {
        if (currentIndex == index) {
            return;
        }
        type = String.valueOf(index);
        clearAndShowIndex(index - 1);
        initData();
        currentIndex = index;
    }
}
