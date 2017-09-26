package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.view.View;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.widget.HeaderBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/8/17
 * e-mail: phlxplus@163.com
 * description: 订单信息管理（OMS)
 */

public class OMSActivity extends BaseActivity {

    @BindView(R.id.title)
    HeaderBar title;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_oms;
    }

    @Override
    protected void init() {
        setTitle();
    }

    private void setTitle() {
        title.setTitle("订单信息管理（OMS)");
    }

    @OnClick({R.id.tihuo, R.id.paijian, R.id.daohuo})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tihuo:
                intent.putExtra("jump_type", 0);
                JumpToActivity(DisPatchAndCountActivity.class, intent);
                break;
            case R.id.daohuo:
                intent.putExtra("jump_type", 2);
                JumpToActivity(DisPatchAndCountActivity.class, intent);
                break;
            case R.id.paijian:
                JumpToActivity(SendPiecesActivity.class, intent);
                break;
        }
    }
}
