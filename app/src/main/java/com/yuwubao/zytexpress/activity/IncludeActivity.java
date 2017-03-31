package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.karics.library.zxing.android.CaptureActivity;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.adapter.ShowImgAdapter;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.StatusBean;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/21
 * e-mail: phlxplus@163.com
 * description: 备案
 */

public class IncludeActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.code69)
    TextView code69;
    @BindView(R.id.image)
    RecyclerView image;

    ShowImgAdapter showImgAdapter;
    List<LocalMedia> localMediaList;
    Map<String, File> files;
    String code;
    int id;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_include;
    }

    @Override
    protected void init() {
        initData();
        setHeader();
        setRecAdapter();
    }

    private void initData() {
        code = getIntent().getStringExtra("code69");
        id = getIntent().getExtras().getInt("id");
        localMediaList = new ArrayList<>();
        files = new HashMap<>();
        code69.setText(code);
    }

    @OnClick(R.id.submit)
    public void onClick() {
        if (files.isEmpty()) {
            UIHelper.showMessage(c, "请至少选择一张照片");
            return;
        }
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.INCLUDE_69_CODE)//
                .addParams("id", String.valueOf(id))//
                .addParams("code", code)//
                .addParams(AppConfig.USER_ID, AppConfig.userId)//
                .files("pics", files)
                .build()//
                .execute(new AppGsonCallback<StatusBean>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(StatusBean response, int id) {
                        super.onResponseOK(response, id);
                        Intent intent = new Intent();
                        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_MANGSAO);
                        intent.putExtra(AppConfig.CODE_69, code);
                        UIHelper.showMessage(c, "备案成功！请扫描SN码");
                        if (AppConfig.isPDA) {
                            JumpToActivity(PDAScanActivity.class, intent);
                        } else {
                            JumpToActivity(CaptureActivity.class, intent);
                        }
                        finish();
                    }
                });

    }

    private void setHeader() {
        title.setTitle(getString(R.string.include));
    }

    private void setRecAdapter() {
        showImgAdapter = new ShowImgAdapter(c, localMediaList, true);
        image.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false));
        image.setAdapter(showImgAdapter);
        showImgAdapter.setOnItemAddClickListener(new ShowImgAdapter.OnItemAddClickListener() {
            @Override
            public void onItemAddClick(int pos) {
                FunctionConfig config = new FunctionConfig();
                config.setImageSpanCount(4);
                config.setType(LocalMediaLoader.TYPE_IMAGE);
                config.setSelectMode(FunctionConfig.MODE_MULTIPLE);
                config.setMaxSelectNum(9);
                config.setShowCamera(true);
                config.setEnablePreview(true);
                config.setEnableCrop(false);
                config.setSelectMedia(localMediaList);
                config.setCompress(true);
                config.setCompressFlag(1);
                config.setCompressQuality(100);
                config.setCheckedBoxDrawable(R.drawable.selector_choose);
                config.setThemeStyle(ContextCompat.getColor(c, R.color.colorPrimary));
                // 先初始化参数配置，在启动相册
                PictureConfig.init(config);
                PictureConfig.getPictureConfig().openPhoto(c, resultCallback);
            }
        });

        showImgAdapter.setOnImgItemClickListener(new ShowImgAdapter.OnImgItemClickListener() {
            @Override
            public void onImgClick(int pos) {

            }
        });

        showImgAdapter.setOnItemImgDeletListener(new ShowImgAdapter.OnItemImgDeletListener() {
            @Override
            public void onDeleteImg(int pos) {
                localMediaList.remove(pos);
                showImgAdapter.notifyItemRemoved(pos);
                showImgAdapter.notifyItemRangeChanged(pos, localMediaList.size());
            }
        });
    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig
            .OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            if (resultList == null) {
                resultList = new ArrayList<>();
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    File file = new File(resultList.get(i).getPath());
                    files.put(file.getName(), file);
                    Log.d("path", file.getName() + "," + resultList.get(i).getPath());
                }
            }
            showImgAdapter.update(resultList, ShowImgAdapter.UPDATE_REPLACE);
        }
    };

}
