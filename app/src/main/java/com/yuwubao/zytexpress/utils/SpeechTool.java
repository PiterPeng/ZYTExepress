package com.yuwubao.zytexpress.utils;

import android.content.Context;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * 百度语音识别模块。
 * 使用方法：
 * new SpeechTool(getApplicationContext()).speak("内容");
 *
 * @author Guan
 */
public class SpeechTool implements SpeechSynthesizerListener {
    private final String TAG = SpeechTool.class.getName();

    private final String appId = "9364215";
    private final String apikey = "a4octG5knvnKZGiKbF1u6ior";
    private final String secretkey = "d7d11101b6f5cd6be16054644d1ce886";

    private SpeechSynthesizer mSpeechSynthesizer;


    public SpeechTool(Context cx) {
        initialTts(cx);
    }

    public void speak(String str) {
        int result = this.mSpeechSynthesizer.speak(str);
        if (result < 0) {
            Log.e(TAG, "error,please look up error code in doc or URL:http://yuyin.baidu" +
                    ".com/docs/tts/122 ");
        }
    }

    private void initialTts(Context cx) {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(cx);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
//        // 文本模型文件路径 (离线引擎使用)
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE,
// mSampleDirPath + "/"
//                + TEXT_MODEL_NAME);
//        // 声学模型文件路径 (离线引擎使用)
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
// mSampleDirPath + "/"
//                + SPEECH_FEMALE_MODEL_NAME);
//        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license
// 文件时需要进行设置，如果在[应用管理]中开通了离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE,
// mSampleDirPath + "/"
//                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId(appId);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey(apikey, secretkey);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 发音音量调节到最大
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer
                .MIX_MODE_DEFAULT);
        // 授权检测接口(可以不使用，只是验证授权是否成功)
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            Log.i(TAG, "auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.e(TAG, "auth failed errorMsg=" + errorMsg);
        }
        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);
//        // 加载离线英文资源（提供离线英文合成功能）
//        int result =
//                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" +
// ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
//                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
//        Log.e(TAG,"loadEnglishModel result=" + result);
    }


    @Override
    public void onError(String arg0, SpeechError arg1) {
    }

    @Override
    public void onSpeechFinish(String arg0) {
    }

    @Override
    public void onSpeechProgressChanged(String arg0, int arg1) {
    }

    @Override
    public void onSpeechStart(String arg0) {
    }

    @Override
    public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
    }

    @Override
    public void onSynthesizeFinish(String arg0) {
    }

    @Override
    public void onSynthesizeStart(String arg0) {
    }

}
