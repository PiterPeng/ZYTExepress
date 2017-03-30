package com.yuwubao.zytexpress.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.Count2Bean;
import com.yuwubao.zytexpress.bean.DispatchBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/21
 * e-mail: phlxplus@163.com
 * description: 调度
 */

public class DispatchActivity extends BaseActivity {

    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.dispatchList)
    RecyclerView dispatchList;
    CommonAdapter adapter;
    List<DispatchBean.ResultBean.ContentBean> contentBeen;
    List<Count2Bean.ResultBean> countBeen;
    @BindView(R.id.total_quantity)
    TextView totalQuantity;
    @BindView(R.id.total_volume)
    TextView totalVolume;
    @BindView(R.id.total_weight)
    TextView totalWeight;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_dispatch;
    }

    @Override
    protected void init() {
        setCount();
        setTitleBar();
        setComAdapter();
        initData();
    }

    private void setCount() {
        countBeen = new ArrayList<>();
        OkHttpUtils
                .get()//
                .tag(this)//
                .url(Urls.DISPATCH_COUNT)//
                .build()//
                .execute(new AppGsonCallback<Count2Bean>(new RequestModel(c)) {
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
                .url(Urls.DISPATCH)//
                .build()//
                .execute(new AppGsonCallback<DispatchBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(DispatchBean response, int id) {
                        super.onResponseOK(response, id);
                        List<DispatchBean.ResultBean.ContentBean> temp = response.getResult()
                                .getContent();
                        contentBeen.clear();
                        contentBeen.addAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setComAdapter() {
        contentBeen = new ArrayList<>();
        adapter = new CommonAdapter<DispatchBean.ResultBean.ContentBean>(c, R.layout
                .item_recyclerview_dispatch, contentBeen) {
            @Override
            protected void convert(ViewHolder holder, DispatchBean.ResultBean.ContentBean o, int
                    position) {
                holder.setText(R.id.itemName, o.getItemName());
                holder.setText(R.id.quantity, String.valueOf(o.getQuantity()));
                holder.setText(R.id.volume, String.valueOf(o.getVolume()) + "m³");
                holder.setText(R.id.width, String.valueOf(o.getWeight()) + "kg");
                holder.setText(R.id.shipperName, o.getShipperName());
            }

        };
        dispatchList.setLayoutManager(new LinearLayoutManager(c));
        dispatchList.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration
                .VERTICAL));
        dispatchList.setAdapter(adapter);
    }

    private void setTitleBar() {
        title.setTitle(getString(R.string.dispatch));
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
