package com.yuwubao.zytexpress.frag;

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
import com.yuwubao.zytexpress.bean.CountBean;
import com.yuwubao.zytexpress.bean.GoodsDetailsBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/20
 * e-mail: phlxplus@163.com
 * description: 指定扫描
 */

public class AppointScanFragment extends BaseFragement implements OnRefreshListener,
        OnLoadMoreListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    private CommonAdapter adapter;
    private List<GoodsDetailsBean.ResultBean> goodsDetailsBeen;
    View headerView;
    HeaderAndFooterWrapper wrapper;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_zhisao;
    }

    @Override
    protected void init() {
        setSwipe();
        setmAdapter();
        addHeader();
        initData();
    }

    private void addHeader() {
        wrapper = new HeaderAndFooterWrapper(adapter);
        headerView = LayoutInflater.from(c).inflate(R.layout.header_zhisao, null);
        wrapper.addHeaderView(headerView);
        swipeTarget.setAdapter(wrapper);
        OkHttpUtils//
                .get()//
                .tag(this)//
                .addParams("type", "2")
                .url(Urls.COUNT)//
                .build()//
                .execute(new AppGsonCallback<CountBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(CountBean response, int id) {
                        super.onResponseOK(response, id);
                        CountBean.ResultBean resultBean = response.getResult().get(0);
                        TextView total = (TextView) headerView.findViewById(R.id.tv_total);
                        TextView weiTH = (TextView) headerView.findViewById(R.id.tv_weiTH);
                        TextView yiTH = (TextView) headerView.findViewById(R.id.tv_yiTH);
                        TextView weiZC = (TextView) headerView.findViewById(R.id.tv_weiZC);
                        TextView yiZC = (TextView) headerView.findViewById(R.id.tv_yiZC);
                        total.setText(resultBean.getTotle() + "");
                        weiTH.setText(resultBean.getLastTake() + "");
                        yiTH.setText(resultBean.getTakeNum() + "");
                        weiZC.setText(resultBean.getLastCar() + "");
                        yiZC.setText(resultBean.getCarNum() + "");
                        wrapper.notifyItemChanged(1);
                    }
                });
    }

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.PICK_UP)//
                .build()//
                .execute(new AppGsonCallback<GoodsDetailsBean>(new
                        RequestModel(c)) {
                    @Override
                    public void onResponseOK(GoodsDetailsBean response, int id) {
                        super.onResponseOK(response, id);
                        List<GoodsDetailsBean.ResultBean> temp = response.getResult();
                        goodsDetailsBeen.clear();
                        goodsDetailsBeen.addAll(temp);
                        wrapper.notifyDataSetChanged();
                    }
                });
    }

    private void setmAdapter() {
        goodsDetailsBeen = new ArrayList<>();
        adapter = new CommonAdapter<GoodsDetailsBean.ResultBean>(c, R.layout
                .item_pick_up, goodsDetailsBeen) {
            @Override
            protected void convert(ViewHolder holder, GoodsDetailsBean.ResultBean
                    details, int position) {
                holder.setText(R.id.order_No, details.getOldOrderNo());
                holder.setText(R.id.name, details.getItemName());
                holder.setText(R.id.volume, String.valueOf(details.getVolume()) + "m³");
                holder.setText(R.id.grossWeight, details.getGroosWeight() == null ? "0kg" : details
                        .getGroosWeight() + "kg");
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_ZHISAO);
                intent.putExtra(AppConfig.ORDER_ID, goodsDetailsBeen.get(position + 1).getOrderId
                        ());
                intent.putExtra(AppConfig.SCAN_MODE, goodsDetailsBeen.get(position + 1)
                        .getScanMode());
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
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
