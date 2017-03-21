package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.TextView;

import com.yuwubao.zytexpress.R;

import butterknife.BindView;


/**
 * 扫描的结果
 *
 * @author mhdt
 * @version 1.0
 * @created 2016-8-1 下午4:40:32
 * @update
 */
public class ScannerResultActivity extends BaseActivity {
    @BindView(R.id.sr)
    TextView showScannerDetail;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_scanner;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String res = intent.getStringExtra("codedContent");
        Bitmap bit = intent.getParcelableExtra("codedBitmap");
        if (!TextUtils.isEmpty(res)) {
            showScannerDetail.setText("扫描结果:\r\n" + res);
        }
    }
}
