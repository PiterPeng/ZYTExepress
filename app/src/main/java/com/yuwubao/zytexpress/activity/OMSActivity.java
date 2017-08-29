package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.view.View;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.helper.UIHelper;
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

    void jump(int type) {
        Intent i = new Intent(c, ProjectSelectActivity.class);
        i.putExtra("jumpType", type);
        startActivity(i);
    }

    private void setTitle() {
        title.setTitle("订单信息管理（OMS)");
    }

    @OnClick({R.id.tihuo, R.id.paijian, R.id.daohuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tihuo:
                JumpToActivity(DisPatchAndCountActivity.class);
                break;
            case R.id.paijian:
                JumpToActivity(DisPatchAndCountActivity.class);
                break;
            case R.id.daohuo:
                UIHelper.showMessage(c,"敬请期待！");
                break;
        }
    }
}
