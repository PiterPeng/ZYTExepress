package com.yuwubao.zytexpress.net;

/**
 * 请求地址
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
public class Urls {
    public static final boolean TEST_MODE = true;// 当前连接服务器模式，测试模式还是产线模式
    /**
     * 默认的API头地址
     */
//    public static final String TEST_HEAD_URL = "http://192.168.100.123:8080/";//wl
    public static final String TEST_HEAD_URL = "http://192.168.100.66:8080/";//xs
    public static final String ONLINE_HEAD_URL = "http://139.224.10.42:8080/";

    public static final String HEAD_URL = TEST_MODE ? TEST_HEAD_URL : ONLINE_HEAD_URL;

    //登录
    public static final String LOGIN = HEAD_URL + "sys/login";
    //指定扫描列表
    public static final String PICK_UP = HEAD_URL + "oms/order/designated";
    //盲扫列表
    public static final String MANG_SCAN = HEAD_URL + "oms/orderItem/queryItemTaked";
    //调度列表
    public static final String DISPATCH = HEAD_URL + "oms/orderItem/queryItemType";
    //统计 参数type=1 --->盲扫   参数type=2 --->指定扫描
    public static final String COUNT = HEAD_URL + "oms/orderItem/findItemModeSum";
    //未备案列表
    public static final String INCLUDE_LIST = HEAD_URL + "ssm/product/findProductList";
    //查询69码是否备案
    public static final String SEARCH_69_CODE = HEAD_URL + "oms/orderItem/searchCode";
    //盲扫69备案
    public static final String INCLUDE_69_CODE = HEAD_URL + "oms/order/blindRecord";
    //盲扫配货
    public static final String BLIND_SN_CODE = HEAD_URL + "oms/order/blindSn";
    //扫描指定SN
    public static final String BLIND_SN_CODE_ZHISAO = HEAD_URL + "oms/order/designatedSn";
    //待装车列表
    public static final String INTO_CAR_LIST = HEAD_URL + "oms/orderItem/queryItemCar";
    //盲扫扫车
    public static final String SCAN_CAR_MANGSAO = HEAD_URL + "oms/orderItem/itemScanInCar";
    //指定扫描 扫车
    public static final String SCAN_CAR_ZHISAO = HEAD_URL + "oms/orderItem/itemInCar";
    //自由查询
    public static final String FREE_INQUIRY = HEAD_URL + "oms/orderItem/itemStickScanning";
    //商品贴标
    public static final String COMMODITY_LABELING = HEAD_URL + "oms/orderItem/itemStick";
    //商品复核
    public static final String STICK_CHECK = HEAD_URL + "oms/orderItem/itemStickCheck";
    //实际入库
    public static final String IN_STORAGE = HEAD_URL + "oms/order/inStorage";
    //理论入库
    public static final String IN_STORAGE_THOERY = HEAD_URL + "oms/order/productFalseStorage";
    //出库
    public static final String OUT_STORAGE = HEAD_URL + "oms/order/outStorage";
    //订单签收验证码
    public static final String PHONE_VERIFICATION_CODE = HEAD_URL + "oms/order/sms";
    //订单签收
    public static final String ORDER_SIGN = HEAD_URL + "oms/order/sign";
    //订单拒收
    public static final String ORDER_REJECTION = HEAD_URL + "oms/order/deny";
    //调度汇总
    public static final String DISPATCH_COUNT = HEAD_URL + "oms/orderItem/findItemTypeSum";
    //统计汇总
    public static final String COUNT_COUNT = HEAD_URL + "oms/orderItem/findItemTypeTotle";
}
