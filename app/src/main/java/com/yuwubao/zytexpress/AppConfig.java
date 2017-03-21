package com.yuwubao.zytexpress;

import java.io.File;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
public class AppConfig {
    public static final String APP_DBNAME = "zytexpress.db";
    public static final int APP_DBVERSION = 1;
    public static final String APP_SHAPENAME = "zytexpress";
    //    public static final String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory()
    // .getPath();
    public static final String APP_PACKAGE_PATH = "com.yuwubao.zytexpress";
    public static final String FILEPATH = File.separator + APP_PACKAGE_PATH + File.separator;
    //--log
    public static final String LOG_DIRPATH = FILEPATH;
    public static final String LOG_FILENAME = "log.txt";
    //--voice
    public static final String VOICE_DIRPATH = FILEPATH + "voice/msc/tts.wav";
    public static final String VOICE_RECORDPATH = FILEPATH + "voice" + File.separator + "record" +
            ".acc";
    //--
    public static long lastPressTime;
    public static long exitduration = 2000;
    public static final String ISLOGINKEY = "isloginkey";

    public static final String ISAUTOLOGOIN = "isAutoLogin";
    //--
    public static final String STARTCOUNT = "startCount";
    //记录设备是否为PDA
    public static boolean isPDA = false;
    //请求扫码界面时调用
    public final static int SCANNIN_GREQUEST_CODE = 1;
    public final static String ORDER_ID = "ORDER_ID";
    public final static String SCAN_MODE = "SCAN_MODE";
    public final static String CURRENT_SCAN_TYPE = "CURRENT_SCAN_TYPE";
    public final static String ENTER_TYPE = "ENTER_TYPE";
    public final static String CODE_69 = "CODE_69";
    public final static int SCAN_TYPE_CODE_69 = 69;
    public final static int SCAN_TYPE_CODE_SN = 70;
    public final static int SCAN_TYPE_CODE_CAR = 71;
    public final static int ENTER_TYPE_MANGSAO = 72;
    public final static int ENTER_TYPE_ZHISAO = 73;
    //    public static int currentType = SCAN_TYPE_CODE_69;
    public static int enterType = ENTER_TYPE_MANGSAO;
    public final static String SHOW_VOICE_69 = "请扫描六九码";
    public final static String SHOW_TEXT_69 = "请扫描69码";
    public final static String SHOW_VOICE_SN = "请扫描SN码";


    //--返回码值
    public static final int RESULT_OK = 0;//成功
    public static final int RESULT_LOGINOUT = 301;//异地登录
    //--
    public static final String ISCHECKVERSION = "isCheckVersion";
    public static final String LASTVERSIONCODE = "LASTVERSIONCODE";// 上一次更新的版本id
    public static final String LASTVERSIONSIZE = "LASTVERSIONSIZE";// 更新版本的apk大小
    public static final String NEWVERSIONCODE = "NEWVERSIONCODE";// 最新的版本

    //banner图临时图片url
    public static final String BANNER_IAMGE_URL_01 = "https://timgsa.baidu" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1488794272451&di" +
            "=603cf395b03e1f09a85eb9315d726ffd&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia" +
            ".com%2FuploadImages%2F2015%2F113%2F51%2FMTHK4XLG04NS_1000x500.jpg";
    public static final String BANNER_IAMGE_URL_02 = "https://timgsa.baidu" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1488794272432&di" +
            "=6f4f637d4a09e24bafa6d0893654211e&imgtype=0&src=http%3A%2F%2Fimg.zcool" +
            ".cn%2Fcommunity%2F015105554265f00000019ae9c4f4ab.jpg";
    public static final String BANNER_IAMGE_URL_03 = "https://timgsa.baidu" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1488794272426&di" +
            "=55899c67c0b053c7800d8c4be49e5582&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia" +
            ".com%2FuploadImages%2F2015%2F113%2F13%2FBPH35431PH4U_1000x500.jpg";
    public static final String BANNER_IAMGE_URL_04 = "https://timgsa.baidu" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1488794272433&di" +
            "=e06465901364d94332fca092acdbb4f8&imgtype=0&src=http%3A%2F%2Fimg.zcool" +
            ".cn%2Fcommunity%2F017cf4554265f00000019ae976f081.jpg";

}
