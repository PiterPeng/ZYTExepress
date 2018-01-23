package com.yuwubao.zytexpress.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.BaseBean;
import com.yuwubao.zytexpress.bean.CodeBean;
import com.yuwubao.zytexpress.bean.NewStatusBean;
import com.yuwubao.zytexpress.bean.QueryBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.ScanModeBean;
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
import butterknife.OnClick;
import okhttp3.Call;

import static com.yuwubao.zytexpress.AppConfig.CODE_SN;
import static com.yuwubao.zytexpress.AppConfig.CURRENT_SCAN_TYPE;
import static com.yuwubao.zytexpress.AppConfig.ENTER_TYPE;
import static com.yuwubao.zytexpress.AppConfig.ENTER_TYPE_IN;
import static com.yuwubao.zytexpress.AppConfig.ENTER_TYPE_SN;
import static com.yuwubao.zytexpress.AppConfig.SCAN_TYPE_CODE_SALE;
import static com.yuwubao.zytexpress.AppConfig.SCAN_TYPE_CODE_XIANG_HAO_N;
import static com.yuwubao.zytexpress.AppConfig.SHOW_TEXT_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_BUY;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_CAR;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_CHUWEI;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_FACE_No;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_ORDER;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_SALE;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_SN;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_STORAGE;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_SUB_No;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_XIANG_HAO;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_XIANG_HAO_N;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_XIANG_HAO_O;
import static com.yuwubao.zytexpress.R.id.input_no;
import static com.zhy.http.okhttp.OkHttpUtils.post;

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
    TextView scan_type;
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
    String scan_buy = "";//买方料号
    String scan_sale = "";//卖方料号
    String scan_sale_intent = "";//卖方料号
    String scan_buy_intent = "";//买方料号
    String codeOrder = "";//运单号
    String storageNo = "";//储位号
    String subNo = "";//子单号
    String faceNumber = "";//面单号
    int currentType;//当前的扫描类型69 or SN
    int enterType;//进入类型 盲扫 or 制定扫描
    private String userId;
    boolean isNiXiang = false;//是否是逆向
    int scanId = 0;//当前扫描的出入库单id
    int index = 0;//当前扫描的类型
    int chuWeiId = 0;//扫描储位时的ID
    String scanType = "";//扫描类型
    String xiang_Hao = "";//箱号
    String xiang_Hao_N = "";//新的箱号
    String xiang_Hao_Intent = "";//传过来的箱号

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
        isNiXiang = getIntent().getExtras().getBoolean(AppConfig.IS_NIXIANG);
        currentType = getIntent().getExtras().getInt(CURRENT_SCAN_TYPE);
        enterType = getIntent().getExtras().getInt(ENTER_TYPE);
        code69Intent = getIntent().getExtras().getString(AppConfig.CODE_69);
        codeSNIntent = getIntent().getExtras().getString(CODE_SN);
        codeIDIntent = getIntent().getExtras().getString(AppConfig.CODE_ID);
        scan_sale_intent = getIntent().getExtras().getString("scan_sale");
        scan_buy_intent = getIntent().getExtras().getString("scan_buy");
        inType = getIntent().getExtras().getInt(AppConfig.IN_TYPE);
        scanId = getIntent().getExtras().getInt(AppConfig.SCAN_ID);
        index = getIntent().getExtras().getInt(AppConfig.SCAN_INDEX);
        Log.d("SCAN_INDEX", index + "");
        chuWeiId = getIntent().getExtras().getInt("id");
        scanType = getIntent().getExtras().getString("type");
        xiang_Hao_Intent = getIntent().getExtras().getString("xiang_Hao");
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
                    handleResult(intent.getStringExtra(AppConfig.SCN_CUST_EX_SCODE));
                } else if (intent.getAction().equals(ScanManager.ACTION_DECODE)) {
                    handleResult(getScanResultCode(intent));
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "PDA出错,请重试\n\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * 处理扫描成功后的结果
     *
     * @param resultCode
     */
    private void handleResult(String resultCode) {
        //获取条码
        switch (currentType) {
            case AppConfig.SCAN_TYPE_CODE_69:
                code69 = resultCode;
                if (!code69.toUpperCase().startsWith("69")) {
                    UIHelper.showMessage(c, "六九码错误请重新扫描");
                    return;
                }
                scan_code_69.setText("69码：" + code69);
                switch (enterType) {
                    case ENTER_TYPE_SN:
                        inScanForSN69();
                        break;
                    default:
                        check69IsInclude();
                        break;
                }
                break;
            case AppConfig.SCAN_TYPE_CODE_BUY:
                scan_buy = resultCode;
                switch (enterType) {
                    case ENTER_TYPE_IN://扫描方式为买方和卖方料号
                        Intent intent = new Intent();
                        intent.putExtra(CURRENT_SCAN_TYPE, SCAN_TYPE_CODE_SALE);
                        intent.putExtra(ENTER_TYPE, ENTER_TYPE_IN);
                        intent.putExtra("scan_buy", scan_buy);
                        intent.putExtra(AppConfig.SCAN_ID, scanId);
                        intent.putExtra(AppConfig.SCAN_INDEX, index);
                        intent.putExtra(CODE_SN, codeSNIntent);
                        JumpToActivity(PDAScanActivity.class, intent);
                        break;
                    default:
                        inScanForSNBuy();
                        break;
                }
                break;
            case AppConfig.SCAN_TYPE_CODE_SALE:
                scan_sale = resultCode;
                switch (enterType) {
                    case ENTER_TYPE_IN://扫描方式为买方和卖方料号
                        inScanForSNBuyAndSale();
                        break;
                    default:
                        inScanForSNSale();
                        break;
                }
                break;
            case AppConfig.SCAN_TYPE_CODE_CHUWEI:
                storageNo = resultCode;
                switch (enterType) {
                    case ENTER_TYPE_IN://移库进来
                        UIHelper.showMyCustomDialog(c, "是否确认移库操作?" + "\n" + "箱号：" + xiang_Hao_Intent + "\n" + "新储位号："
                                + storageNo, "确认", new View.OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                goYiKu();
                            }
                        }, null);
                        break;
                    default:
                        inScanForChuW();
                        break;
                }
                break;
            case AppConfig.SCAN_TYPE_CODE_XIANG_HAO_O:
                xiang_Hao = resultCode;
                Intent intent4 = new Intent();
                intent4.putExtra(CURRENT_SCAN_TYPE, SCAN_TYPE_CODE_XIANG_HAO_N);
                intent4.putExtra("xiang_Hao", xiang_Hao);
                JumpToActivity(PDAScanActivity.class, intent4);
                break;
            case AppConfig.SCAN_TYPE_CODE_XIANG_HAO_N:
                xiang_Hao_N = resultCode;
                chaiXiang();
                break;
            case AppConfig.SCAN_TYPE_CODE_SN:
                codeSN = resultCode;
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
                    case ENTER_TYPE_IN:
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
                    case AppConfig.ENTER_TYPE_RESEND://短信重发
                        reSendSMS();
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN://已点：只扫SN
                        inScanForSN();
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN_69://已点：SN+69
                        Intent intent = new Intent();
                        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_69);
                        intent.putExtra(AppConfig.CODE_SN, codeSN);
                        intent.putExtra(AppConfig.SCAN_INDEX, index);
                        intent.putExtra(AppConfig.SCAN_ID, scanId);
                        JumpToActivity(PDAScanActivity.class, intent);
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN_BUY://已点：SN+买方料号
                        Intent intent1 = new Intent();
                        intent1.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_BUY);
                        intent1.putExtra(AppConfig.CODE_SN, codeSN);
                        intent1.putExtra(AppConfig.SCAN_ID, scanId);
                        intent1.putExtra(AppConfig.SCAN_INDEX, index);
                        JumpToActivity(PDAScanActivity.class, intent1);
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN_SELL://已点：SN+卖方料号
                        Intent intent2 = new Intent();
                        intent2.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SALE);
                        intent2.putExtra(AppConfig.CODE_SN, codeSN);
                        intent2.putExtra(AppConfig.SCAN_INDEX, index);
                        intent2.putExtra(AppConfig.SCAN_ID, scanId);
                        JumpToActivity(PDAScanActivity.class, intent2);
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN_BUY_SELL://已点：SN+买方料号+卖方料号
                        Intent intent3 = new Intent();
                        intent3.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_BUY);
                        intent3.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN);
                        intent3.putExtra(AppConfig.CODE_SN, codeSN);
                        intent3.putExtra(AppConfig.SCAN_ID, scanId);
                        intent3.putExtra(AppConfig.SCAN_INDEX, index);
                        JumpToActivity(PDAScanActivity.class, intent3);
                        break;
                }
                break;
            case AppConfig.SCAN_TYPE_CODE_CAR:
                codeCar = resultCode;
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
                codeOrder = resultCode;
                enterSignActivity();
                break;
            case AppConfig.SCAN_TYPE_CODE_REJECTION:
                codeOrder = resultCode;
                enterRejectionActivity();
                break;
            case AppConfig.SCAN_TYPE_CODE_STORAGE:
                storageNo = resultCode;
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
                subNo = resultCode;
                tieSubNo();
                break;
            case AppConfig.SCAN_TYPE_CODE_SUBNO2:
                subNo = resultCode;
                checkSubNo();
                break;
            case AppConfig.SCAN_TYPE_CODE_TRANSFER:
                faceNumber = resultCode;
                transferScan();
                break;
            case AppConfig.SCAN_TYPE_CODE_XIANG_HAO:
                xiang_Hao = resultCode;
                Intent i = new Intent(c, PDAScanActivity.class);
                i.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CHUWEI);
                i.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN);
                i.putExtra("xiang_Hao", xiang_Hao);
                startActivity(i);
                finish();
                break;
        }

    }

    /**
     * 拆箱
     */
    private void chaiXiang() {
        /*
        *  @param caseNo 旧箱号
        *  @param devanningNo 拆分的新箱号
        *  @param num 拆分出去的数量
        */
        View view = LayoutInflater.from(c).inflate(R.layout.dialog_shoudong, null);
        final EditText input_no = (EditText) view.findViewById(R.id.input_no);
        input_no.setHint("请输入数量");
        AlertDialog alertDialog = new AlertDialog.Builder(c).setTitle("拆箱数量").setView(view).setPositiveButton("确定",
                null).setNegativeButton("取消", null).create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = input_no.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    UIHelper.showMessage(c, "输入不能为空");
                    return;
                }
                OkHttpUtils//
                        .post()//
                        .tag(this)//
                        .url(Urls.DEVANNING)//
                        .addParams(AppConfig.USER_ID, userId)//
                        .addParams("caseNo", xiang_Hao_Intent)//
                        .addParams("devanningNo", xiang_Hao_N)//
                        .addParams("num", number)//
                        .build()//
                        .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {

                            @Override
                            public void onResponseOtherCase(BaseBean response, int id) {
                                super.onResponseOtherCase(response, id);
                                showVioce("请求超时");
                            }

                            @Override
                            public void onResponseOK(BaseBean response, int id) {
                                super.onResponseOK(response, id);
                                UIHelper.showMessage(c, response.getMessage());
                            }
                        });
            }
        });

    }

    /**
     * 进行移库操作
     */
    private void goYiKu() {
        //
        post()//
                .url(Urls.TRANSFER)//
                .tag(this)//
                .addParams("userId", userId)//
                .addParams("cartonNo", xiang_Hao_Intent)//
                .addParams("whBinCode", storageNo)//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOtherCase(BaseBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        finish();
                    }
                });

    }

    /**
     * 入库扫描方式只扫SN
     */
    private void inScanForSN() {
        OkHttpUtils//
                .postString()//
                .tag(this)//
                .url(TextUtils.equals(scanType, "pancun") ? Urls.INVENTORYSCAN : Urls.SCAN)//
                .mediaType(AppConfig.JSON)//
                .content(new Gson().toJson(new ScanModeBean(Integer.parseInt(userId), scanId, index, codeSN, "", "",
                        "")))//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        finish();
                    }

                    @Override
                    public void onResponseOtherCase(BaseBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                    }
                });
    }

    /**
     * 入库扫描方式扫SN和69
     */
    private void inScanForSN69() {
        OkHttpUtils//
                .postString()//
                .tag(this)//
                .url(TextUtils.equals(scanType, "pancun") ? Urls.INVENTORYSCAN : Urls.SCAN)//
                .mediaType(AppConfig.JSON)//
                .content(new Gson().toJson(new ScanModeBean(Integer.parseInt(userId), scanId, index, codeSNIntent,
                        code69, "", "")))//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        finish();
                    }
                });
    }

    /**
     * 入库扫描方式扫SN和买方料号
     */
    private void inScanForSNBuy() {
        OkHttpUtils//
                .postString()//
                .tag(this)//
                .url(TextUtils.equals(scanType, "pancun") ? Urls.INVENTORYSCAN : Urls.SCAN)//
                .mediaType(AppConfig.JSON)//
                .content(new Gson().toJson(new ScanModeBean(Integer.parseInt(userId), scanId, index, codeSNIntent,
                        "", scan_buy, "")))//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        finish();
                    }

                    @Override
                    public void onResponseOtherCase(BaseBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                    }
                });
    }

    /**
     * 入库扫描方式扫SN和买方料号以及卖方料号
     */
    private void inScanForSNBuyAndSale() {
        OkHttpUtils//
                .postString()//
                .tag(this)//
                .url(TextUtils.equals(scanType, "pancun") ? Urls.INVENTORYSCAN : Urls.SCAN)//
                .mediaType(AppConfig.JSON)//
                .content(new Gson().toJson(new ScanModeBean(Integer.parseInt(userId), scanId, index, codeSNIntent,
                        "", scan_buy_intent, scan_sale)))//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        finish();
                    }

                    @Override
                    public void onResponseOtherCase(BaseBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                    }
                });
    }

    /**
     * 点击扫描储位号
     */
    private void inScanForChuW() {
        //
        post()//
                .url(Urls.SCANLOCATION)//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("ids", chuWeiId + "")//
                .addParams("locationNo", storageNo)//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        finish();
                    }

                    @Override
                    public void onResponseOtherCase(BaseBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                    }
                });

    }

    /**
     * 入库扫描方式扫SN和卖方料号
     */
    private void inScanForSNSale() {
        OkHttpUtils//
                .postString()//
                .tag(this)//
                .url(TextUtils.equals(scanType, "pancun") ? Urls.INVENTORYSCAN : Urls.SCAN)//
                .mediaType(AppConfig.JSON)//
                .content(new Gson().toJson(new ScanModeBean(Integer.parseInt(userId), scanId, index, codeSNIntent,
                        "", "", scan_sale)))//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponseOK(BaseBean response, int id) {
                        super.onResponseOK(response, id);
                        UIHelper.showMessage(c, response.getMessage());
                        finish();
                    }

                    @Override
                    public void onResponseOtherCase(BaseBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                    }
                });
    }

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
                        showVioce("请求超时");
                    }
                });

    }

    /**
     * 扫描SN后 扫描车号 然后装车
     */
    private void inToCar() {
        Intent intent = new Intent();
        intent.putExtra(CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CAR);
        intent.putExtra(ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
        intent.putExtra(CODE_SN, codeSN);
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
                                 showVioce("请求超时");
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
                .addParams("id", chuWeiId + "")//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
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
                .addParams("id", codeIDIntent)//
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
                                intent.putExtra(CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                                intent.putExtra(ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
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
        //
        post()//
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
                                intent.putExtra(CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CAR);
                                intent.putExtra(ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                                intent.putExtra(CODE_SN, codeSN);
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
        //
        post()//
                .tag(this)//
                .url(isNiXiang ? Urls.RE_BLIND_SN_CODE_ZHISAO : Urls.BLIND_SN_CODE_ZHISAO)//
                .addParams(isNiXiang ? "orderItemId" : "id", String.valueOf(orderId))//
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
        intent.putExtra(CODE_SN, codeSN);
        intent.putExtra(CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_STORAGE);
        JumpToActivity(PDAScanActivity.class, intent);
        finish();
    }

    /**
     * 货物出库
     */
    private void outStorage() {
        //
        post()//
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
        post()//
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
        //
        post()//
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
                .addParams("id", chuWeiId + "")//
                .build()//
                .execute(new GenericsCallback<QueryBean>(new GsonSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIHelper.showMessage(c, "服务器异常--->" + e.getMessage());
                        showVioce("请求超时");
                    }

                    @Override
                    public void onResponse(QueryBean response, int id) {
                        String showTitle, showVoice;
                        if (response != null) {
                            int status = response.getStatus();
                            String msg = response.getMessage();
                            String subFaceOrderNo;
                            String itemNo;
                            if (response.getResult() != null && response.getResult().getSubFaceOrderNo() != null) {
                                subFaceOrderNo = response.getResult().getSubFaceOrderNo();
                                itemNo = response.getResult().getItemNo();
                            } else {
//                                showTitle = "状态不对";
                                showVioce(msg);
                                UIHelper.showMessage(c, msg);
                                return;
                            }
                            if (status != -1) {
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
                                                + "】" + ",快捷单号：【" + itemNo + "】";
                                        showVoice = "请贴子单" + status + "，快捷单号" + itemNo.substring(itemNo.length() - 4,
                                                itemNo.length());
                                    }
                                    showVioce(showVoice);
                                    UIHelper.showMyCustomDialog(c, showTitle, "点我去贴单", new View.OnClickListener() {


                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.putExtra(CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SUBNO);
                                            intent.putExtra(CODE_SN, codeSN);
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
        intent.putExtra(CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SUBNO2);
        intent.putExtra(CODE_SN, codeSN);
        intent.putExtra("id", chuWeiId);
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
        if (isNiXiang) {
            Intent intent = new Intent();
            intent.putExtra(AppConfig.ORDER_CODE, codeOrder);
            intent.putExtra(AppConfig.IS_NIXIANG, true);
            intent.putExtra("isNeed", false);
            JumpToActivity(SignActivity.class, intent);
            finish();
        } else {
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
                            intent.putExtra(AppConfig.IS_NIXIANG, false);
                            intent.putExtra("isNeed", code == 1 ? true : false);
                            JumpToActivity(SignActivity.class, intent);
                            finish();
                        }
                    });
        }
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

    /**
     * 短信重发
     */
    private void reSendSMS() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            //
            post()//
                    .url(Urls.RESENDSMS)//
                    .addParams("userId", user.getId() + "")//
                    .addParams("sn", codeSN)//
                    .tag(this)//
                    .build()//
                    .execute(new AppGsonCallback<CodeBean>(new RequestModel(c)) {
                        @Override
                        public void onResponseOK(CodeBean response, int id) {
                            super.onResponseOK(response, id);
                            int code = response.getResult();
                            UIHelper.showCodeDialog(c, String.valueOf(code));
                        }
                    });
        }
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
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_BUY) {
            voice = SHOW_VOICE_BUY;
            text1 = SHOW_VOICE_BUY;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_SALE) {
            voice = SHOW_VOICE_SALE;
            text1 = SHOW_VOICE_SALE;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_CHUWEI) {
            voice = SHOW_VOICE_CHUWEI;
            text1 = SHOW_VOICE_CHUWEI;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_XIANG_HAO) {
            voice = SHOW_VOICE_XIANG_HAO;
            text1 = SHOW_VOICE_XIANG_HAO;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_XIANG_HAO_O) {
            voice = SHOW_VOICE_XIANG_HAO_O;
            text1 = SHOW_VOICE_XIANG_HAO_O;
        } else if (currentType == AppConfig.SCAN_TYPE_CODE_XIANG_HAO_N) {
            voice = SHOW_VOICE_XIANG_HAO_N;
            text1 = SHOW_VOICE_XIANG_HAO_N;
        }
        showVioce(voice);
        scan_type.setText(text1);
    }

    @OnClick(R.id.input)
    public void onViewClicked() {
        View view = LayoutInflater.from(c).inflate(R.layout.dialog_shoudong, null);
        final EditText input = (EditText) view.findViewById(input_no);

        AlertDialog alertDialog = new AlertDialog.Builder(c).setView(view).setTitle("手动录入二维码").setPositiveButton
                ("确定", null).setNegativeButton("取消", null).create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(input.getText().toString().trim())) {
                    UIHelper.showMessage(c, "输入不能为空");
                    return;
                }
                handleResult(input.getText().toString().trim());
            }
        });
    }
}
