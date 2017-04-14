package com.yuwubao.zytexpress.activity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.Count2Bean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.frag.CountFragment;
import com.yuwubao.zytexpress.frag.DispatchFragment;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/31
 * e-mail: phlxplus@163.com
 * description: 调度 & 统计
 */

public class DisPatchAndCountActivity extends BaseActivity {
    @BindView(R.id.rb_left)
    RadioButton rbLeft;
    @BindView(R.id.rb_right)
    RadioButton rbRight;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.total_quantity)
    TextView totalQuantity;
    @BindView(R.id.total_volume)
    TextView totalVolume;
    @BindView(R.id.total_weight)
    TextView totalWeight;

    DispatchFragment dispatchFragment;
    CountFragment countFragment;
    int currentId;
    List<Count2Bean.ResultBean> countBeen;


    @Override
    protected int getContentResourseId() {
        return R.layout.activity_dispatch_count;
    }

    @Override
    protected void init() {
        setCount();
        setFrag();
        setRadioGroup();
    }

    private void setCount() {
        countBeen = new ArrayList<>();
        OkHttpUtils.get()//
                .tag(this)//
                .url(Urls.DISPATCH_COUNT)//
                .addParams(AppConfig.USER_ID, AppConfig.userId)//
                .build()//
                .execute(new AppGsonCallback<Count2Bean>(new RequestModel(c).setShowProgress(false)) {
                    @Override
                    public void onResponseOK(Count2Bean response, int id) {
                        super.onResponseOK(response, id);
                        List<Count2Bean.ResultBean> temp = response.getResult();
                        countBeen.clear();
                        countBeen.addAll(temp);
                        if (!countBeen.isEmpty()) {
                            Count2Bean.ResultBean bean = countBeen.get(0);
                            totalQuantity.setText("总数量\n（" + bean.getQuantity() + "）");
                            totalWeight.setText("总重量\n（" + String.valueOf(bean.getWeight()) + "kg）");
                            totalVolume.setText("总体积\n（" + String.valueOf(bean.getVolume()) + "m³）");
                        }
                    }
                });
    }

    private void setFrag() {
        dispatchFragment = new DispatchFragment();
        countFragment = new CountFragment();
        replaceFragment(R.id.replace, dispatchFragment);
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
                        replaceFragment(R.id.replace, dispatchFragment);
                        currentId = 1;
                        break;
                    case R.id.rb_right:
                        if (currentId == -1) {
                            return;
                        }
                        replaceFragment(R.id.replace, countFragment);
                        currentId = -1;
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
