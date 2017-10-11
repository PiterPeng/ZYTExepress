package com.yuwubao.zytexpress.frag;

import android.content.Intent;
import android.view.View;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.PDAScanActivity;
import com.yuwubao.zytexpress.activity.ProjectSelectActivity;

import butterknife.OnClick;

/**
 * Created by Peng on 2017/4/24
 * e-mail: phlxplus@163.com
 * description: 查询
 */

public class QueryFragment extends BaseFragement {

    public static final int JUMP_TYPE_TIE_SCAN = 0x03;//贴标扫描
    public static final int JUMP_TYPE_TIE_CHECK = 0x04;//贴标复核

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_stick_scan;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.query, R.id.check, R.id.scan})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.scan://查件扫描
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_SCAN);
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                break;
            case R.id.query://贴标扫描
                Intent intent1 = new Intent();
                intent1.putExtra("jumpType", JUMP_TYPE_TIE_SCAN);
                JumpToActivity(ProjectSelectActivity.class, intent1);
//                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_QUERY);
                break;
            case R.id.check://复核
                Intent intent2 = new Intent();
                intent2.putExtra("jumpType", JUMP_TYPE_TIE_CHECK);
                JumpToActivity(ProjectSelectActivity.class, intent2);
//                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_CHECK);
                break;
        }

    }
}
