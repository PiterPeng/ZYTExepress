package com.yuwubao.zytexpress.frag;

import android.content.Intent;
import android.view.View;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;

import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/8
 * e-mail: phlxplus@163.com
 * description: 仓库
 */

public class SaveFragment extends BaseFragement {
    @Override
    protected int getContentResourseId() {
        return R.layout.frag_save;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.in, R.id.out})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.in:
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN);
                break;
            case R.id.out:
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_OUT);
                break;
        }
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
        JumpToActivity(CaptureActivity.class, intent);
    }
}
