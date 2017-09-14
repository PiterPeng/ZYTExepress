package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.ProjectListBack;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.frag.HomeFragment;
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

/**
 * Created by Peng on 2017/6/22
 * e-mail: phlxplus@163.com
 * description: 选择对应的项目进入对应的扫描
 */

public class ProjectSelectActivity extends BaseActivity {

    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.project_list)
    RecyclerView projectList;

    CommonAdapter adapter;
    List<ProjectListBack.ResultBean> resultBeanList;

    int jumpType;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_select_pm;
    }

    @Override
    protected void init() {
        resolveIntent();
        setTitle();
        initData();
        setRecycler();
    }

    private void resolveIntent() {
        jumpType = getIntent().getExtras().getInt("jumpType");
    }

    private void initData() {
        resultBeanList = new ArrayList<>();
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            OkHttpUtils.get()//
                    .url(Urls.FINDAUTHEDCUSTOMERLIST)//
                    .tag(this)//
                    .addParams("currentUserId", user.getId() + "")//
                    .build()//
                    .execute(new AppGsonCallback<ProjectListBack>(new RequestModel(c)) {
                        @Override
                        public void onResponseOK(ProjectListBack response, int id) {
                            super.onResponseOK(response, id);
                            List<ProjectListBack.ResultBean> temp = response.getResult();
                            resultBeanList.clear();
                            resultBeanList.addAll(temp);
                            adapter.notifyDataSetChanged();
                        }
                    });
        }

    }

    private void setRecycler() {
        adapter = new CommonAdapter<ProjectListBack.ResultBean>(c, R.layout.item_project_list, resultBeanList) {
            @Override
            protected void convert(ViewHolder holder, ProjectListBack.ResultBean bean, int position) {
                holder.setText(R.id.customerName, bean.getCustomerName());
                holder.setText(R.id.projectName, bean.getProjectName());
            }
        };
        projectList.setLayoutManager(new LinearLayoutManager(c));
        projectList.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        projectList.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (!resultBeanList.isEmpty()) {
                    if (jumpType == HomeFragment.JUMP_TYPE_PICK_UP) {
                        Intent intent = new Intent(c, PickUpActivity.class);
                        intent.putExtra("scanMode", resultBeanList.get(position).getScanMode());
                        intent.putExtra("id", resultBeanList.get(position).getId());
                        intent.putExtra("customerName", resultBeanList.get(position).getCustomerName());
                        intent.putExtra("projectName", resultBeanList.get(position).getProjectName());
                        startActivity(intent);
                    } else if (jumpType == HomeFragment.JUMP_TYPE_CAR_LIST) {
                        Intent intent = new Intent(c, IntoCarListActivity.class);
                        intent.putExtra("id", resultBeanList.get(position).getId());
                        intent.putExtra("customerName", resultBeanList.get(position).getCustomerName());
                        intent.putExtra("projectName", resultBeanList.get(position).getProjectName());
                        startActivity(intent);
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void setTitle() {
        title.setTitle("项目选择");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
