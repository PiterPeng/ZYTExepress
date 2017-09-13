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
import com.yuwubao.zytexpress.bean.Count3Bean;
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

public class CountFragment extends BaseFragement implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.tv_road_case)
    TextView tv_RoadCase;
    @BindView(R.id.tv_news)
    TextView tv_News;
    @BindView(R.id.tv_weiquan)
    TextView tv_Wq;
    @BindView(R.id.v_road_case)
    View v_RoadCase;
    @BindView(R.id.v_news)
    View v_News;
    @BindView(R.id.v_weiquan)
    View v_Wq;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    private String type;//0：按仓库统计；1：按客户统计；2：按项目统计
    List<Count3Bean.ResultBean> resultBeen;
    CommonAdapter adapter;
    private TextView[] textViews;
    private View[] views;

    int currentPage = 1;
    int pageSize = 10;

    private String userId;
    int jumpType = -1;//0: 提货预知；1：派件预知；2：到货预知

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_count;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        getBundle();
        setSwipe();
        initView();
        setComAdapter();
//        initData();
    }

    private void getBundle() {
        jumpType = getArguments().getInt("jumpType");
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
        type = "0";
        textViews = new TextView[]{tv_RoadCase, tv_News, tv_Wq};
        views = new View[]{v_RoadCase, v_News, v_Wq};
    }

    private boolean isLoadMoreClose = false;

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.COUNT_COUNT)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("countType", type)//
                .addParams("predictType", jumpType + "")//
                .build()//
                .execute(new AppGsonCallback<Count3Bean>(new RequestModel(c)) {
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
                    public void onResponseOK(Count3Bean response, int id) {
                        super.onResponseOK(response, id);
                        if (currentPage == 1) {
                            resultBeen.clear();
                        }
                        List<Count3Bean.ResultBean> temp = response.getResult();
                        if (pageSize > temp.size()) {
                            isLoadMoreClose = true;
                        } else {
                            swipeToLoadLayout.setLoadMoreEnabled(true);
                        }
                        resultBeen.addAll(temp);
                        adapter.notifyDataSetChanged();
                        currentPage++;
                    }
                });
    }

    private void setComAdapter() {
        resultBeen = new ArrayList<>();
        adapter = new CommonAdapter<Count3Bean.ResultBean>(c, R.layout.item_recyclerview_count, resultBeen) {
            @Override
            protected void convert(ViewHolder holder, Count3Bean.ResultBean o, int position) {
                holder.setText(R.id.quantity, String.valueOf(o.getQuantity()));
                holder.setText(R.id.volume, String.valueOf(o.getVolume()) + "m³");
                holder.setText(R.id.width, String.valueOf(o.getWeight()) + "kg");
                holder.setText(R.id.shipperName, o.getCustomerName());
            }

        };
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        swipeTarget.setAdapter(adapter);
    }

    private int currentIndex;

    /**
     * 仓库
     */
    @OnClick(R.id.ll_road_case)
    public void onRoadCaseClick() {
        if (currentIndex == 1) {
            return;
        }
        type = "0";
        clearAndShowIndex(0);
        initData();
        currentIndex = 1;
    }

    /**
     * 客户
     */
    @OnClick(R.id.ll_news)
    public void onNewsClick() {
        if (currentIndex == 2) {
            return;
        }
        type = "1";
        clearAndShowIndex(1);
        initData();
        currentIndex = 2;
    }

    /**
     * 项目
     */
    @OnClick(R.id.ll_weiquan)
    public void onWeiQuanClick() {
        if (currentIndex == 3) {
            return;
        }
        type = "2";
        clearAndShowIndex(2);
        initData();
        currentIndex = 3;
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
