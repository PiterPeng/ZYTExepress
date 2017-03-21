package com.yuwubao.zytexpress.net;

import android.app.ProgressDialog;
import android.util.Log;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.BaseBean;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.helper.AuthHelper;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.zhy.http.okhttp.callback.GenericsCallback;

import okhttp3.Call;
import okhttp3.Request;


/**
 * 请求返回预处理
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
public class AppGsonCallback<T> extends GenericsCallback<T> {
    private RequestModel requestModle;
    private ProgressDialog progressDialog;

    public AppGsonCallback(RequestModel requestModle) {
        super(GsonSerializator.getInstance());
        this.requestModle = requestModle;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        Log.d("AppGsonCallback","onBefore....");
        if (requestModle.isShowProgress())
            progressDialog = UIHelper.showAppProgressBar(requestModle.getC());
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.d("AppGsonCallback","onError...."+e.getMessage());
        if (requestModle.isShowError())
            UIHelper.showMessage(requestModle.getC(), requestModle.getC().getString(R.string
                    .requestfailed));
    }

    @Override
    public void onResponse(T response, int id) {
        Log.d("AppGsonCallback","onResponse....");
        BaseBean baseBean = (BaseBean) response;
        if (requestModle.isOnlyDealSuccess()) {
            if (response != null) {
                if (baseBean.getStatus() == AppConfig.RESULT_OK) {
                    onResponseOK(response, id);
                } else if (baseBean.getStatus() == AppConfig.RESULT_LOGINOUT) {
                    AuthHelper.kickOut(requestModle.getC());
                } else {
                    if (requestModle.isAutoDealOtherCase()) {
                        UIHelper.showMessage(requestModle.getC(), baseBean.getMessage());
                    } else {
                        onResponseOtherCase(response, id);
                    }
                }
            } else {
                if (requestModle.isShowError())
                    UIHelper.showMessage(requestModle.getC(), requestModle.getC().getResources()
                            .getString(R.string
                                    .success_dataempty));
            }
        }
    }

    /**
     * 成功的情况
     *
     * @param response
     * @param id
     */
    public void onResponseOK(T response, int id) {

    }

    /**
     * 其他情况
     *
     * @param response
     * @param id
     */
    public void onResponseOtherCase(T response, int id) {

    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if (progressDialog != null)
            UIHelper.dismissAppProgressBar(progressDialog);
    }
}
