package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.InTableBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.frag.WeiDFragment;
import com.yuwubao.zytexpress.frag.YiDFragment;
import com.yuwubao.zytexpress.helper.DialogHelper;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.listener.OnItemClickListener;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/9/21
 * e-mail: phlxplus@163.com
 * description: 新版入库界面
 */

public class NewInSaveActivity extends BaseActivity {
    @BindView(R.id.in_replace)
    FrameLayout inReplace;
    @BindView(R.id.rb_left)
    RadioButton rbLeft;
    @BindView(R.id.rb_right)
    RadioButton rbRight;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.tableName)
    TextView tableName;
    @BindView(R.id.projectName)
    TextView projectName;
    @BindView(R.id.in_or_out)
    TextView in_or_out;
    @BindView(R.id.text_show)
    TextView textShow;

    YiDFragment yiDFragment;
    WeiDFragment weiDFragment;
    int currentId;
    String userId;

    List<InTableBean.ResultBean> inTableBeenList, inTableBeenList1;
    int type;//０出库　１入库


    @Override
    protected int getContentResourseId() {
        return R.layout.activity_new_insave;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        resolveIntent();
        setFrag();
        getList();
        setRadioGroup();
    }

    private void resolveIntent() {
        type = getIntent().getExtras().getInt("TYPE_IN_OUT");
        in_or_out.setText(type == 0 ? "出库" : "入库");
        textShow.setText(type == 0 ? "出库单号：" : "入库单号：");
    }

    private void setFrag() {
        yiDFragment = new YiDFragment();
        weiDFragment = new WeiDFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        yiDFragment.setArguments(bundle);
        weiDFragment.setArguments(bundle);
        addFrag();
        hideFrag();
        showFrag(yiDFragment);
    }

    /**
     * 获取数据并设置表单
     */
    private void getList() {
        inTableBeenList = new ArrayList<>();
        inTableBeenList1 = new ArrayList<>();
        OkHttpUtils//
                .get()//
                .url(Urls.GETINOUTLIST)//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("type", type + "")//０出库　１入库
                .build()//
                .execute(new AppGsonCallback<InTableBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(InTableBean response, int id) {
                        super.onResponseOK(response, id);
                        List<InTableBean.ResultBean> temp = response.getResult();
                        if (temp != null) {
                            if (temp.size() == 0) {
                                UIHelper.showMessage(c, "暂无单号可选择");
                                tableName.setText("暂无单号可选择");
                            } else {
                                tableName.setText(type == 0 ? "请选择出库单" : "请选择入库单");
                            }
                            inTableBeenList.clear();
                            inTableBeenList.addAll(temp);
                        }
                    }
                });
    }

    private void getProjectList(int id) {
        OkHttpUtils//
                .get()//
                .url(Urls.GETINOUTPROJECT)//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("id", id + "")//０出库　１入库
                .build()//
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
                            inTableBeenList1.clear();
                            inTableBeenList1.addAll(temp);
                        }
                    }
                });

    }

    private void addFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.in_replace, yiDFragment);
        ft.add(R.id.in_replace, weiDFragment);
        ft.commit();
    }

    private void hideFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(yiDFragment);
        ft.hide(weiDFragment);
        ft.commit();
    }

    private void showFrag(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(yiDFragment);
        ft.hide(weiDFragment);
        ft.show(fragment);
        ft.commit();
    }

    public void onBackClick(View view) {
        finish();
    }

    private void setRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_left:
                        if (currentId == 1) {
                            return;
                        }
                        showFrag(yiDFragment);
                        currentId = 1;
                        break;
                    case R.id.rb_right:
                        if (currentId == -1) {
                            return;
                        }
                        showFrag(weiDFragment);
                        currentId = -1;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.ll_tableName, R.id.ll_projectName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tableName://出库单
                DialogHelper.showPopupwindow(c, inTableBeenList, tableName, new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, RecyclerView.ViewHolder holder, int position) {
                        tableName.setText(inTableBeenList.get(position).getName());
                        int id = inTableBeenList.get(position).getId();
                        getProjectList(id);
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        intent.setAction("YiDianId");
                        sendBroadcast(intent);
                    }
                });
                break;
            case R.id.ll_projectName://项目
                DialogHelper.showPopupwindow(c, inTableBeenList1, projectName, new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, RecyclerView.ViewHolder holder, int position) {
                        projectName.setText(inTableBeenList1.get(position).getName());
                        int scanId = inTableBeenList1.get(position).getId();
                        yiDFragment.getScanMode(scanId);
                    }
                });
                break;
        }
    }
}
