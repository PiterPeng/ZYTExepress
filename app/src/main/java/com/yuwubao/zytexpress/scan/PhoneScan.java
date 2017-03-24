package com.yuwubao.zytexpress.scan;

import android.app.Activity;
import android.os.Handler;
import android.widget.ImageButton;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.karics.library.zxing.android.BeepManager;
import com.karics.library.zxing.android.CaptureActivityHandler;
import com.karics.library.zxing.android.InactivityTimer;
import com.karics.library.zxing.android.IntentSource;
import com.karics.library.zxing.camera.CameraManager;
import com.karics.library.zxing.view.ViewfinderView;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Peng on 2017/3/24
 * e-mail: phlxplus@163.com
 * description:手机扫描界面
 */

public class PhoneScan extends Scan {

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

    Activity activity;


    public PhoneScan(Activity activity) {
        this.activity = activity;
    }

    @Override
    void init() {
        hasSurface = false;
        inactivityTimer = new InactivityTimer(activity);
        beepManager = new BeepManager(activity);
    }

    @Override
    void resume() {

    }

    @Override
    void start() {

    }

    @Override
    void stop() {

    }

    @Override
    void destroy() {

    }

}
