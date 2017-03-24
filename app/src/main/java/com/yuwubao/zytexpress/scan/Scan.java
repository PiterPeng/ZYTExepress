package com.yuwubao.zytexpress.scan;

/**
 * Created by Peng on 2017/3/24
 * e-mail: phlxplus@163.com
 * description:
 */

public abstract class Scan {

    ScanResultInterface scanResultInterface;

    abstract void init();

    abstract void resume();

    abstract void start();

    abstract void stop();

    abstract void destroy();

    public void setScanResultInterface(ScanResultInterface scanResultInterface) {
        this.scanResultInterface = scanResultInterface;
    }
}
