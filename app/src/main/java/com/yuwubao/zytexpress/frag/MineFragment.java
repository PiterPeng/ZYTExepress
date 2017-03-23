package com.yuwubao.zytexpress.frag;

import android.widget.ImageView;
import android.widget.TextView;

import com.yuwubao.zytexpress.R;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/8
 * e-mail: phlxplus@163.com
 * description: 我的
 */

public class MineFragment extends BaseFragement {
    @BindView(R.id.headerImg)
    ImageView headerImg;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.companyName)
    TextView companyName;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_mine;
    }

    @Override
    protected void init() {

    }
}
