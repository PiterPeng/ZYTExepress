package com.yuwubao.zytexpress;

import java.io.File;

import okhttp3.MediaType;

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
    public static final String VOICE_RECORDPATH = FILEPATH + "voice" + File.separator + "record" + ".acc";
    //--
    public static long lastPressTime;
    public static long exitduration = 2000;
    public static final String ISLOGINKEY = "isloginkey";

    public static final String ISAUTOLOGOIN = "isAutoLogin";
    //--
    public static final String STARTCOUNT = "startCount";
    //记录设备是否为PDA
    public static boolean isPDA = false;
    public final static String USER_ID = "userId";
    //请求扫码界面时调用
    public final static String ORDER_ID = "ORDER_ID";
    public final static String SCAN_MODE = "SCAN_MODE";
    public final static String IS_NIXIANG = "IS_NIXIANG";
    public final static String ORDER_CODE = "ORDER_CODE";
    public final static String CURRENT_SCAN_TYPE = "CURRENT_SCAN_TYPE";
    public final static String ENTER_TYPE = "ENTER_TYPE";
    public final static String IN_TYPE = "IN_TYPE";
    public final static String CODE_69 = "CODE_69";
    public final static String CODE_SN = "CODE_SN";
    public final static String CODE_ID = "CODE_ID";
    public final static String CODE_FACE = "CODE_FACE";
    public final static String STATUS_NO = "STATUS_NO";
    public final static String SCAN_ID = "SCAN_ID";
    public final static String SCAN_INDEX = "SCAN_INDEX";
    public final static int SCAN_TYPE_CODE_69 = 69;
    public final static int SCAN_TYPE_CODE_SN = 70;
    public final static int SCAN_TYPE_CODE_CAR = 71;
    public final static int ENTER_TYPE_MANGSAO = 72;
    public final static int ENTER_TYPE_ZHISAO = 73;
    public final static int SCAN_TYPE_CODE_SIGN = 74;
    public final static int SCAN_TYPE_CODE_REJECTION = 75;
    public final static int ENTER_TYPE_IN = 76;
    public final static int ENTER_TYPE_OUT = 77;
    public final static int SCAN_TYPE_CODE_STORAGE = 78;
    public final static int ENTER_TYPE_QUERY = 79;
    public final static int ENTER_TYPE_CHECK = 80;
    public final static int IN_TYPE_THOERY = 81;
    public final static int IN_TYPE_FACT = 82;
    public final static int ENTER_TYPE_SCAN = 83;
    public final static int ENTER_TYPE_CAR = 84;
    public final static int SCAN_TYPE_CODE_SUBNO = 85;
    public final static int SCAN_TYPE_CODE_SUBNO2 = 86;
    public final static int SCAN_TYPE_CODE_TRANSFER = 87;
    public final static int ENTER_TYPE_RESEND = 88;
    public final static int ENTER_TYPE_IN_SN = 89;
    public final static int ENTER_TYPE_IN_SN_69 = 90;
    public final static int ENTER_TYPE_IN_SN_BUY = 91;
    public final static int ENTER_TYPE_IN_SN_SELL = 92;
    public final static int ENTER_TYPE_IN_SN_BUY_SELL = 93;
    public final static int ENTER_TYPE_SN = 94;
    public final static int SCAN_TYPE_CODE_BUY = 95;
    public final static int SCAN_TYPE_CODE_SALE = 96;
    public final static int SCAN_TYPE_CODE_CHUWEI = 97;
    public final static int SCAN_TYPE_CODE_XIANG_HAO = 98;
    public final static String SHOW_VOICE_69 = "请扫描六九码";
    public final static String SHOW_TEXT_69 = "请扫描69码";
    public final static String SHOW_VOICE_SN = "请扫描SN码";
    public final static String SHOW_VOICE_CAR = "请扫描车号";
    public final static String SHOW_VOICE_ORDER = "请扫描子单号或SN码";
    public final static String SHOW_VOICE_STORAGE = "请扫描储位号";
    public final static String SHOW_VOICE_SUB_No = "请扫描子单号";
    public final static String SHOW_VOICE_FACE_No = "请扫描子单号或SN码";
    public final static String SHOW_VOICE_BUY = "请扫描买方料号";
    public final static String SHOW_VOICE_SALE = "请扫描卖方料号";
    public final static String SHOW_VOICE_CHUWEI = "请扫描储位号";
    public final static String SHOW_VOICE_XIANG_HAO = "请扫描箱号";
    /***PDA扫描SCAN****/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice" + ".broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";
    /* defined by MEXXEN */
    public static final String SCN_CUST_ACTION_START = "android.intent.action.SCANNER_BUTTON_DOWN";
    public static final String SCN_CUST_ACTION_CANCEL = "android.intent.action.SCANNER_BUTTON_UP";

    //--返回码值
    public static final int RESULT_OK = 0;//成功
    public static final int RESULT_LOGINOUT = 301;//异地登录
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //--
    public static final String ISCHECKVERSION = "isCheckVersion";
    public static final String LASTVERSIONCODE = "LASTVERSIONCODE";// 上一次更新的版本id
    public static final String LASTVERSIONSIZE = "LASTVERSIONSIZE";// 更新版本的apk大小
    public static final String NEWVERSIONCODE = "NEWVERSIONCODE";// 最新的版本
    //--分页 请求参数
    public static final String CURRENT_PAGE = "page";// 当前第几页
    public static final String PAGE_SIZE = "pageSize";// 每页显示条数

    //banner图临时图片url
    public static final String BANNER_IAMGE_URL_01 = "https://timgsa.baidu" + "" + "" + "" + "" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1498112530343&di=75a6c85b996763135fd2f4c8912f9ac5" +
            "&imgtype=0&src=http%3A%2F%2Fs2.sinaimg.cn%2Fmiddle%2F46e725a4gb923374cc2e1%26690";
    public static final String BANNER_IAMGE_URL_02 = "https://timgsa.baidu" + "" + "" + "" + "" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1498112565619&di=c17e1eaefd9856758b1f9ebfb93a5631" +
            "&imgtype=0&src=http%3A%2F%2Ffdfs.xmcdn.com%2Fgroup3%2FM0A%2F4D%2F29%2FwKgDslNH-Duyq6qeAAN26chjMPo905.png";
    public static final String BANNER_IAMGE_URL_03 = "https://timgsa.baidu" + "" + "" + "" + "" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1498112565608&di=fbc29ca41cc90fc9b98384a949c8137a" +
            "&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmiddle%2F46e725a4gb9233aee3199%26690";
    public static final String BANNER_IAMGE_URL_04 = "https://timgsa.baidu" + "" + "" + "" + "" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1498112699467&di=dca8792ca998e36499533a7d7c6ba715" +
            "&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D758347139%2C3687152%26fm%3D214%26gp%3D0.jpg";

}
