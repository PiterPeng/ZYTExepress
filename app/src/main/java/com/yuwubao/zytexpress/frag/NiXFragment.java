package com.yuwubao.zytexpress.frag;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.NiXActivity;
import com.yuwubao.zytexpress.activity.PDAScanActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/8/21
 * e-mail: phlxplus@163.com
 * description: 逆向接单
 */

public class NiXFragment extends BaseFragement {


    @BindView(R.id.zhuangche)
    ImageView zhuangche;
    @BindView(R.id.lanjian)
    ImageView lanjian;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_zheng_li;
    }

    @Override
    protected void init() {
        lanjian.setVisibility(View.GONE);
        zhuangche.setImageResource(R.drawable.received);
    }

    @OnClick({R.id.tihuo, R.id.zhuangche})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tihuo://逆向提货
                JumpToActivity(NiXActivity.class);
                break;
            case R.id.zhuangche://逆向签收
                Intent intent = new Intent();
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SIGN);
                intent.putExtra(AppConfig.IS_NIXIANG, true);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                break;
        }
    }
}
