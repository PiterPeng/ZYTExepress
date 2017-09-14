package com.yuwubao.zytexpress.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.frag.AppointScanFragment;
import com.yuwubao.zytexpress.frag.MangScanFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/16
 * e-mail: phlxplus@163.com
 * description: 待提货
 */

public class PickUpActivity extends BaseActivity {
    public static final String ARG = "arg";
    @BindView(R.id.rb_left)
    RadioButton rbLeft;
    @BindView(R.id.rb_right)
    RadioButton rbRight;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.showPM)
    TextView showPM;
    MangScanFragment mangScanFragment;
    AppointScanFragment appointScanFragment;
    int scanMode, id;
    String customerName, projectName;


    @Override
    protected int getContentResourseId() {
        return R.layout.activity_pick_up;
    }

    @Override
    protected void init() {
        resolveIntent();
        showDialog();
        setFrag();
    }

    private void showDialog() {
        new AlertDialog.Builder(c).setTitle("提示").setMessage("当前客户名称：" + customerName + "\n" + "当前项目名称：" +
                projectName).setPositiveButton("我知道了", null).show();
        showPM.setText(customerName + projectName);
        showPM.setVisibility(View.VISIBLE);
    }

    private void resolveIntent() {
        scanMode = getIntent().getExtras().getInt("scanMode");
        customerName = getIntent().getExtras().getString("customerName");
        projectName = getIntent().getExtras().getString("projectName");
        id = getIntent().getExtras().getInt("id");
    }

    private void setFrag() {
        mangScanFragment = new MangScanFragment();
        appointScanFragment = new AppointScanFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG, String.valueOf(id));

        if (scanMode == 119) {
            rbRight.setClickable(false);
            rbLeft.setChecked(true);
            replaceFragment(R.id.replace, mangScanFragment);
            mangScanFragment.setArguments(bundle);
        } else {
            rbLeft.setClickable(false);
            rbRight.setChecked(true);
            replaceFragment(R.id.replace, appointScanFragment);
            appointScanFragment.setArguments(bundle);
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
