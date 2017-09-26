package com.yuwubao.zytexpress.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.Count2Bean;
import com.yuwubao.zytexpress.bean.DetailBean;
import com.yuwubao.zytexpress.bean.PaiJianBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/9/20
 * e-mail: phlxplus@163.com
 * description: 派件预知
 */

public class SendPiecesActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
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
    @BindView(R.id.total_quantity)
    TextView totalQuantity;
    @BindView(R.id.total_volume)
    TextView totalVolume;
    @BindView(R.id.total_weight)
    TextView totalWeight;

    private String type = "0";//0: 接单时间；1：区域；2：项目
    List<PaiJianBean.ResultBean.ContentBean> contentBeen;
    CommonAdapter adapter;
    private TextView[] textViews;
    private View[] views;

    int currentPage = 1;
    int pageSize = 10;

    private String userId;

    List<Count2Bean.ResultBean> countBeen;
    List<DetailBean> detailBeen = new ArrayList<>();

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_send_pieces;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        setSwipe();
        initView();
        setComAdapter();
        setCount();
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

    /**
     * 底部汇总
     */
    private void setCount() {
        countBeen = new ArrayList<>();
        OkHttpUtils.get()//
                .tag(this)//
                .url(Urls.PAIJIAN_COUNT)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("predictType", "1")//
                .build()//
                .execute(new AppGsonCallback<Count2Bean>(new RequestModel(c).setShowProgress(false)) {
                    @Override
                    public void onResponseOK(Count2Bean response, int id) {
                        super.onResponseOK(response, id);
                        List<Count2Bean.ResultBean> temp = response.getResult();
                        countBeen.clear();
                        countBeen.addAll(temp);
                        if (!countBeen.isEmpty()) {
                            Count2Bean.ResultBean bean = countBeen.get(0);
                            totalQuantity.setText("总数量\n（" + bean.getQuantity() + "）");
                            totalWeight.setText("总重量\n（" + String.valueOf(bean.getWeight()) + "kg）");
                            totalVolume.setText("总体积\n（" + String.valueOf(bean.getVolume()) + "m³）");
                        }
                    }
                });
    }

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.PAIJIAN_LIST)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("countType", type)//
                .addParams(AppConfig.CURRENT_PAGE, currentPage + "")//
                .addParams(AppConfig.PAGE_SIZE, pageSize + "")//
                .build()//
                .execute(new AppGsonCallback<PaiJianBean>(new RequestModel(c)) {
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
                    public void onResponseOK(PaiJianBean response, int id) {
                        super.onResponseOK(response, id);
                        if (currentPage == 1) {
                            contentBeen.clear();
                        }
                        List<PaiJianBean.ResultBean.ContentBean> temp = response.getResult().getContent();
                        if (pageSize > temp.size()) {
                            isLoadMoreClose = true;
                        } else {
                            swipeToLoadLayout.setLoadMoreEnabled(true);
                        }
                        contentBeen.addAll(temp);
                        adapter.notifyDataSetChanged();
                        currentPage++;
                    }
                });
    }

    private void setComAdapter() {
        contentBeen = new ArrayList<>();
        adapter = new CommonAdapter<PaiJianBean.ResultBean.ContentBean>(c, R.layout.item_recyclerview_dispatch,
                contentBeen) {
            @Override
            protected void convert(ViewHolder holder, PaiJianBean.ResultBean.ContentBean o, int position) {
                holder.setText(R.id.itemName, o.getOrderNo());
                holder.setText(R.id.quantity, o.getAcceptTime());
                holder.setText(R.id.volume, o.getProjectName());
                holder.setText(R.id.width, o.getArea());
                holder.setText(R.id.shipperName, o.getQuantity() + "");

            }

        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //显示订单详细
                showDetailDialog(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        swipeTarget.setAdapter(adapter);
    }

    /**
     * 展示详细信息
     */
    private void showDetailDialog(int position) {
        if (null != contentBeen && !contentBeen.isEmpty()) {
            detailBeen.clear();
            detailBeen.add(new DetailBean("订单号：", contentBeen.get(position).getOrderNo()));
            detailBeen.add(new DetailBean("接单时间：", contentBeen.get(position).getAcceptTime()));
            detailBeen.add(new DetailBean("收件地址：", contentBeen.get(position).getAddress()));
            detailBeen.add(new DetailBean("区域：", contentBeen.get(position).getArea()));
            detailBeen.add(new DetailBean("收件人姓名：", contentBeen.get(position).getContactName()));
            detailBeen.add(new DetailBean("预警级别：", contentBeen.get(position).getMobile()));
            detailBeen.add(new DetailBean("收件人电话：", contentBeen.get(position).getProjectName()));
            detailBeen.add(new DetailBean("送达时间：", contentBeen.get(position).getRequireArrivalDate()));
            detailBeen.add(new DetailBean("提货仓库：", contentBeen.get(position).getShipperName()));
            detailBeen.add(new DetailBean("预警级别：", contentBeen.get(position).getLevel() + ""));
            detailBeen.add(new DetailBean("商品件数：", contentBeen.get(position).getQuantity() + ""));
            detailBeen.add(new DetailBean("订单状态：", contentBeen.get(position).getStatusX() + ""));
            detailBeen.add(new DetailBean("体积：", contentBeen.get(position).getVolume() + ""));
            detailBeen.add(new DetailBean("重量：", contentBeen.get(position).getWeight() + ""));
        }
        Dialog dialog = new Dialog(c, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(c).inflate(R.layout.dialog_show_detail, null);
        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recyclerView_dialog);
        CommonAdapter commonAdapter = new CommonAdapter<DetailBean>(c, R.layout.item_dialog, detailBeen) {
            @Override
            protected void convert(ViewHolder holder, DetailBean bean, int position) {
                holder.setText(R.id.dialog_title, bean.getTitle());
                holder.setText(R.id.dialog_content, bean.getContent());
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(commonAdapter);
        //将布局设置给Dialog
        dialog.setContentView(dialogView);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//            lp.y = 20;//设置Dialog距离底部的距离
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#FFFFFF"));
        //设置SelectPicPopupWindow弹出窗体的背景
        dialogWindow.setBackgroundDrawable(dw);
        dialog.show();
    }


    private int currentIndex;

    /**
     * 按日期统计
     */
    @OnClick(R.id.ll_road_case)
    public void onRoadCaseClick() {
        if (currentIndex == 1) {
            return;
        }
        type = "0";
        clearAndShowIndex(0);
        currentIndex = 1;
    }

    /**
     * 按区域统计
     */
    @OnClick(R.id.ll_news)
    public void onNewsClick() {
        if (currentIndex == 2) {
            return;
        }
        type = "1";
        clearAndShowIndex(1);
        currentIndex = 2;
    }

    /**
     * 按项目统计
     */
    @OnClick(R.id.ll_weiquan)
    public void onWeiQuanClick() {
        if (currentIndex == 3) {
            return;
        }
        type = "2";
        clearAndShowIndex(2);
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
        currentPage = 1;
        initData();
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
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        currentPage = 1;
        super.onDestroy();
    }
}
