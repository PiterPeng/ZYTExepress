package com.yuwubao.zytexpress.frag;

import android.content.Intent;
import android.view.View;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.PDAScanActivity;
import com.yuwubao.zytexpress.activity.TransferScanActivity;

import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/8
 * e-mail: phlxplus@163.com
 * description: 签收
 */

public class IncludeFragment extends BaseFragement {
    @Override
    protected int getContentResourseId() {
        return R.layout.frag_include;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.sign, R.id.rejection, R.id.scan})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.scan://中转扫描
                JumpToActivity(TransferScanActivity.class);
                break;
            case R.id.sign://签收
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SIGN);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                break;
            case R.id.rejection://拒收
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_REJECTION);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                break;
        }
    }
}
