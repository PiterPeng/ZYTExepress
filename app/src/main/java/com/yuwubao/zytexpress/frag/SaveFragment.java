package com.yuwubao.zytexpress.frag;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.PDAScanActivity;

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
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
        switch (view.getId()) {
            case R.id.in:
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN);
                showDialog(intent);
                break;
            case R.id.out:
                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_OUT);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                break;
        }
    }

    private void showDialog(final Intent intent) {
        View layoutDialog = LayoutInflater.from(c).inflate(R.layout.dialog, null);
        TextView theory = (TextView) layoutDialog.findViewById(R.id.theory_in);
        TextView fact = (TextView) layoutDialog.findViewById(R.id.fact_in);
        final AlertDialog dialog = new AlertDialog.Builder(c).setTitle("选择入库方式").setView(layoutDialog).show();
        theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(AppConfig.IN_TYPE, AppConfig.IN_TYPE_THOERY);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                dialog.dismiss();
            }
        });
        fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(AppConfig.IN_TYPE, AppConfig.IN_TYPE_FACT);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                dialog.dismiss();
            }
        });
    }
}
