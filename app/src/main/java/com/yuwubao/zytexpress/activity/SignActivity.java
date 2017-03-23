package com.yuwubao.zytexpress.activity;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.adapter.ShowImgAdapter;
import com.yuwubao.zytexpress.bean.CodeBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.StatusBean;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.utils.StringUtils;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by Peng on 2017/3/22
 * e-mail: phlxplus@163.com
 * description: 签收页面
 */

public class SignActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.phone_No)
    EditText phoneNo;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.image)
    RecyclerView image;
    @BindView(R.id.getCode)
    TextView getCode;
    @BindView(R.id.sign)
    TextView sign;

    String mobileNumber, codeNumber, orderIntent;
    int mobileCode;

    ShowImgAdapter showImgAdapter;
    List<LocalMedia> localMediaList;
    Map<String, File> files;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void init() {
        setHeader();
        initDatas();
        setEditextWatch();
        setRecAdapter();
    }

    private void setEditextWatch() {
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.equals(s, String.valueOf(mobileCode)) && count == 6) {
                    code.setBackgroundResource(R.drawable.shape_black_broder);
                    sign.setClickable(true);
                } else {
                    code.setBackgroundResource(R.drawable.shape_red_broder);
                    sign.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setRecAdapter() {
        showImgAdapter = new ShowImgAdapter(c, localMediaList, true);
        image.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false));
        image.setAdapter(showImgAdapter);
        showImgAdapter.setOnItemAddClickListener(new ShowImgAdapter.OnItemAddClickListener() {
            @Override
            public void onItemAddClick(int pos) {
                FunctionConfig config = new FunctionConfig();
                config.setImageSpanCount(4);
                config.setType(LocalMediaLoader.TYPE_IMAGE);
                config.setSelectMode(FunctionConfig.MODE_MULTIPLE);
                config.setMaxSelectNum(9);
                config.setShowCamera(true);
                config.setEnablePreview(true);
                config.setEnableCrop(false);
                config.setSelectMedia(localMediaList);
                config.setCompress(true);
                config.setCompressFlag(1);
                config.setCompressQuality(100);
                config.setCheckedBoxDrawable(R.drawable.selector_choose);
                config.setThemeStyle(ContextCompat.getColor(c, R.color.colorPrimary));
                // 先初始化参数配置，在启动相册
                PictureConfig.init(config);
                PictureConfig.getPictureConfig().openPhoto(c, resultCallback);
            }
        });

        showImgAdapter.setOnImgItemClickListener(new ShowImgAdapter.OnImgItemClickListener() {
            @Override
            public void onImgClick(int pos) {

            }
        });

        showImgAdapter.setOnItemImgDeletListener(new ShowImgAdapter.OnItemImgDeletListener() {
            @Override
            public void onDeleteImg(int pos) {
                localMediaList.remove(pos);
                showImgAdapter.notifyItemRemoved(pos);
                showImgAdapter.notifyItemRangeChanged(pos, localMediaList.size());
            }
        });
    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig
            .OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            if (resultList == null) {
                resultList = new ArrayList<>();
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    File file = new File(resultList.get(i).getPath());
                    files.put(file.getName(), file);
                    Log.d("path", file.getName() + "," + resultList.get(i).getPath());
                }
            }
            showImgAdapter.update(resultList, ShowImgAdapter.UPDATE_REPLACE);
        }
    };

    private void initDatas() {
        orderIntent = getIntent().getExtras().getString(AppConfig.ORDER_CODE);
        if (!TextUtils.isEmpty(orderIntent)) {
            order.setText("运单号：" + orderIntent);
        }
        handler = new Handler();
        myRunnable = new MyRunnable(new WeakReference<>(this));
        localMediaList = new ArrayList<>();
        files = new HashMap<>();
    }

    private void getCodeFromPhone() {
        mobileNumber = phoneNo.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumber)) {
            return;
        }
        if (!StringUtils.isMobileNum(mobileNumber)) {
            UIHelper.showMessage(c, "请输入正确的手机号");
            return;
        }

        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.PHONE_VERIFICATION_CODE)//
                .addParams("mobile", mobileNumber)//
                .build()//
                .execute(new AppGsonCallback<CodeBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(CodeBean response, int id) {
                        super.onResponseOK(response, id);
                        mobileCode = response.getResult();
                        handler.postDelayed(myRunnable, 1000);
                    }
                });
    }

    private void signOk() {
        codeNumber = code.getText().toString().trim();
        mobileNumber = phoneNo.getText().toString().trim();
        if (TextUtils.isEmpty(codeNumber) || TextUtils.isEmpty(mobileNumber)) {
            return;
        }
        if (files.isEmpty()) {
            UIHelper.showMessage(c, "请至少选择一张照片");
            return;
        }

        //
        post()//
                .tag(this)//
                .url(Urls.ORDER_SIGN)//
                .addParams("no", orderIntent)//
                .addParams("pic", "")//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, "签收成功");
                        finish();
                    }
                });


    }

    private MyRunnable myRunnable;
    private Handler handler;

    int currentTime = 60;
    private static final int ISTIMEOUT = 0;

    private static class MyRunnable implements Runnable {

        WeakReference<SignActivity> reference;
        SignActivity signActivity;

        public MyRunnable(WeakReference<SignActivity> reference) {
            this.reference = reference;
        }

        @Override
        public void run() {
            signActivity = reference.get();
            signActivity.currentTime--;
            signActivity.getCode.setText(signActivity.currentTime + "s后重新获取");
            if (signActivity.currentTime == signActivity.ISTIMEOUT) {
                signActivity.handler.removeCallbacks(this);
                signActivity.getCode.setText("重新获取验证码");
            } else {
                signActivity.handler.postDelayed(this, 1000);
            }

        }
    }

    private void setHeader() {
        title.setTitle(getString(R.string.sign));
    }

    @OnClick({R.id.getCode, R.id.sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                getCodeFromPhone();
                break;
            case R.id.sign:
                signOk();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
