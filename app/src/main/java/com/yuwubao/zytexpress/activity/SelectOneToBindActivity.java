package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.NewStatusBean;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/4/8
 * e-mail: phlxplus@163.com
 * description: 这里需要处理一个69码有多个商品的情况，判断返回的List集合长度，展示所有的商品，选择一个商品与69码绑定
 */

public class SelectOneToBindActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.display)
    RecyclerView display;
    CommonAdapter adapter;
    List<NewStatusBean.ResultBean> statusBeen;
    String code69;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_display;
    }

    @Override
    protected void init() {
        setHeader();
        initData();
        initAdapter();
    }

    private void initData() {
        statusBeen = (List<NewStatusBean.ResultBean>) getIntent().getSerializableExtra("bean");
        code69 = getIntent().getExtras().getString(AppConfig.CODE_69);
    }

    private void initAdapter() {
        adapter = new CommonAdapter<NewStatusBean.ResultBean>(c, R.layout.item_bind, statusBeen) {
            @Override
            protected void convert(ViewHolder holder, NewStatusBean.ResultBean newStatusBean, int position) {
                holder.setText(R.id.id, code69);
                holder.setText(R.id.number, newStatusBean.getCode());
                holder.setText(R.id.name, newStatusBean.getName());
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                int mId = statusBeen.get(position).getId();
                Intent intent = new Intent();
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                intent.putExtra(AppConfig.CODE_69, String.valueOf(mId));
                if (AppConfig.isPDA) JumpToActivity(PDAScanActivity.class, intent);
                else JumpToActivity(CaptureActivity.class, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        display.setLayoutManager(new LinearLayoutManager(c));
        display.setAdapter(adapter);
    }

    private void setHeader() {
        title.setTitle("商品绑定");
    }

}
