package com.karics.library.zxing.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.karics.library.zxing.camera.CameraManager;
import com.karics.library.zxing.view.ViewfinderView;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.BaseActivity;
import com.yuwubao.zytexpress.activity.IncludeActivity;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.StatusBean;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static com.yuwubao.zytexpress.AppConfig.SHOW_TEXT_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_69;
import static com.yuwubao.zytexpress.AppConfig.SHOW_VOICE_SN;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends BaseActivity implements
        SurfaceHolder.Callback {

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

    /**
     * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
     */

    @Override
    protected int getContentResourseId() {
        return R.layout.capture;
    }

    @Override
    protected void init() {
// 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
////隐藏状态栏
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
//                .LayoutParams.FLAG_FULLSCREEN);

        orderId = getIntent().getExtras().getInt(AppConfig.ORDER_ID);
        scanMode = getIntent().getExtras().getInt(AppConfig.SCAN_MODE);
        currentType = getIntent().getExtras().getInt(AppConfig.CURRENT_SCAN_TYPE);
        enterType = getIntent().getExtras().getInt(AppConfig.ENTER_TYPE);
        code69Intent = getIntent().getExtras().getString(AppConfig.CODE_69);

        Log.d("handleDecode", "orderId" + orderId + "," + "scanMode" + scanMode + "," +
                "currentType" +
                currentType + "," + "enterType" + enterType + "," + "code69Intent" + code69Intent);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        imageButton_back = (ImageButton) findViewById(R.id.capture_imageview_back);
        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

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
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
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
            codedContent = rawResult.getText();
            Toast.makeText(this, "扫描成功", Toast.LENGTH_SHORT).show();
            Log.d("handleDecode", "currentType" + currentType);
            switch (currentType) {
                case AppConfig.SCAN_TYPE_CODE_69:
//                    result = "请确认69码:【" + rawResult.getText() + "】";
                    code69 = rawResult.getText();
                    check69IsInclude();
                    break;
                case AppConfig.SCAN_TYPE_CODE_SN:
                    Log.d("handleDecode", "wwwwwwwwwwwwwwwwwwww");
//                    result = "请确认SN码:【" + rawResult.getText() + "】";
                    codeSN = rawResult.getText();
                    Log.d("handleDecode", "enterType" + enterType);
                    switch (enterType) {
                        case AppConfig.ENTER_TYPE_MANGSAO:
                            blindSnForMangSao();
                            break;
                        case AppConfig.ENTER_TYPE_ZHISAO:
                            blindSnForZhiSao();
                            break;
                    }
//                    currentType = AppConfig.SCAN_TYPE_CODE_CAR;
                    break;
            }
//            viewfinderView.setHintText2(result);
            alertDialog = new AlertDialog.Builder(c).create();
//            mHandler.postDelayed(runnable, 1000);
        }
    }

    /**
     * 指定扫-->配货
     */
    private void blindSnForZhiSao() {
        OkHttpUtils//
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
                        UIHelper.showMessage(c, "配货成功！");
                        finish();
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
                            Log.d("handleDecode", "qqqqqqqqqqqqqqq");
                            Intent intent = new Intent();
                            intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig
                                    .SCAN_TYPE_CODE_SN);
                            intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                            intent.putExtra(AppConfig.CODE_69, code69);
                            JumpToActivity(CaptureActivity.class, intent);
                        }
                    }
                });

    }

    String result = "";
    int orderId;//订单id
    int scanMode;//扫描类型
    String code69 = "";//69码，结果
    String code69Intent = "";//69码,接收
    String codeSN = "";//SN码
    String codedContent = "";
    int currentType;//当前的扫描类型69 or SN
    int enterType;//进入类型 盲扫 or 制定扫描
    int time = 5;
    private final static int ISTIME = 0;
    private AlertDialog alertDialog;

    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            if (alertDialog == null) {
                return;
            }
            if (ISTIME <= time) {
                alertDialog.setMessage(result);
                alertDialog.show();

                mHandler.postDelayed(this, 1000);
            } else {
                alertDialog.dismiss();
                Intent intent = new Intent(CaptureActivity.this, CaptureActivity.class);
                intent.putExtra("codedContent", codedContent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
                mHandler.removeCallbacks(this);
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
//                updateUI();
                break;
        }
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
            UIHelper.showMyCustomDialog(c, "是否装车", "装车", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showMessage(c, "跳入装车页面");
                }
            });
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
