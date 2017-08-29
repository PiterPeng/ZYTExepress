package com.yuwubao.zytexpress.frag;

import android.content.Intent;
import android.view.View;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.ProjectSelectActivity;

import butterknife.OnClick;

/**
 * Created by Peng on 2017/8/21
 * e-mail: phlxplus@163.com
 * description: 正向接单
 */

public class ZhengXFragment extends BaseFragement {

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_zheng_li;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.tihuo, R.id.zhuangche, R.id.lanjian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tihuo:
                jump(HomeFragment.JUMP_TYPE_PICK_UP);
                break;
            case R.id.zhuangche:
                jump(HomeFragment.JUMP_TYPE_CAR_LIST);
                break;
            case R.id.lanjian:
                break;
        }
    }

    void jump(int type) {
        Intent i = new Intent(c, ProjectSelectActivity.class);
        i.putExtra("jumpType", type);
        startActivity(i);
    }
}
