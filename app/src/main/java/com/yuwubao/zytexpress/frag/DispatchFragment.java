package com.yuwubao.zytexpress.frag;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.DispatchBean;
import com.yuwubao.zytexpress.bean.RequestModel;
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

public class DispatchFragment extends BaseFragement {

    @BindView(R.id.dispatchList)
    RecyclerView dispatchList;
    CommonAdapter adapter;
    List<DispatchBean.ResultBean.ContentBean> contentBeen;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_dispatch;
    }

    @Override
    protected void init() {
        setComAdapter();
        initData();
    }

    private void initData() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.DISPATCH)//
                .addParams(AppConfig.USER_ID, AppConfig.userId)//
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

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroyView();
    }

}
