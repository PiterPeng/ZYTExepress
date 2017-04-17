package com.yuwubao.zytexpress.frag;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.DispatchBean;
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

/**
 * Created by Peng on 2017/3/31
 * e-mail: phlxplus@163.com
 * description: 调度清单
 */

public class DispatchFragment extends BaseFragement implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    CommonAdapter adapter;
    List<DispatchBean.ResultBean.ContentBean> contentBeen;
    int currentPage = 1;
    int pageSize = 10;
    private String userId;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_dispatch;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        setSwipe();
        setComAdapter();
//        initData();
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

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.DISPATCH)//
                .addParams(AppConfig.USER_ID, userId)//
                .build()//
                .execute(new AppGsonCallback<DispatchBean>(new RequestModel(c)) {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        swipeToLoadLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponseOK(DispatchBean response, int id) {
                        super.onResponseOK(response, id);
                        List<DispatchBean.ResultBean.ContentBean> temp = response.getResult().getContent();
                        contentBeen.clear();
                        contentBeen.addAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setComAdapter() {
        contentBeen = new ArrayList<>();
        adapter = new CommonAdapter<DispatchBean.ResultBean.ContentBean>(c, R.layout.item_recyclerview_dispatch,
                contentBeen) {
            @Override
            protected void convert(ViewHolder holder, DispatchBean.ResultBean.ContentBean o, int position) {
                holder.setText(R.id.itemName, o.getItemName());
                holder.setText(R.id.quantity, String.valueOf(o.getQuantity()));
                holder.setText(R.id.volume, String.valueOf(o.getVolume()) + "m³");
                holder.setText(R.id.width, String.valueOf(o.getWeight()) + "kg");
                holder.setText(R.id.shipperName, o.getShipperName());
            }

        };
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        swipeTarget.setAdapter(adapter);
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
