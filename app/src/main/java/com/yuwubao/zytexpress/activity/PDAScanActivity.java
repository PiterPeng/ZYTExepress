package com.yuwubao.zytexpress.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.QueryBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.StatusBean;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;

import static com.yuwubao.zytexpress.AppConfig.SHOW_TEXT_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_CAR;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_ORDER;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_SN;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_STORAGE;

/**
 * Created by Peng on 2017/3/24
 * e-mail: phlxplus@163.com
 * description: PDA扫描界面
 */

public class PDAScanActivity extends BaseActivity {

    @BindView(R.id.scan_code_69)
    TextView scan_code_69;
    @BindView(R.id.scan_code_sn)
    TextView scan_code_sn;
    @BindView(R.id.scan)
    TextView scan;
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.scan_type)
    TextView scanType;


    int orderId;//订单id
    int scanMode;//扫描类型
    String code69 = "";//69码，结果
    String code69Intent = "";//69码,接收
    String codeSN = "";//SN码
    String codeSNIntent = "";//SN码,接收
    String codeCar = "";//车号
    String codeOrder = "";//运单号
    String storageNo = "";//储位号
    int currentType;//当前的扫描类型69 or SN
    int enterType;//进入类型 盲扫 or 制定扫描

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_pda_scan;
    }

    @Override
    protected void init() {
        setHeader();
        resolveIntent();
        updateUI();
        setBroadcast();
        setOnTouchListener();
    }

    private void setOnTouchListener() {
        scan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onTouchButton();
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent
                        .ACTION_CANCEL) {
                    onReleaseButton();
                }
                return true;
            }
        });
    }

    private void setBroadcast() {
        IntentFilter intentFilter = new IntentFilter(AppConfig.SCN_CUST_ACTION_SCODE);
        registerReceiver(mSamDataReceiver, intentFilter);

    }

    private void resolveIntent() {
        orderId = getIntent().getExtras().getInt(AppConfig.ORDER_ID);
        scanMode = getIntent().getExtras().getInt(AppConfig.SCAN_MODE);
        currentType = getIntent().getExtras().getInt(AppConfig.CURRENT_SCAN_TYPE);
        enterType = getIntent().getExtras().getInt(AppConfig.ENTER_TYPE);
        code69Intent = getIntent().getExtras().getString(AppConfig.CODE_69);
        codeSNIntent = getIntent().getExtras().getString(AppConfig.CODE_SN);
    }

    private void setHeader() {
        title.setTitle(getString(R.string.PDA));
    }

    private void onTouchButton() {
        Intent scannerIntent = new Intent(AppConfig.SCN_CUST_ACTION_START);
        sendBroadcast(scannerIntent);
    }

    private void onReleaseButton() {
        Intent scannerIntent = new Intent(AppConfig.SCN_CUST_ACTION_CANCEL);
        sendBroadcast(scannerIntent);
    }

    private BroadcastReceiver mSamDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals(AppConfig.SCN_CUST_ACTION_SCODE)) {
                    //获取条码
                    switch (currentType) {
                        case AppConfig.SCAN_TYPE_CODE_69:
                            code69 = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            scan_code_69.setText("69码：" + code69);
                            check69IsInclude();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SN:
                            codeSN = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            scan_code_69.setText("69码：" + code69Intent);
                            scan_code_sn.setText("SN码：" + codeSN);
                            scan_code_sn.setVisibility(View.VISIBLE);
                            switch (enterType) {
                                case AppConfig.ENTER_TYPE_MANGSAO:
                                    blindSnForMangSao();
                                    break;
                                case AppConfig.ENTER_TYPE_ZHISAO:
                                    blindSnForZhiSao();
                                    break;
                                case AppConfig.ENTER_TYPE_IN:
                                    toScanStorageNo();
                                    break;
                                case AppConfig.ENTER_TYPE_OUT:
                                    outStorage();
                                    break;
                                case AppConfig.ENTER_TYPE_QUERY:
                                    query();
                                    break;
                                case AppConfig.ENTER_TYPE_CHECK:
                                    check();
                                    break;
                            }
                            break;
                        case AppConfig.SCAN_TYPE_CODE_CAR:
                            codeCar = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            scan_code_69.setText("SN码：" + codeSNIntent);
                            scan_code_sn.setText("车号：" + codeCar);
                            scan_code_sn.setVisibility(View.VISIBLE);
                            switch (enterType) {
                                case AppConfig.ENTER_TYPE_MANGSAO:
                                    intoCarForMangsao();
                                    break;
                                case AppConfig.ENTER_TYPE_ZHISAO:
                                    intoCarForZhiSao();
                                    break;
                            }
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SIGN:
                            codeOrder = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            enterSignActivity();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_REJECTION:
                            codeOrder = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            enterRejectionActivity();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_STORAGE:
                            storageNo = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            scan_code_69.setText("SN码：" + codeSNIntent);
                            scan_code_sn.setText("储位号：" + storageNo);
                            scan_code_sn.setVisibility(View.VISIBLE);
                            inStorage();
                            break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "PDA出错,请重试\n\n" + e.getMessage(), Toast
                        .LENGTH_LONG).show();
            }
        }
    };

    /**
     * 盲扫-->检查69码是否备案
     */
    private void check69IsInclude() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.SEARCH_69_CODE)//
                .addParams("code", code69)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        if (response.isResult()) {
                            UIHelper.showMyCustomDialog(c, "商品未备案，是否备案？", "去备案", new View
                                    .OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra("code69", code69);
                                    JumpToActivity(IncludeActivity.class, intent);
                                    finish();
                                }
                            });
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig
                                    .SCAN_TYPE_CODE_SN);
                            intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                            intent.putExtra(AppConfig.CODE_69, code69);
                            JumpToActivity(PDAScanActivity.class, intent);
                            finish();
                        }
                    }
                });

    }

    /**
     * 盲扫-->配货
     */
    private void blindSnForMangSao() {
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.BLIND_SN_CODE)//
                .addParams("code", code69Intent)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMyCustomDialog(c, "配货成功，是否装车？", "去装车", new View
                                .OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig
                                        .SCAN_TYPE_CODE_CAR);
                                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                                intent.putExtra(AppConfig.CODE_SN, code69Intent);
                                JumpToActivity(PDAScanActivity.class, intent);
                                finish();
                            }
                        });
                    }
                });

    }

    /**
     * 指定扫-->配货
     */
    private void blindSnForZhiSao() {
        OkHttpUtils //
                .post()//
                .tag(this)//
                .url(Urls.BLIND_SN_CODE_ZHISAO)//
                .addParams("id", String.valueOf(orderId))//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, "配货成功！");
                        finish();
                    }
                });

    }

    /**
     * 如果得到SN，就去扫描储位号
     */
    private void toScanStorageNo() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CODE_SN, codeSN);
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_STORAGE);
        JumpToActivity(PDAScanActivity.class, intent);
    }

    /**
     * 货物出库
     */
    private void outStorage() {
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.OUT_STORAGE)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, "出库成功");
                        finish();
                    }
                });
    }


    /**
     * 货物入库
     */
    private void inStorage() {
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.IN_STORAGE)//
                .addParams("sn", codeSNIntent)//
                .addParams("storageNo", storageNo)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, "入库成功");
                        finish();
                    }
                });
    }

    /**
     * 查询商品是否贴标
     */
    private void query() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.FREE_INQUIRY)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<QueryBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(QueryBean response, int id) {
                        super.onResponseOK(response, id);
                        int status = response.getStatus();
                        String showTitle;
//                        String showVoice;
                        if (status == 1) {
                            showTitle = "请贴面单和子单1";
                        } else {
                            showTitle = "请贴子单" + status;
                        }
                        showVioce(showTitle);
                        UIHelper.showMyCustomDialog(c, showTitle, "我已经贴好了", new View
                                .OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                OkHttpUtils//
                                        .get()//
                                        .tag(this)//
                                        .url(Urls.COMMODITY_LABELING)//
                                        .addParams("sn", codeSN)//
                                        .build()//
                                        .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                                                     @Override
                                                     public void onResponseOK(StatusBean response, int id) {
                                                         super.onResponseOK(response, id);
                                                         UIHelper.showMessage(c, "贴标成功");
                                                         finish();
                                                     }
                                                 }

                                        );
                            }
                        });
                    }
                });
    }

    /**
     * 商品贴标复核
     */
    private void check() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.STICK_CHECK)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);

                    }
                });
    }

    /**
     * 盲扫-->装车
     */
    private void intoCarForMangsao() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.SCAN_CAR_MANGSAO)//
                .addParams("carNo", codeCar)//
                .addParams("sn", codeSNIntent)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, "装车成功！");
                        finish();
                    }
                });
    }

    /**
     * 指定扫描-->装车
     */
    private void intoCarForZhiSao() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.SCAN_CAR_ZHISAO)//
                .addParams("carNo", codeCar)//
                .addParams("id", String.valueOf(orderId))//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, "装车成功！");
                        finish();
                    }
                });
    }

    /**
     * 拿到运单号，进入签收页面
     */
    private void enterSignActivity() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.ORDER_CODE, codeOrder);
        JumpToActivity(SignActivity.class, intent);
    }

    /**
     * 拿到运单号，进入拒收页面
     */
    private void enterRejectionActivity() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.ORDER_CODE, codeOrder);
        JumpToActivity(RejectionActivity.class, intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mSamDataReceiver);
        super.onDestroy();
    }

    public void updateUI() {
        String voice = "";
        String text1 = "";
        if (currentType == AppConfig.SCAN_TYPE_CODE_69) {
            voice = SHOW_VOICE_69;
            text1 = SHOW_TEXT_69;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_SN) {
            voice = SHOW_VOICE_SN;
            text1 = SHOW_VOICE_SN;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_CAR) {
            voice = SHOW_VOICE_CAR;
            text1 = SHOW_VOICE_CAR;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_SIGN || currentType == AppConfig
                .SCAN_TYPE_CODE_REJECTION) {
            voice = SHOW_VOICE_ORDER;
            text1 = SHOW_VOICE_ORDER;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_STORAGE) {
            voice = SHOW_VOICE_STORAGE;
            text1 = SHOW_VOICE_STORAGE;
        }
        showVioce(voice);
        scanType.setText(text1);
    }

}
