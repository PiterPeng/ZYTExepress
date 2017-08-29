package com.yuwubao.zytexpress.frag;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuwubao.zytexpress.R;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/23
 * e-mail: phlxplus@163.com
 * description: 查询&统计
 */

public class StickScanFragment extends BaseFragement {
    @BindView(R.id.rb_left)
    RadioButton rbLeft;
    @BindView(R.id.rb_right)
    RadioButton rbRight;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.back)
    ImageView back;
    QueryFragment queryFragment;
    Count2Fragment count2Fragment;
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
        rbLeft.setText(getString(R.string.query));
        rbRight.setText(getString(R.string.count));
        back.setVisibility(View.GONE);
        queryFragment = new QueryFragment();
        count2Fragment = new Count2Fragment();
        replaceFragment(R.id.replace, queryFragment);
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
                        replaceFragment(R.id.replace, queryFragment);
                        currentId = 1;
                        break;
                    case R.id.rb_right:
                        if (currentId == -1) {
                            return;
                        }
                        replaceFragment(R.id.replace, count2Fragment);
                        currentId = -1;
                        break;
                }
            }
        });
    }
}
