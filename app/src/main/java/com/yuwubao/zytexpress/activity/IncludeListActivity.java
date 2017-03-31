package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.IncludeListBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/29
 * e-mail: phlxplus@163.com
 * description: 未备案列表
 */

public class IncludeListActivity extends BaseActivity {

    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CommonAdapter adapter;
    List<IncludeListBean.ResultBean> resultBeen;
    String code_69;
    @BindView(R.id.et_name)
    EditText etName;
    String name = "";

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_include_list;
    }

    @Override
    protected void init() {
        setHeader();
        initIntent();
        setRecyclerView();
        iniData();
    }

    private void initIntent() {
        code_69 = getIntent().getStringExtra("code69");
    }

    private void iniData() {
        OkHttpUtils
                .get()//
                .tag(this)//
                .url(Urls.INCLUDE_LIST)//
                .addParams(AppConfig.USER_ID, AppConfig.userId)//
                .addParams("name", name)//
                .build()//
                .execute(new AppGsonCallback<IncludeListBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(IncludeListBean response, int id) {
                        super.onResponseOK(response, id);
                        List<IncludeListBean.ResultBean> temp = response.getResult();
                        resultBeen.clear();
                        resultBeen.addAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setRecyclerView() {
        resultBeen = new ArrayList<>();
        adapter = new CommonAdapter<IncludeListBean.ResultBean>(c, R.layout.item_include_list, resultBeen) {
            @Override
            protected void convert(ViewHolder holder, IncludeListBean.ResultBean o, int position) {
                holder.setText(R.id.name, o.getName());
                holder.setText(R.id.code, o.getCode());
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                UIHelper.showMyCustomDialog(c, "确定将69码与此商品绑定？", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("code69", code_69);
                        intent.putExtra("id", resultBeen.get(position).getId());
                        JumpToActivity(IncludeActivity.class, intent);
                        finish();
                    }
                }, null);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setHeader() {
        title.setTitle("未备案列表");
    }

    @OnClick(R.id.search)
    public void onClick() {
        name = etName.getText().toString().trim();
        iniData();
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
