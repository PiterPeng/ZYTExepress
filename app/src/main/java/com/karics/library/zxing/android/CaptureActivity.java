package com.karics.library.zxing.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.karics.library.zxing.camera.CameraManager;
import com.karics.library.zxing.view.ViewfinderView;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.BaseActivity;
import com.yuwubao.zytexpress.activity.RejectionActivity;
import com.yuwubao.zytexpress.activity.SelectOneToBindActivity;
import com.yuwubao.zytexpress.activity.ShowDetailsActivity;
import com.yuwubao.zytexpress.activity.SignActivity;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.GenericsCallback;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    @SuppressWarnings("unused")
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制
    private InactivityTimer inactivityTimer;
    // 声音、震动控制
    private BeepManager beepManager;

    private ImageButton imageButton_back;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    String userId;
    private TextView input;

    /**
     * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
     */

    @Override
    protected int getContentResourseId() {
        return R.layout.capture;
    }

    @Override
    protected void init() {
        Window window = getWindow();
        // 保持Activity处于唤醒状态
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        orderId = getIntent().getExtras().getInt(AppConfig.ORDER_ID);
        scanMode = getIntent().getExtras().getInt(AppConfig.SCAN_MODE);
        isNiXiang = getIntent().getExtras().getBoolean(AppConfig.IS_NIXIANG);
        currentType = getIntent().getExtras().getInt(AppConfig.CURRENT_SCAN_TYPE);
        enterType = getIntent().getExtras().getInt(AppConfig.ENTER_TYPE);
        code69Intent = getIntent().getExtras().getString(AppConfig.CODE_69);
        codeSNIntent = getIntent().getExtras().getString(AppConfig.CODE_SN);
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


        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            userId = String.valueOf(user.getId());
        }
        //点击事件
        setOnClick();

    }


    @Override
    protected void onResume() {
        super.onResume();
        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        handler = null;
        updateUI();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void setOnClick() {
        imageButton_back = (ImageButton) findViewById(R.id.capture_imageview_back);
        input = (TextView) findViewById(R.id.input);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(c).inflate(R.layout.dialog_shoudong, null);
                final EditText input_no = (EditText) view.findViewById(R.id.input_no);

                AlertDialog alertDialog = new AlertDialog.Builder(c).setTitle("手动录入二维码").setView(view)
                        .setPositiveButton("确定", null).setNegativeButton("取消", null).create();
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = input_no.getText().toString().trim();
                        if (TextUtils.isEmpty(code)) {
                            UIHelper.showMessage(c, "输入不能为空");
                            return;
                        }
                        simpleCode(code);
                    }
                });
            }
        });
    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(final Result rawResult, final Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;
        // 这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();
            Toast.makeText(this, "扫描成功", Toast.LENGTH_SHORT).show();
            simpleCode(rawResult.getText());
        }
    }

    /**
     * 合并处理结果
     *
     * @param code
     */
    private void simpleCode(String code) {
        switch (currentType) {
            case AppConfig.SCAN_TYPE_CODE_69:
                code69 = code;
                if (!code69.toUpperCase().startsWith("69")) {
                    UIHelper.showMessage(c, "六九码错误请重新扫描");
                    onPause();
                    onResume();
                    return;
                }
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
                scan_buy = code;
                switch (enterType) {
                    case ENTER_TYPE_IN://扫描方式为买方和卖方料号
                        Intent intent = new Intent();
                        intent.putExtra(CURRENT_SCAN_TYPE, SCAN_TYPE_CODE_SALE);
                        intent.putExtra(ENTER_TYPE, ENTER_TYPE_IN);
                        intent.putExtra(AppConfig.SCAN_INDEX, index);
                        intent.putExtra("scan_buy", scan_buy);
                        intent.putExtra(CODE_SN, codeSNIntent);
                        JumpToActivity(CaptureActivity.class, intent);
                        break;
                    default:
                        inScanForSNBuy();
                        break;
                }
                break;
            case AppConfig.SCAN_TYPE_CODE_SALE:
                scan_sale = code;
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
                scan_chuW = code;
                switch (enterType) {
                    case ENTER_TYPE_IN://移库进来
                        UIHelper.showMyCustomDialog(c, "是否确认移库操作?" + "\n" + "箱号：" + xiang_Hao_Intent + "\n" + "新储位号："
                                + scan_chuW, "确认", new View.OnClickListener() {


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
            case AppConfig.SCAN_TYPE_CODE_SN:
                codeSN = code;
                if (codeSN.toUpperCase().startsWith("69")) {
                    UIHelper.showMessage(c, "SN码错误请重新扫描");
                    onPause();
                    onResume();
                    return;
                }
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
                        JumpToActivity(CaptureActivity.class, intent);
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN_BUY://已点：SN+买方料号
                        Intent intent1 = new Intent();
                        intent1.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_BUY);
                        intent1.putExtra(AppConfig.CODE_SN, codeSN);
                        intent1.putExtra(AppConfig.SCAN_INDEX, index);
                        JumpToActivity(CaptureActivity.class, intent1);
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN_SELL://已点：SN+卖方料号
                        Intent intent2 = new Intent();
                        intent2.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SALE);
                        intent2.putExtra(AppConfig.CODE_SN, codeSN);
                        intent2.putExtra(AppConfig.SCAN_INDEX, index);
                        JumpToActivity(CaptureActivity.class, intent2);
                        break;
                    case AppConfig.ENTER_TYPE_IN_SN_BUY_SELL://已点：SN+买方料号+卖方料号
                        Intent intent3 = new Intent();
                        intent3.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_BUY);
                        intent3.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN);
                        intent3.putExtra(AppConfig.CODE_SN, codeSN);
                        intent3.putExtra(AppConfig.SCAN_INDEX, index);
                        JumpToActivity(CaptureActivity.class, intent3);
                        break;

                }
                break;
            case AppConfig.SCAN_TYPE_CODE_CAR:
                codeCar = code;
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
                codeOrder = code;
                enterSignActivity();
                break;
            case AppConfig.SCAN_TYPE_CODE_REJECTION:
                codeOrder = code;
                enterRejectionActivity();
                break;
            case AppConfig.SCAN_TYPE_CODE_STORAGE:
                storageNo = code;
                inStorage();
                break;
            case AppConfig.SCAN_TYPE_CODE_SUBNO:
                subNo = code;
                tieSubNo();
                break;
            case AppConfig.SCAN_TYPE_CODE_SUBNO2:
                subNo = code;
                checkSubNo();
                break;
            case AppConfig.SCAN_TYPE_CODE_TRANSFER:
                faceNumber = code;
                transferScan();
                break;
            case AppConfig.SCAN_TYPE_CODE_XIANG_HAO:
                xiang_Hao = code;
                Intent i = new Intent(this, CaptureActivity.class);
                i.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CHUWEI);
                i.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN);
                i.putExtra("xiang_Hao", xiang_Hao);
                startActivity(i);
                finish();
                break;
            case AppConfig.SCAN_TYPE_CODE_XIANG_HAO_O:
                xiang_Hao = code;
                Intent intent4 = new Intent();
                intent4.putExtra(CURRENT_SCAN_TYPE, SCAN_TYPE_CODE_XIANG_HAO_N);
                intent4.putExtra("xiang_Hao", xiang_Hao);
                JumpToActivity(CaptureActivity.class, intent4);
                break;
            case SCAN_TYPE_CODE_XIANG_HAO_N:
                xiang_Hao_N = code;
                chaiXiang();
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
        showVioce("请输入拆箱数量");
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
                                finish();
                            }
                        });
            }
        });

    }

    /**
     * 进行移库操作
     */
    private void goYiKu() {
        OkHttpUtils//
                .post()//
                .url(Urls.TRANSFER)//
                .tag(this)//
                .addParams("userId", userId)//
                .addParams("cartonNo", xiang_Hao_Intent)//
                .addParams("whBinCode", scan_chuW)//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(BaseBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
                    }
                });
    }

    /**
     * 点击扫描储位号
     */
    private void inScanForChuW() {
        OkHttpUtils//
                .post()//
                .url(Urls.SCANLOCATION)//
                .tag(this)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("ids", chuWeiId + "")//
                .addParams("locationNo", scan_chuW)//
                .build()//
                .execute(new AppGsonCallback<BaseBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
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
                        onPause();
                        onResume();
                    }
                });
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
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
                                 onPause();
                                 onResume();
                             }

                             @Override
                             public void onResponseOtherCase(StatusBean response, int id) {
                                 super.onResponseOtherCase(response, id);
                                 showVioce("请求超时");
                                 onPause();
                                 onResume();
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
     * 扫描SN后 扫描车号 然后装车
     */
    private void inToCar() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_CAR);
        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
        intent.putExtra(AppConfig.CODE_SN, codeSN);
        JumpToActivity(CaptureActivity.class, intent);
        finish();
    }

    /**
     * 查询货物状态
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
     * 理论入库
     */
    private void inStorageForThoery() {
        OkHttpUtils.post()//
                .tag(this)//
                .url(Urls.IN_STORAGE_THOERY)//
                .addParams("sn", codeSN)//
                .addParams(AppConfig.USER_ID, userId)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
     * 商品贴标复核
     */
    private void check() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SUBNO2);
        intent.putExtra(AppConfig.CODE_SN, codeSN);
        intent.putExtra("id", chuWeiId);
        JumpToActivity(CaptureActivity.class, intent);
        finish();
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
                        onPause();
                        onResume();
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
                                onPause();
                                onResume();
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
                                            intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig
                                                    .SCAN_TYPE_CODE_SUBNO);
                                            intent.putExtra(AppConfig.CODE_SN, codeSN);
                                            JumpToActivity(CaptureActivity.class, intent);
                                            finish();
                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onPause();
                                            onResume();
                                        }
                                    });
                                }

                            } else {
                                UIHelper.showMessage(c, msg);
                            }
                        }
                    }
                });
    }

    /**
     * 实际入库 如果得到SN，就去扫描储位号
     */
    private void toScanStorageNo() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.CODE_SN, codeSN);
        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_STORAGE);
        JumpToActivity(CaptureActivity.class, intent);
        finish();
    }

    /**
     * 货物入库
     */
    private void inStorage() {
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.IN_STORAGE)//
                .addParams(AppConfig.USER_ID, userId)//
                .addParams("sn", codeSNIntent)//
                .addParams("storageNo", storageNo)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
     * 货物出库
     */
    private void outStorage() {
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.OUT_STORAGE)//
                .addParams("sn", codeSN)//
                .addParams(AppConfig.USER_ID, userId)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
     * 拿到运单号，进入拒收页面
     */
    private void enterRejectionActivity() {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.ORDER_CODE, codeOrder);
        JumpToActivity(RejectionActivity.class, intent);
        finish();
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

                        @Override
                        public void onResponseOtherCase(Status2Bean response, int id) {
                            super.onResponseOtherCase(response, id);
                            showVioce("请求超时");
                            onPause();
                            onResume();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            super.onError(call, e, id);
                            showVioce("请求超时");
                            onPause();
                            onResume();
                        }
                    });
        }
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
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
     * 指定扫-->配货
     */
    private void blindSnForZhiSao() {
        OkHttpUtils //
                .post()//
                .tag(this)//
                .url(isNiXiang ? Urls.RE_BLIND_SN_CODE_ZHISAO : Urls.BLIND_SN_CODE_ZHISAO)//
                .addParams("id", String.valueOf(orderId))//
                .addParams("sn", codeSN)//
                .addParams(AppConfig.USER_ID, userId)//
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
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
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(StatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

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
                                JumpToActivity(CaptureActivity.class, intent);
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
     * 短信重发
     */
    private void reSendSMS() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            OkHttpUtils//
                    .post()//
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

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            super.onError(call, e, id);
                            showVioce("请求超时");
                            onPause();
                            onResume();
                        }

                        @Override
                        public void onResponseOtherCase(CodeBean response, int id) {
                            super.onResponseOtherCase(response, id);
                            showVioce("请求超时");
                            onPause();
                            onResume();
                        }
                    });
        }
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
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOtherCase(NewStatusBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onResponseOK(NewStatusBean response, int id) {
                        super.onResponseOK(response, id);
                        List<NewStatusBean.ResultBean> list = response.getResult();
                        if (list == null || list.isEmpty()) {
                            UIHelper.showMyCustomDialog(c, "商品未备案，请联系管理员备案。", "好的", new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
//                                    Intent intent = new Intent();
//                                    intent.putExtra("code69", code69);
//                                    JumpToActivity(IncludeListActivity.class, intent);
                                    finish();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent intent = new Intent();
//                                    intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_69);
//                                    intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
//                                    JumpToActivity(CaptureActivity.class, intent);
//                                    finish();
                                    onPause();
                                    onResume();
                                }
                            });
                        } else {
                            if (list.size() == 1) {
                                int mId = list.get(0).getId();
                                Intent intent = new Intent();
                                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                                intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                                intent.putExtra(AppConfig.CODE_69, code69);
                                intent.putExtra(AppConfig.CODE_ID, String.valueOf(mId));
                                JumpToActivity(CaptureActivity.class, intent);
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
                    public void onResponseOtherCase(TransferBackBean response, int id) {
                        super.onResponseOtherCase(response, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        showVioce("请求超时");
                        onPause();
                        onResume();
                    }
                });
    }

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
    String xiang_Hao = "";//箱号
    String xiang_Hao_N = "";//新的箱号
    String xiang_Hao_Intent = "";//传过来的箱号
    String scan_buy = "";//买方料号
    String scan_sale = "";//卖方料号
    String scan_sale_intent = "";//卖方料号
    String scan_buy_intent = "";//买方料号
    String scan_chuW = "";//储位号
    String scanType = "";//扫描类型
    int currentType;//当前的扫描类型69 or SN
    int enterType;//进入类型 盲扫 or 制定扫描
    boolean isNiXiang = false;//是否是逆向
    int scanId = 0;//当前扫描的出入库单id
    int index = 0;//当前扫描的类型
    int chuWeiId = 0;//扫描储位时的ID


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
        }
        showVioce(voice);
        viewfinderView.setHintText1(text1);
        viewfinderView.setHintText2("");
    }


    /**
     * 显示底层错误信息并退出应用
     */
    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }


}
