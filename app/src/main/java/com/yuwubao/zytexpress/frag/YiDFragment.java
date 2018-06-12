package com.yuwubao.zytexpress.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.PDAScanActivity;
import com.yuwubao.zytexpress.bean.BaseBean;
import com.yuwubao.zytexpress.bean.CodeBean;
import com.yuwubao.zytexpress.bean.InOrOutListBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.ScanBean;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/9/21
 * e-mail: phlxplus@163.com
 * description: 已点
 */

public class YiDFragment extends BaseFragement implements OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.in_total)
    TextView inTotal;
    @BindView(R.id.in_yiD)
    TextView inYiD;
    @BindView(R.id.in_weiD)
    TextView inWeiD;
    @BindView(R.id.in_ruK)
    TextView inRuK;
    @BindView(R.id.inTo)
    TextView inTo;
    @BindView(R.id.in_ChaX)
    TextView in_ChaX;
    @BindView(R.id.in_submit)
    TextView in_submit;

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
            //判断是否有拆箱
            getChaiX();
        }
    };

    private void getChaiX() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.ISUNPACK)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("id", id + "")//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c).setShowProgress(false)) {
                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        String key = response.getMessage();//0没有拆箱的，1有
                        in_ChaX.setVisibility(TextUtils.equals("1", key) ? View.VISIBLE : View.GONE);
                    }
                });
    }

    private int type;//0 出库 1 入库

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_yidian;
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
        inTo.setText(type == 0 ? "已出库" : "已入库");
        in_submit.setText(type == 0 ? "确认出库" : "确认入库");
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
        adapter = new CommonAdapter<InOrOutListBean.ResultBean.PagesBean.ContentBean>(c, R.layout.item_frag_yidian,
                contentBeen) {
            @Override
            protected void convert(ViewHolder holder, final InOrOutListBean.ResultBean.PagesBean.ContentBean o, int
                    position) {
                String binCode = o.getWhBinCode() == null ? "" : "" + o.getWhBinCode();
                holder.setText(R.id.in_chu_wei, type == 0 ? "外包号：" : "储位号：" + binCode);
                holder.setText(R.id.scan_chu_wei, type == 0 ? "外包号" : "储位号");
                holder.setText(R.id.in_SN, "SN：" + o.getSn());
                holder.setText(R.id.in_PO, "PO号：" + o.getPo());
                holder.setText(R.id.in_liao_hao, "料号：" + o.getCustomerPartNo());
                holder.setText(R.id.in_shu_liang, "数量：" + o.getQuantity());
                holder.setOnClickListener(R.id.scan_chu_wei, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CHUWEI);
                        intent.putExtra("id", o.getId());
                        if (AppConfig.isPDA) {
                            JumpToActivity(PDAScanActivity.class, intent);
                        } else {
                            JumpToActivity(CaptureActivity.class, intent);
                        }
                    }
                });
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
                .addParams("status", "1")//状态 0 未点 1已点
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
                            inYiD.setText(bean.getPoint() + "");
                            inWeiD.setText(bean.getUn_point() + "");
                            inRuK.setText(bean.getIn_out() + "");
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

    @OnClick({R.id.in_start_scan, R.id.in_clear, R.id.in_submit, R.id.in_ChaX})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
        intent.putExtra(AppConfig.SCAN_ID, id);
        switch (view.getId()) {
            case R.id.in_start_scan://开始扫描
                if (scanType == -1) {
                    UIHelper.showMessage(c, "请先选择项目");
                    return;
                }
                intent.putExtra(AppConfig.SCAN_INDEX, scanType);
                switch (scanType) {
                    case 0://SN
                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN_SN);
                        break;
                    case 1://SN+69
                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN_SN_69);
                        break;
                    case 2://SN+买方料号
                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN_SN_BUY);
                        break;
                    case 3://SN+卖方料号
                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN_SN_SELL);
                        break;
                    case 4://SN+买方料号+卖方料号
                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN_SN_BUY_SELL);
                        break;
                }
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                break;
            case R.id.in_clear://清除
                UIHelper.showMessage(c, "清除");
                break;
            case R.id.in_submit://确认入库
                sureInOrOut();
                break;
            case R.id.in_ChaX://拆箱
                Intent intent1 = new Intent();
                intent1.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_XIANG_HAO_O);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent1);
                } else {
                    JumpToActivity(CaptureActivity.class, intent1);
                }
                break;
        }
    }

    /**
     * 确认出入库
     */
    private void sureInOrOut() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.GETCARTONS)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("id", id + "")//
                .build()//
                .execute(new AppGsonCallback<CodeBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(CodeBean response, int ids) {
                        super.onResponseOK(response, ids);
                        int number = response.getResult();
                        UIHelper.showMyCustomDialog(c, type == 1 ? "本次入库商品" + number + "箱" + "，是否确认？" : "本次出库商品" +
                                number + "箱" + "，是否确认？", "确认", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                OkHttpUtils//
                                        .post()//
                                        .tag(this)//
                                        .url(Urls.CONFIRMWAREHOUSING)//
                                        .addParams(AppConfig.USER_ID, userId)//
                                        .addParams("id", id + "")//
                                        .build()//
                                        .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                                            @Override
                                            public void onResponseOK(BaseBean response, int id) {
                                                super.onResponseOK(response, id);

                                            }
                                        });
                            }
                        }, null);
                    }
                });
    }

    public void getScanMode(int scanId) {
        OkHttpUtils//
                .get()//
                .url(Urls.GETSCANMODE)//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("id", scanId + "")//０出库　１入库
                .addParams("type", "1")//０出库　１入库
                .build()//
                .execute(new AppGsonCallback<ScanBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(ScanBean response, int id) {
                        super.onResponseOK(response, id);
                        ScanBean.ResultBean temp = response.getResult();
                        if (temp != null) {
                            scanType = temp.getIndex();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroyView();
    }
}
