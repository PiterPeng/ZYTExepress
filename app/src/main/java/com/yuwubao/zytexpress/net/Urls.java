package com.yuwubao.zytexpress.net;

/**
 * 请求地址
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
public class Urls {
    public static boolean TEST_MODE = true;// 当前连接服务器模式，测试模式还是产线模式
    /**
     * 默认的API头地址
     */
    public static String TEST_HEAD_URL = "http://192.168.100.135:8080/";
    public static String ONLINE_HEAD_URL = "http://139.224.10.42:8080/";

    public static String HEAD_URL = TEST_MODE ? TEST_HEAD_URL : ONLINE_HEAD_URL;

    //登录
    public static String LOGIN = HEAD_URL + "sys/login";
    //指定扫描列表
    public static String PICK_UP = HEAD_URL + "oms/order/designated";
    //盲扫列表
    public static String MANG_SCAN = HEAD_URL + "oms/orderItem/queryItemTaked";
    //调度列表
    public static String DISPATCH = HEAD_URL + "oms/orderItem/orderPredictList";
    //统计 参数type=1 --->盲扫   参数type=2 --->指定扫描
    public static String COUNT = HEAD_URL + "oms/orderItem/findItemModeSum";
    //未备案列表
    public static String INCLUDE_LIST = HEAD_URL + "ssm/product/findProductList";
    //查询69码是否备案
    public static String SEARCH_69_CODE = HEAD_URL + "oms/orderItem/searchCode";
    //盲扫69备案
    public static String INCLUDE_69_CODE = HEAD_URL + "oms/order/blindRecord";
    //盲扫配货
    public static String BLIND_SN_CODE = HEAD_URL + "oms/order/blindSn";
    //扫描指定SN
    public static String BLIND_SN_CODE_ZHISAO = HEAD_URL + "oms/order/designatedSn";
    //逆向扫描指定SN
    public static String RE_BLIND_SN_CODE_ZHISAO = HEAD_URL + "oms/order/reverseTake";
    //待装车列表
    public static String INTO_CAR_LIST = HEAD_URL + "oms/orderItem/queryItemCar";
    //盲扫扫车
    public static String SCAN_CAR_MANGSAO = HEAD_URL + "oms/orderItem/itemScanInCar";
    //指定扫描 扫车
    public static String SCAN_CAR_ZHISAO = HEAD_URL + "oms/orderItem/itemInCar";
    //自由查询
    public static String FREE_INQUIRY = HEAD_URL + "oms/orderItem/itemStickScanning";
    //商品贴标
    public static String COMMODITY_LABELING = HEAD_URL + "oms/orderItem/itemStick";
    //商品复核
    public static String STICK_CHECK = HEAD_URL + "oms/orderItem/itemStickCheck";
    //实际入库
    public static String IN_STORAGE = HEAD_URL + "oms/order/inStorage";
    //理论入库
    public static String IN_STORAGE_THOERY = HEAD_URL + "oms/order/productFalseStorage";
    //出库
    public static String OUT_STORAGE = HEAD_URL + "oms/order/outStorage";
    //订单签收验证码
    public static String PHONE_VERIFICATION_CODE = HEAD_URL + "oms/order/sms";
    //正向订单签收
    public static String ORDER_SIGN = HEAD_URL + "oms/order/sign";
    //逆向订单签收
    public static String RE_VERSE_SIGN = HEAD_URL + "oms/order/reverseSign";
    //逆向订单签收列表
    public static String RE_ORDER_SIGN = HEAD_URL + "oms/order/reverseOrderList";
    //订单拒收
    public static String ORDER_REJECTION = HEAD_URL + "oms/order/deny";
    //调度汇总
    public static String DISPATCH_COUNT = HEAD_URL + "oms/orderItem/findItemTypeSum";
    //统计汇总
    public static String COUNT_COUNT = HEAD_URL + "oms/orderItem/orderPredictStatistics";
    //查询里面的统计
    public static String QUERY_COUNT = HEAD_URL + "oms/orderItem/findLabelingSum";
    //查询里面的列表
    public static String QUERY_LIST = HEAD_URL + "oms/orderItem/findLabelingList";
    //中转扫描
    public static String TRANSFER_SCAN = HEAD_URL + "oms/orderItem/orderTransfer";
    //历史提货列表
    public static String HISTORY_LIST = HEAD_URL + "oms/orderItem/queryItemLabelDay";
    //历史提货统计
    public static String HISTORY_COUNT = HEAD_URL + "oms/orderItem/findHistorySum";
    //检查是否需要填写手机号
    public static String CHECK_PHONE_NUMBER = HEAD_URL + "oms/order/send_no";
    //获取项目列表
    public static String FINDAUTHEDCUSTOMERLIST = HEAD_URL + "ssm/customer/findDeptCustomerList";
    //轮播图地址
    public static String BANNER_URLS = HEAD_URL + "sys/news/pda";
    //短信重发
    public static String RESENDSMS = HEAD_URL + "oms/order/resendSms";

    public static void change() {
        TEST_HEAD_URL = "http://192.168.100.135:8080/";
        ONLINE_HEAD_URL = "http://139.224.10.42:8080/";

        //登录
        LOGIN = HEAD_URL + "sys/login";
        PICK_UP = HEAD_URL + "oms/order/designated";

        MANG_SCAN = HEAD_URL + "oms/orderItem/queryItemTaked";

        DISPATCH = HEAD_URL + "oms/orderItem/queryItemType";
        COUNT = HEAD_URL + "oms/orderItem/findItemModeSum";

        INCLUDE_LIST = HEAD_URL + "ssm/product/findProductList";

        SEARCH_69_CODE = HEAD_URL + "oms/orderItem/searchCode";

        INCLUDE_69_CODE = HEAD_URL + "oms/order/blindRecord";

        BLIND_SN_CODE = HEAD_URL + "oms/order/blindSn";

        BLIND_SN_CODE_ZHISAO = HEAD_URL + "oms/order/designatedSn";

        RE_BLIND_SN_CODE_ZHISAO = HEAD_URL + "oms/order/reverseTake";

        INTO_CAR_LIST = HEAD_URL + "oms/orderItem/queryItemCar";

        SCAN_CAR_MANGSAO = HEAD_URL + "oms/orderItem/itemScanInCar";

        SCAN_CAR_ZHISAO = HEAD_URL + "oms/orderItem/itemInCar";

        FREE_INQUIRY = HEAD_URL + "oms/orderItem/itemStickScanning";

        COMMODITY_LABELING = HEAD_URL + "oms/orderItem/itemStick";

        STICK_CHECK = HEAD_URL + "oms/orderItem/itemStickCheck";

        IN_STORAGE = HEAD_URL + "oms/order/inStorage";

        IN_STORAGE_THOERY = HEAD_URL + "oms/order/productFalseStorage";

        OUT_STORAGE = HEAD_URL + "oms/order/outStorage";

        PHONE_VERIFICATION_CODE = HEAD_URL + "oms/order/sms";

        ORDER_SIGN = HEAD_URL + "oms/order/sign";

        RE_VERSE_SIGN = HEAD_URL + "oms/order/reverseSign";

        RE_ORDER_SIGN = HEAD_URL + "oms/order/reverseOrderList";

        ORDER_REJECTION = HEAD_URL + "oms/order/deny";

        DISPATCH_COUNT = HEAD_URL + "oms/orderItem/findItemTypeSum";

        COUNT_COUNT = HEAD_URL + "oms/orderItem/findItemTypeTotle";

        QUERY_COUNT = HEAD_URL + "oms/orderItem/findLabelingSum";

        QUERY_LIST = HEAD_URL + "oms/orderItem/findLabelingList";

        TRANSFER_SCAN = HEAD_URL + "oms/orderItem/orderTransfer";

        HISTORY_LIST = HEAD_URL + "oms/orderItem/queryItemLabelDay";

        HISTORY_COUNT = HEAD_URL + "oms/orderItem/findHistorySum";

        CHECK_PHONE_NUMBER = HEAD_URL + "oms/order/send_no";

        FINDAUTHEDCUSTOMERLIST = HEAD_URL + "ssm/customer/findDeptCustomerList";

        BANNER_URLS = HEAD_URL + "sys/news/pda";

    }

}
