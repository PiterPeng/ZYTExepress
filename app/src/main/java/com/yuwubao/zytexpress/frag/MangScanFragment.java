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
import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.CountBean;
import com.yuwubao.zytexpress.bean.MangScanBean;
import com.yuwubao.zytexpress.bean.RequestModel;
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
 * Created by Peng on 2017/3/20
 * e-mail: phlxplus@163.com
 * description: 盲扫
 */

public class MangScanFragment extends BaseFragement implements OnRefreshListener,
        OnLoadMoreListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    private CommonAdapter<MangScanBean.ResultBean.ContentBean> adapter;
    private List<MangScanBean.ResultBean.ContentBean> mangScanBeen;
    View headerView;
    HeaderAndFooterWrapper wrapper;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_mangsao;
    }

    @Override
    protected void init() {
        setSwipe();
        initAdapter();
        addHeader();
        initData();
    }

    private void addHeader() {
        wrapper = new HeaderAndFooterWrapper(adapter);
        headerView = LayoutInflater.from(c).inflate(R.layout.header_mangsao, null);
        TextView toScan = (TextView) headerView.findViewById(R.id.to_Scan);
        toScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.currentType = AppConfig.SCAN_TYPE_CODE_69;
                AppConfig.enterType = AppConfig.ENTER_TYPE_MANGSAO;
                JumpToActivity(CaptureActivity.class);
            }
        });
        wrapper.addHeaderView(headerView);
        swipeTarget.setAdapter(wrapper);
        OkHttpUtils//
                .get()//
                .tag(this)//
                .addParams("type", "1")
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

    private void initAdapter() {
        mangScanBeen = new ArrayList<>();
        adapter = new CommonAdapter<MangScanBean.ResultBean.ContentBean>(c, R.layout
                .item_recyclerview_mangsao, mangScanBeen) {
            @Override
            protected void convert(ViewHolder holder, MangScanBean.ResultBean.ContentBean bean,
                                   int position) {
                try {
                    holder.setText(R.id.name, bean.getItemName());
                    holder.setText(R.id.type, bean.getItemCode());
                    holder.setText(R.id.count, bean.getQuantity() + "\n" + "（" + (bean.getQuantity()
                            - bean.getLENGTH()) + "/" + bean.getLENGTH() + "）");
                    holder.setText(R.id.bei, bean.getHEIGHT() == 0 ? "未备案" : "已备案");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        OkHttpUtils//
                .get()//
                .url(Urls.MANG_SCAN)//
                .build()//
                .execute(new AppGsonCallback<MangScanBean>(new
                        RequestModel(c)) {
                    @Override
                    public void onResponseOK(MangScanBean response, int id) {
                        super.onResponseOK(response, id);
                        List<MangScanBean.ResultBean.ContentBean> temp = response.getResult()
                                .getContent();
                        mangScanBeen.clear();
                        mangScanBeen.addAll(temp);
                        wrapper.notifyDataSetChanged();
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
}
