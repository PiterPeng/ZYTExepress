package com.yuwubao.zytexpress.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.NewStatusBean;
import com.yuwubao.zytexpress.bean.QueryBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.Status2Bean;
import com.yuwubao.zytexpress.bean.StatusBean;
import com.yuwubao.zytexpress.bean.TransferBackBean;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.GsonSerializator;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.GenericsCallback;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.yuwubao.zytexpress.AppConfig.SHOW_TEXT_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_CAR;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_FACE_No;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_ORDER;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_SN;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_STORAGE;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_SUB_No;

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
    ScanManager scanMgr;

    int orderId;//订单id
    int scanMode;//扫描类型
    int inType;//入库类型
    String code69 = "";//69码，结果
    String code69Intent = "";//69码,接收
    String codeSN = "";//SN码
    String codeSNIntent = "";//SN码,接收
    String codeIDIntent = "";//id,接收
    String codeFaceIntent = "";//扫描类型（中转，分拨，揽件）
    String codeCar = "";//车号
    String codeOrder = "";//运单号
    String storageNo = "";//储位号
    String subNo = "";//子单号
    String faceNumber = "";//面单号
    int currentType;//当前的扫描类型69 or SN
    int enterType;//进入类型 盲扫 or 制定扫描
    private String userId;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_pda_scan;
    }

    @Override
    protected void init() {
        scanMgr = new ScanManager();
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
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
        IntentFilter intentFilter;
        if (Build.MODEL.toUpperCase().equals("I6200S") || Build.MODEL.toUpperCase().equals("I6100S") || Build.MODEL
                .toUpperCase().equals("I6200A")) {
            intentFilter = new IntentFilter(ScanManager.ACTION_DECODE);
        } else {
            intentFilter = new IntentFilter(AppConfig.SCN_CUST_ACTION_SCODE);
        }
        registerReceiver(mSamDataReceiver, intentFilter);
    }

    private void resolveIntent() {
        orderId = getIntent().getExtras().getInt(AppConfig.ORDER_ID);
        scanMode = getIntent().getExtras().getInt(AppConfig.SCAN_MODE);
        currentType = getIntent().getExtras().getInt(AppConfig.CURRENT_SCAN_TYPE);
        enterType = getIntent().getExtras().getInt(AppConfig.ENTER_TYPE);
        code69Intent = getIntent().getExtras().getString(AppConfig.CODE_69);
        codeSNIntent = getIntent().getExtras().getString(AppConfig.CODE_SN);
        codeIDIntent = getIntent().getExtras().getString(AppConfig.CODE_ID);
        inType = getIntent().getExtras().getInt(AppConfig.IN_TYPE);
        codeFaceIntent = getIntent().getExtras().getString(AppConfig.CODE_FACE);
    }

    private void setHeader() {
        title.setTitle(getString(R.string.PDA));
    }

    private void onTouchButton() {
        if (Build.MODEL.toUpperCase().equals("I6200S") || Build.MODEL.toUpperCase().equals("I6100S") || Build.MODEL
                .toUpperCase().equals("I6200A")) {
            scanMgr.startDecode();
        } else {
            Intent scannerIntent = new Intent(AppConfig.SCN_CUST_ACTION_START);
            sendBroadcast(scannerIntent);
        }
    }

    private void onReleaseButton() {
        if (Build.MODEL.toUpperCase().equals("I6200S") || Build.MODEL.toUpperCase().equals("I6100S") || Build.MODEL
                .toUpperCase().equals("I6200A")) {
            scanMgr.stopDecode();
        } else {
            Intent scannerIntent = new Intent(AppConfig.SCN_CUST_ACTION_CANCEL);
            sendBroadcast(scannerIntent);
        }
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
                            if (!code69.toUpperCase().startsWith("69")) {
                                UIHelper.showMessage(c, "六九码错误请重新扫描");
                                return;
                            }
                            scan_code_69.setText("69码：" + code69);
                            check69IsInclude();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SN:
                            codeSN = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            if (codeSN.toUpperCase().startsWith("69")) {
                                UIHelper.showMessage(c, "SN码错误请重新扫描");
                                return;
                            }
                            if (TextUtils.isEmpty(code69Intent)) {
                                scan_code_69.setVisibility(View.GONE);
                            } else {
                                scan_code_69.setText("69码：" + code69Intent);
                            }
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
                                    switch (inType) {
                                        case AppConfig.IN_TYPE_THOERY:
                                            inStorageForThoery();
                                            break;
                                        case AppConfig.IN_TYPE_FACT:
                                            toScanStorageNo();
                                            break;
                                    }
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
                                case AppConfig.ENTER_TYPE_SCAN:
                                    scan();
                                    break;
                                case AppConfig.ENTER_TYPE_CAR:
                                    inToCar();
                                    break;
                            }
                            break;
                        case AppConfig.SCAN_TYPE_CODE_CAR:
                            codeCar = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            if (TextUtils.isEmpty(codeSNIntent)) {
                                scan_code_69.setVisibility(View.GONE);
                            } else {
                                scan_code_69.setText("SN码：" + codeSNIntent);
                            }
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
                            if (TextUtils.isEmpty(codeSNIntent)) {
                                scan_code_69.setVisibility(View.GONE);
                            } else {
                                scan_code_69.setText("SN码：" + codeSNIntent);
                            }
                            scan_code_sn.setText("储位号：" + storageNo);
                            scan_code_sn.setVisibility(View.VISIBLE);
                            inStorage();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SUBNO:
                            subNo = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            tieSubNo();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SUBNO2:
                            subNo = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            checkSubNo();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_TRANSFER:
                            faceNumber = intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE);
                            transferScan();
                            break;
                    }
                } else if (intent.getAction().equals(ScanManager.ACTION_DECODE)) {
                    switch (currentType) {
                        case AppConfig.SCAN_TYPE_CODE_69:
                            code69 = getScanResultCode(intent);
                            if (!code69.toUpperCase().startsWith("69")) {
                                UIHelper.showMessage(c, "六九码错误请重新扫描");
                                return;
                            }
                            scan_code_69.setText("69码：" + code69);
                            check69IsInclude();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SN:
                            codeSN = getScanResultCode(intent);
                            if (codeSN.toUpperCase().startsWith("69")) {
                                UIHelper.showMessage(c, "SN码错误请重新扫描");
                                return;
                            }
                            if (TextUtils.isEmpty(code69Intent)) {
                                scan_code_69.setVisibility(View.GONE);
                            } else {
                                scan_code_69.setText("69码：" + code69Intent);
                            }
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
                                    switch (inType) {
                                        case AppConfig.IN_TYPE_THOERY:
                                            inStorageForThoery();
                                            break;
                                        case AppConfig.IN_TYPE_FACT:
                                            toScanStorageNo();
                                            break;
                                    }
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
                                case AppConfig.ENTER_TYPE_SCAN:
                                    scan();
                                    break;
                                case AppConfig.ENTER_TYPE_CAR:
                                    inToCar();
                                    break;
                            }
                            break;
                        case AppConfig.SCAN_TYPE_CODE_CAR:
                            codeCar = getScanResultCode(intent);
                            if (TextUtils.isEmpty(codeSNIntent)) {
                                scan_code_69.setVisibility(View.GONE);
                            } else {
                                scan_code_69.setText("SN码：" + codeSNIntent);
                            }
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
                            codeOrder = getScanResultCode(intent);
                            enterSignActivity();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_REJECTION:
                            codeOrder = getScanResultCode(intent);
                            enterRejectionActivity();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_STORAGE:
                            storageNo = getScanResultCode(intent);
                            if (TextUtils.isEmpty(codeSNIntent)) {
                                scan_code_69.setVisibility(View.GONE);
                            } else {
                                scan_code_69.setText("SN码：" + codeSNIntent);
                            }
                            scan_code_sn.setText("储位号：" + storageNo);
                            scan_code_sn.setVisibility(View.VISIBLE);
                            inStorage();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SUBNO:
                            subNo = getScanResultCode(intent);
                            tieSubNo();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_SUBNO2:
                            subNo = getScanResultCode(intent);
                            checkSubNo();
                            break;
                        case AppConfig.SCAN_TYPE_CODE_TRANSFER:
                            faceNumber = getScanResultCode(intent);
                            transferScan();
                            break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "PDA出错,请重试\n\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * 中转扫描
     */
    private void transferScan() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.TRANSFER_SCAN)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("no", faceNumber)//
                .addParams("type", codeFaceIntent)//
                .build()//
                .execute(new AppGsonCallback<TransferBackBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(TransferBackBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
                        finish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("服务器异常");
                    }
                });

    }

    /**
     * 扫描SN后 扫描车号 然后装车
     */
    private void inToCar() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CAR);
        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
        intent.putExtra(AppConfig.CODE_SN, codeSN);
        JumpToActivity(PDAScanActivity.class, intent);
        finish();
    }

    /**
     * 贴子单号
     */
    private void tieSubNo() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.COMMODITY_LABELING)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("subNo", subNo)//
                .addParams("sn", codeSNIntent)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 super.onError(call, e, id);
                                 showVioce("服务器异常");
                             }

                             @Override
                             public void onResponseOK(StatusBean response, int id) {
                                 super.onResponseOK(response, id);
                                 UIHelper.showMessage(c, response.getMessage());
                                 showVioce(response.getMessage());
                                 finish();
                             }
                         }

                );
    }

    /**
     * 复核子单号
     */
    private void checkSubNo() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.STICK_CHECK)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("sn", codeSNIntent)//
                .addParams("subNo", subNo)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("服务器异常");
                    }

                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
                        finish();
                    }
                });

    }

    /**
     * 获取扫描结果（型号i6200S）
     *
     * @param intent
     * @return
     */
    private String getScanResultCode(Intent intent) {
        byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
        int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
        byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
        String result = new String(barcode, 0, barcodelen);
        return result;
    }

    /**
     * 查件扫描
     */
    private void scan() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.FREE_INQUIRY)//
                .addParams("sn", codeSN)//
                .addParams(AppConfig.USER_ID, userId)//
                .build()//
                .execute(new GenericsCallback<QueryBean>(new GsonSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIHelper.showMessage(c, "服务器异常--->" + e.getMessage());
                        Log.d("onError", "onError--->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(QueryBean response, int id) {
                        if (response != null) {
                            int status = response.getStatus();
                            String msg = response.getMessage();
                            if (status != -1) {
                                String showTitle;
                                if (TextUtils.equals(msg, "提货不完整")) {
                                    showTitle = "提货不完整";
                                    showVioce(showTitle);
                                    UIHelper.showMessage(c, showTitle);
                                } else {

                                }
                                Intent intent = new Intent();
                                intent.putExtra("querybean", response);
                                JumpToActivity(ShowDetailsActivity.class, intent);
                                finish();
                            } else {
                                UIHelper.showMessage(c, msg);
                            }
                        }
                    }
                });
    }

    /**
     * 盲扫-->检查69码是否备案
     */
    private void check69IsInclude() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.SEARCH_69_CODE)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("code", code69)//
                .build()//
                .execute(new AppGsonCallback<NewStatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(NewStatusBean response, int id) {
                        super.onResponseOK(response, id);
                        List<NewStatusBean.ResultBean> list = response.getResult();
                        if (list == null || list.isEmpty()) {
                            UIHelper.showMyCustomDialog(c, "商品未备案，请联系管理员备案。", "好的", null, null);
                        } else {
                            if (list.size() == 1) {
                                int mId = list.get(0).getId();
                                Intent intent = new Intent();
                                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                                intent.putExtra(AppConfig.CODE_69, code69);
                                intent.putExtra(AppConfig.CODE_ID, String.valueOf(mId));
                                JumpToActivity(PDAScanActivity.class, intent);
                                finish();
                            } else {
                                //这里需要处理一个69码有多个商品的情况，判断返回的List集合长度
                                Intent intent = new Intent();
                                intent.putExtra("bean", (Serializable) list);
                                intent.putExtra(AppConfig.CODE_69, code69);
                                JumpToActivity(SelectOneToBindActivity.class, intent);
                                finish();
                            }
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
                .addParams("code", codeIDIntent)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMyCustomDialog(c, "配货成功，是否装车？", "去装车", new View.OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CAR);
                                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                                intent.putExtra(AppConfig.CODE_SN, codeSN);
                                JumpToActivity(PDAScanActivity.class, intent);
                                finish();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
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
        finish();
    }

    /**
     * 货物出库
     */
    private void outStorage() {
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.OUT_STORAGE)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
                        finish();
                    }
                });
    }

    /**
     * 理论入库
     */
    private void inStorageForThoery() {
        OkHttpUtils.post()//
                .tag(this)//
                .url(Urls.IN_STORAGE_THOERY)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
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
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("storageNo", storageNo)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
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
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("sn", codeSN)//
                .build()//
                .execute(new GenericsCallback<QueryBean>(new GsonSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIHelper.showMessage(c, "服务器异常--->" + e.getMessage());
                        showVioce("服务器异常");
                    }

                    @Override
                    public void onResponse(QueryBean response, int id) {
                        String showTitle, showVoice;
                        if (response != null) {
                            int status = response.getStatus();
                            String msg = response.getMessage();
                            String subFaceOrderNo;
                            if (response.getResult() != null && response.getResult().getSubFaceOrderNo() != null) {
                                subFaceOrderNo = response.getResult().getSubFaceOrderNo();
                            } else {
//                                showTitle = "状态不对";
                                showVioce(msg);
                                UIHelper.showMessage(c, msg);
                                return;
                            }
                            if (status != -1) {
                                String itemNo = response.getResult().getItemNo();
                                if (TextUtils.equals(msg, "提货不完整")) {
                                    showTitle = "提货不完整";
                                    showVioce(showTitle);
                                    UIHelper.showMessage(c, showTitle);
                                    finish();
                                } else {
                                    if (status == 1) {
                                        showTitle = "请贴面单和子单1【" + subFaceOrderNo + "】,快捷单号：【" + itemNo + "】";
                                        showVoice = "请贴面单和子单一，快捷单号" + itemNo.substring(itemNo.length() - 4, itemNo
                                                .length());
                                    } else {
                                        showTitle = "请贴子单" + status + "【" + response.getResult().getSubFaceOrderNo()
                                                + "】";
                                        showVoice = "请贴子单" + status + "，快捷单号" + itemNo.substring(itemNo.length() - 4,
                                                itemNo.length());
                                    }
                                    showVioce(showVoice);

                                    UIHelper.showMyCustomDialog(c, showTitle, "点我去贴单", new View.OnClickListener() {


                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig
                                                    .SCAN_TYPE_CODE_SUBNO);
                                            intent.putExtra(AppConfig.CODE_SN, codeSN);
                                            JumpToActivity(PDAScanActivity.class, intent);
                                            finish();
                                        }
                                    }, null);
                                }

                            } else {
                                UIHelper.showMessage(c, msg);
                            }
                        }
                    }
                });
    }

    /**
     * 商品贴标复核
     */
    private void check() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SUBNO2);
        intent.putExtra(AppConfig.CODE_SN, codeSN);
        JumpToActivity(PDAScanActivity.class, intent);
        finish();
    }

    /**
     * 盲扫-->装车
     */
    private void intoCarForMangsao() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.SCAN_CAR_MANGSAO)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("carNo", codeCar)//
                .addParams("sn", codeSNIntent)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
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
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("carNo", codeCar)//
                .addParams("id", String.valueOf(orderId))//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        showVioce(response.getMessage());
                        finish();
                    }
                });
    }

    /**
     * 拿到运单号，进入签收页面
     */
    private void enterSignActivity() {
        OkHttpUtils//
                .get()//
                .tag(this)//
                .url(Urls.CHECK_PHONE_NUMBER)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("no", codeOrder)//
                .build()//
                .execute(new AppGsonCallback<Status2Bean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(Status2Bean response, int id) {
                        super.onResponseOK(response, id);
                        // code == 1 发送短信
                        int code = response.getResult();
                        Intent intent = new Intent();
                        intent.putExtra(AppConfig.ORDER_CODE, codeOrder);
                        intent.putExtra("isNeed", code == 1 ? true : false);
                        JumpToActivity(SignActivity.class, intent);
                        finish();
                    }
                });
    }

    /**
     * 拿到运单号，进入拒收页面
     */
    private void enterRejectionActivity() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.ORDER_CODE, codeOrder);
        JumpToActivity(RejectionActivity.class, intent);
        finish();
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
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_SIGN || currentType == AppConfig.SCAN_TYPE_CODE_REJECTION) {
            voice = SHOW_VOICE_ORDER;
            text1 = SHOW_VOICE_ORDER;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_STORAGE) {
            voice = SHOW_VOICE_STORAGE;
            text1 = SHOW_VOICE_STORAGE;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_SUBNO || currentType == AppConfig.SCAN_TYPE_CODE_SUBNO2) {
            voice = SHOW_VOICE_SUB_No;
            text1 = SHOW_VOICE_SUB_No;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_TRANSFER) {
            voice = SHOW_VOICE_FACE_No;
            text1 = SHOW_VOICE_FACE_No;
        }
        showVioce(voice);
        scanType.setText(text1);
    }

}
