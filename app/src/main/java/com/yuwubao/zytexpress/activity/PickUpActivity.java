package com.yuwubao.zytexpress.activity;

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
    @BindView(R.id.rb_left)
    RadioButton rbLeft;
    @BindView(R.id.rb_right)
    RadioButton rbRight;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    MangScanFragment mangScanFragment;
    AppointScanFragment appointScanFragment;
    int currentId;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_pick_up;
    }

    @Override
    protected void init() {
        setFrag();
        setRadioGroup();
    }

    private void setFrag() {
        mangScanFragment = new MangScanFragment();
        appointScanFragment = new AppointScanFragment();
        replaceFragment(R.id.replace, mangScanFragment);
    }

    public void onBackClick(View view) {
        finish();
    }

    private void setRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_left:
                        if (currentId == 1) {
                            return;
                        }
                        replaceFragment(R.id.replace, mangScanFragment);
                        currentId = 1;
                        break;
                    case R.id.rb_right:
                        if (currentId == -1) {
                            return;
                        }
                        replaceFragment(R.id.replace, appointScanFragment);
                        currentId = -1;
                        break;
                }
            }
        });
    }
}
