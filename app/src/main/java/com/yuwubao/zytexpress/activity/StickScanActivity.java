package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.view.View;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.widget.HeaderBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/23
 * e-mail: phlxplus@163.com
 * description: 查询 复核
 */

public class StickScanActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_stick_scan;
    }

    @Override
    protected void init() {
        setHeader();
    }

    private void setHeader() {
        title.setTitle(getString(R.string.query));
    }

    @OnClick({R.id.query, R.id.check, R.id.scan})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.scan:
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_SCAN);
                break;
            case R.id.query:
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_QUERY);
                break;
            case R.id.check:
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_CHECK);
                break;
        }
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
        if (AppConfig.isPDA) {
            JumpToActivity(PDAScanActivity.class, intent);
        } else {
            JumpToActivity(CaptureActivity.class, intent);
        }
    }

}
