package com.yuwubao.zytexpress.activity;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.StatusBean;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/22
 * e-mail: phlxplus@163.com
 * description: 拒收页面
 */

public class RejectionActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.remake)
    EditText remake;
    String orderCode;
    String et_remake;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_rejection;
    }

    @Override
    protected void init() {
        setHeader();
        orderCode = getIntent().getExtras().getString(AppConfig.ORDER_CODE);
        order.setText("运单号：" + orderCode);
    }

    private void setHeader() {
        title.setTitle(getString(R.string.rejection));
    }

    @OnClick(R.id.submit)
    public void onClick() {
        et_remake = remake.getText().toString().trim();
        if (TextUtils.isEmpty(et_remake)) {
            return;
        }
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.ORDER_REJECTION)//
                .addParams(AppConfig.USER_ID,AppConfig.userId)//
                .addParams("no", orderCode)//
                .addParams("remark", et_remake)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, "操作成功");
                        finish();
                    }
                });
    }
}
