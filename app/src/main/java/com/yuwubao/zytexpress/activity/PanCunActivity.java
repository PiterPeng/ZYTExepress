package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.InOrOutListBean;
import com.yuwubao.zytexpress.bean.InTableBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.ScanBean;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.DialogHelper;
import com.yuwubao.zytexpress.helper.SwipeToLoadLayoutHelper;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.listener.OnItemClickListener;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/9/26
 * e-mail: phlxplus@163.com
 * description: 盘存
 */

public class PanCunActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.typeName)
    TextView typeName;
    @BindView(R.id.panName)
    TextView panName;
    @BindView(R.id.projectName)
    TextView projectName;
    @BindView(R.id.pan_zhangM)
    TextView panZhangM;
    @BindView(R.id.pan_panD)
    TextView panPanD;
    @BindView(R.id.pan_chaY)
    TextView panChaY;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    List<InTableBean.ResultBean> typeList = new ArrayList<>();
    List<InTableBean.ResultBean> pmList = new ArrayList<>();
    List<InTableBean.ResultBean> panList = new ArrayList<>();
    String userId;
    private int scanType = -1;

    private boolean isLoadMoreClose = false;
    List<InOrOutListBean.ResultBean.PagesBean.ContentBean> contentBeen;
    CommonAdapter adapter;
    EmptyWrapper emptyWrapper;
    int currentPage = 1;
    int id = -1;
    int pageSize = 10;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_pancun;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        title.setTitle("盘存");
        initDatas();
        setSwipe();
        setComAdapter();
    }

    private void initDatas() {
        typeList.add(new InTableBean.ResultBean(1, "项目盘存"));
        typeList.add(new InTableBean.ResultBean(2, "储位盘存"));
        typeList.add(new InTableBean.ResultBean(3, "批次盘存"));
        typeList.add(new InTableBean.ResultBean(4, "日期盘存"));
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
                if (id != -1) {
                    swipeToLoadLayout.setRefreshing(true);
                }
            }
        });
    }

    public void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.GETINVENTORYITEMLIST)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("id", id + "")//
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
                            panZhangM.setText(bean.getTotal() + "");//账面
                            panPanD.setText(bean.getPoint() + "");//盘点
                            panChaY.setText(bean.getUn_point() + "");//差异
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

    private void setComAdapter() {
        contentBeen = new ArrayList<>();
        adapter = new CommonAdapter<InOrOutListBean.ResultBean.PagesBean.ContentBean>(c, R.layout.item_frag_yidian,
                contentBeen) {
            @Override
            protected void convert(ViewHolder holder, final InOrOutListBean.ResultBean.PagesBean.ContentBean o, int
                    position) {
                if (o != null) {
                    holder.setText(R.id.in_chu_wei, "储位号：" + o.getWhBinCode());
                    holder.setText(R.id.in_SN, "SN：" + o.getSn());
                    holder.setText(R.id.in_PO, "PO号：" + o.getPo());
                    holder.setText(R.id.in_liao_hao, "料号：" + o.getCustomerPartNo());
                    holder.setText(R.id.in_shu_liang, "数量：" + o.getQuantity());
                }
                holder.setVisible(R.id.scan_chu_wei, false);
            }
        };
        swipeTarget.setLayoutManager(new LinearLayoutManager(c));
        swipeTarget.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        emptyWrapper = new EmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.item_empty);
        swipeTarget.setAdapter(emptyWrapper);
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
        super.onDestroy();
    }

    @OnClick({R.id.ll_typeName, R.id.ll_projectName, R.id.ll_panName, R.id.in_start_scan, R.id.in_clear, R.id
            .in_submit})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
        intent.putExtra(AppConfig.SCAN_ID, id);
        switch (view.getId()) {
            case R.id.ll_typeName://类型
                DialogHelper.showPopupwindow(c, typeList, typeName, new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, RecyclerView.ViewHolder holder, int position) {
                        typeName.setText(typeList.get(position).getName());
                        int type = typeList.get(position).getId();
                        getPanList(type);
                    }
                });
                break;
            case R.id.ll_panName://盘存单号
                DialogHelper.showPopupwindow(c, panList, panName, new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, RecyclerView.ViewHolder holder, int position) {
                        panName.setText(panList.get(position).getName());
                        id = panList.get(position).getId();
                        getProjectList();
                        initData();
                    }
                });
                break;
            case R.id.ll_projectName://盘存项目
                DialogHelper.showPopupwindow(c, pmList, projectName, new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, RecyclerView.ViewHolder holder, int position) {
                        projectName.setText(pmList.get(position).getName());
                        int id = pmList.get(position).getId();
                        getScanMode(id);
                    }
                });
                break;
            case R.id.in_start_scan://扫描
                if (scanType == -1) {
                    UIHelper.showMessage(c, "请先选择项目");
                    return;
                }
                intent.putExtra(AppConfig.SCAN_INDEX, scanType);
                intent.putExtra("type", "pancun");
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
            case R.id.in_clear:
                break;
            case R.id.in_submit:
                break;
        }
    }

    private void getPanList(int type) {
        OkHttpUtils//
                .get()//
                .url(Urls.GETINVENTORYLIST)//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("type", type + "")// 0 项目 1 储位 2批次 3 日期
                .build()//
                .execute(new AppGsonCallback<InTableBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(InTableBean response, int id) {
                        super.onResponseOK(response, id);
                        List<InTableBean.ResultBean> temp = response.getResult();
                        if (temp != null) {
                            if (temp.size() == 0) {
                                UIHelper.showMessage(c, "暂无单号可选择");
                                panName.setText("暂无单号可选择");
                            } else {
                                panName.setText("请选择盘存单");
                            }
                            panList.clear();
                            panList.addAll(temp);
                        }
                    }
                });
    }

    private void getProjectList() {
        OkHttpUtils//
                .get()//
                .url(Urls.GETPROJECT)//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("id", id + "").build()//
                .execute(new AppGsonCallback<InTableBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(InTableBean response, int id) {
                        super.onResponseOK(response, id);
                        List<InTableBean.ResultBean> temp = response.getResult();
                        if (temp != null) {
                            if (temp.size() == 0) {
                                UIHelper.showMessage(c, "暂无项目可选择");
                                projectName.setText("暂无项目可选择");
                            } else {
                                projectName.setText("请选择项目");
                            }
                            pmList.clear();
                            pmList.addAll(temp);
                        }
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
                .addParams("type", "0")//０出库　１入库
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
}
