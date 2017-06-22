package com.yuwubao.zytexpress.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.frag.AppointScanFragment;
import com.yuwubao.zytexpress.frag.MangScanFragment;

import butterknife.BindView;

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
    MangScanFragment mangScanFragment;
    AppointScanFragment appointScanFragment;
    int currentId, scanMode, id;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_pick_up;
    }

    @Override
    protected void init() {
        resolveIntent();
        setFrag();
        setRadioGroup();
    }

    private void resolveIntent() {
        scanMode = getIntent().getExtras().getInt("scanMode");
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

    public void onBackClick(View view) {
        finish();
    }

    private void setRadioGroup() {

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_left:
//                        if (currentId == 1) {
//                            return;
//                        }
//                        replaceFragment(R.id.replace, mangScanFragment);
//                        currentId = 1;
//                        break;
//                    case R.id.rb_right:
//                        if (currentId == -1) {
//                            return;
//                        }
//                        replaceFragment(R.id.replace, appointScanFragment);
//                        currentId = -1;
//                        break;
//                }
//            }
//        });
    }
}
