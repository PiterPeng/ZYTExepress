package com.yuwubao.zytexpress.activity;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.frag.NiXFragment;
import com.yuwubao.zytexpress.frag.ZhengXFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/8/21
 * e-mail: phlxplus@163.com
 * description: 正向/逆向 接单
 */

public class TMSActivity extends BaseActivity {
    @BindView(R.id.rb_left)
    RadioButton rbLeft;
    @BindView(R.id.rb_right)
    RadioButton rbRight;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.back)
    ImageView back;

    ZhengXFragment zhengXFragment;
    NiXFragment niXFragment;
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
        rbLeft.setText("正向");
        rbRight.setText("逆向");
        zhengXFragment = new ZhengXFragment();
        niXFragment = new NiXFragment();
        replaceFragment(R.id.replace, zhengXFragment);

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
                        replaceFragment(R.id.replace, zhengXFragment);
                        currentId = 1;
                        break;
                    case R.id.rb_right:
                        if (currentId == -1) {
                            return;
                        }
                        replaceFragment(R.id.replace, niXFragment);
                        currentId = -1;
                        break;
                }
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
