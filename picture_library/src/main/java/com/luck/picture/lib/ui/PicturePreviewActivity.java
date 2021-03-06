package com.luck.picture.lib.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.R;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.observable.ImagesObservable;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.yalantis.ucrop.MultiUCrop;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.dialog.OptAnimationLoader;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yalantis.ucrop.util.ToolbarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.path;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.ui
 * email：893855882@qq.com
 * data：16/12/31
 */
public class PicturePreviewActivity extends PictureBaseActivity implements View.OnClickListener {
    private ImageButton left_back;
    private TextView tv_img_num, tv_title, tv_ok, save_image;
    private RelativeLayout select_bar_layout;
    private PreviewViewPager viewPager;
    private int position;
    private RelativeLayout rl_title;
    private LinearLayout ll_check;
    private List<LocalMedia> images = new ArrayList<>();
    private List<LocalMedia> selectImages = new ArrayList<>();
    private TextView check;
    private SimpleFragmentAdapter adapter;
    private Handler mHandler = new Handler();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("app.activity.finish")) {
                finish();
                overridePendingTransition(0, R.anim.slide_bottom_out);
            } else if (action.equals("app.action.finish.preview")) {
                // 多图裁剪完关闭 预览界面，在图片列表中进行压缩，所以这里区分开来，不用统一的关闭activity
                finish();
                overridePendingTransition(0, R.anim.slide_bottom_out);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activity_image_preview);
        registerReceiver(receiver, "app.activity.finish", "app.action.finish.preview");
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        left_back = (ImageButton) findViewById(R.id.left_back);
        viewPager = (PreviewViewPager) findViewById(R.id.preview_pager);
        ll_check = (LinearLayout) findViewById(R.id.ll_check);
        save_image = (TextView) findViewById(R.id.save_image);
        select_bar_layout = (RelativeLayout) findViewById(R.id.select_bar_layout);
        check = (TextView) findViewById(R.id.check);
        left_back.setOnClickListener(this);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_img_num = (TextView) findViewById(R.id.tv_img_num);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_ok.setOnClickListener(this);
        position = getIntent().getIntExtra(FunctionConfig.EXTRA_POSITION, 0);
        rl_title.setBackgroundColor(backgroundColor);
        ToolbarUtil.setColorNoTranslucent(this, backgroundColor);
        tv_ok.setTextColor(completeColor);
        select_bar_layout.setBackgroundColor(previewBottomBgColor);
        boolean is_bottom_preview = getIntent().getBooleanExtra(FunctionConfig
                .EXTRA_BOTTOM_PREVIEW, false);
        HideBottomLayout();
        if (is_bottom_preview) {
            // 底部预览按钮过来
            images = (List<LocalMedia>) getIntent().getSerializableExtra(FunctionConfig
                    .EXTRA_PREVIEW_LIST);
        } else {
            images = ImagesObservable.getInstance().readLocalMedias();
        }

        if (is_checked_num) {
            tv_img_num.setBackgroundResource(R.drawable.message_oval_blue);
        }

        selectImages = (List<LocalMedia>) getIntent().getSerializableExtra(FunctionConfig
                .EXTRA_PREVIEW_SELECT_LIST);
        final boolean saveImage = getIntent().getBooleanExtra(FunctionConfig
                .EXTRA_PREVIEW_SAVE_IAMGE, false);
        if (saveImage) {
            save_image.setVisibility(View.VISIBLE);
            check.setVisibility(View.GONE);
        }
        initViewPageAdapterData();
        ll_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveImage) {
                    showToast("下载图片");
                    new Task().execute(images.get(position).getPath());
//                    saveImageToGallery(mContext, bitmap);
//                    SavaImage(bitmap, Environment
//                            .getExternalStorageDirectory() + File.separator + "com.yuwubao" +
//                            ".trafficsound" +
//                            File.separator + "image" + File.separator);

                } else {
                    // 刷新图片列表中图片状态
                    boolean isChecked;
                    if (!check.isSelected()) {
                        isChecked = true;
                        check.setSelected(true);
                        Animation animation = OptAnimationLoader.loadAnimation(mContext, R.anim
                                .modal_in);
                        check.startAnimation(animation);
                    } else {
                        isChecked = false;
                        check.setSelected(false);
                    }
                    if (selectImages.size() >= maxSelectNum && isChecked) {
                        Toast.makeText(PicturePreviewActivity.this, getString(R.string
                                .message_max_num, maxSelectNum), Toast.LENGTH_LONG).show();
                        check.setSelected(false);
                        return;
                    }
                    LocalMedia image = images.get(viewPager.getCurrentItem());
                    if (isChecked) {
                        selectImages.add(image);
                        image.setNum(selectImages.size());
                        if (is_checked_num) {
                            check.setText(image.getNum() + "");
                        }
                    } else {
                        for (LocalMedia media : selectImages) {
                            if (media.getPath().equals(image.getPath())) {
                                selectImages.remove(media);
                                subSelectPosition();
                                notifyCheckChanged(media);
                                break;
                            }
                        }
                    }
                    onSelectNumChange(true);
                }

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(position + 1 + "/" + images.size());
                if (is_checked_num) {
                    LocalMedia media = images.get(position);
                    check.setText(media.getNum() + "");
                    notifyCheckChanged(media);
                }
                onImageChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initViewPageAdapterData() {
        tv_title.setText(position + 1 + "/" + images.size());
        adapter = new SimpleFragmentAdapter(getSupportFragmentManager());
        check.setBackgroundResource(cb_drawable);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        onSelectNumChange(false);
        onImageChecked(position);
        if (is_checked_num) {
            tv_img_num.setBackgroundResource(R.drawable.message_oval_blue);
            LocalMedia media = images.get(position);
            check.setText(media.getNum() + "");
            notifyCheckChanged(media);
        }
    }

    /**
     * 选择按钮更新
     */
    private void notifyCheckChanged(LocalMedia imageBean) {
        if (is_checked_num) {
            check.setText("");
            for (LocalMedia media : selectImages) {
                if (media.getPath().equals(imageBean.getPath())) {
                    imageBean.setNum(media.getNum());
                    check.setText(String.valueOf(imageBean.getNum()));
                }
            }
        }
    }

    /**
     * 更新选择的顺序
     */
    private void subSelectPosition() {
        for (int index = 0, len = selectImages.size(); index < len; index++) {
            LocalMedia media = selectImages.get(index);
            media.setNum(index + 1);
        }
    }

    /**
     * 判断当前图片是否选中
     *
     * @param position
     */
    public void onImageChecked(int position) {
        check.setSelected(isSelected(images.get(position)));
    }

    /**
     * 当前图片是否选中
     *
     * @param image
     * @return
     */
    public boolean isSelected(LocalMedia image) {
        for (LocalMedia media : selectImages) {
            if (media.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新图片选择数量
     */
    public void onSelectNumChange(boolean isRefresh) {
        Animation animation = null;
        boolean enable = selectImages.size() != 0;
        if (enable) {
            tv_ok.setEnabled(true);
            tv_ok.setAlpha(1.0f);
            animation = AnimationUtils.loadAnimation(mContext, R.anim.modal_in);
            tv_img_num.startAnimation(animation);
            tv_img_num.setVisibility(View.VISIBLE);
            tv_img_num.setText(selectImages.size() + "");
            tv_ok.setText(getString(R.string.ok));
        } else {
            tv_ok.setEnabled(false);
            tv_ok.setAlpha(0.5f);
            tv_img_num.setVisibility(View.INVISIBLE);
            tv_ok.setText(getString(R.string.please_select));
        }

        if (isRefresh) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendBroadcast(new Intent().setAction("app.action.refresh.data").putExtra
                            (FunctionConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable)
                                    selectImages));
                }
            }, 100);
        }
    }


    public class SimpleFragmentAdapter extends FragmentPagerAdapter {

        public SimpleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PictureImagePreviewFragment fragment = PictureImagePreviewFragment.getInstance(images
                    .get(position).getPath(), selectImages);
            return fragment;
        }

        @Override
        public int getCount() {
            return images.size();
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.left_back) {
            finish();
        } else if (id == R.id.tv_ok) {
            if (selectMode == FunctionConfig.MODE_MULTIPLE && enableCrop && type ==
                    LocalMediaLoader.TYPE_IMAGE) {
                // 是图片和选择压缩并且是多张，调用批量压缩
                startMultiCopy(selectImages);
            } else {
                onResult(selectImages);
            }
        }
    }

    public void onResult(List<LocalMedia> images) {
        // 因为这里是单一实例的结果集，重新用变量接收一下在返回，不然会产生结果集被单一实例清空的问题
        List<LocalMedia> result = new ArrayList<>();
        for (LocalMedia media : images) {
            result.add(media);
        }
        sendBroadcast(new Intent().setAction("app.action.crop_data").putExtra(UCrop.EXTRA_RESULT,
                (Serializable) result));
        finish();
        overridePendingTransition(0, R.anim.slide_bottom_out);
    }

    /**
     * 多图裁剪
     *
     * @param medias
     */
    protected void startMultiCopy(List<LocalMedia> medias) {
        if (medias != null && medias.size() > 0) {
            LocalMedia media = medias.get(0);
            String path = media.getPath();
            // 去裁剪
            MultiUCrop uCrop = MultiUCrop.of(Uri.parse(path), Uri.fromFile(new File(getCacheDir()
                    , System.currentTimeMillis() + ".jpg")));
            MultiUCrop.Options options = new MultiUCrop.Options();
            switch (copyMode) {
                case FunctionConfig.COPY_MODEL_DEFAULT:
                    options.withAspectRatio(0, 0);
                    break;
                case FunctionConfig.COPY_MODEL_1_1:
                    options.withAspectRatio(1, 1);
                    break;
                case FunctionConfig.COPY_MODEL_3_2:
                    options.withAspectRatio(3, 2);
                    break;
                case FunctionConfig.COPY_MODEL_3_4:
                    options.withAspectRatio(3, 4);
                    break;
                case FunctionConfig.COPY_MODEL_16_9:
                    options.withAspectRatio(16, 9);
                    break;
            }
            options.setLocalMedia(medias);
            options.setCompressionQuality(compressQuality);
            options.withMaxResultSize(cropW, cropH);
            options.background_color(backgroundColor);
            options.copyMode(copyMode);
            uCrop.withOptions(options);
            uCrop.start(PicturePreviewActivity.this);
        }

    }

    /**
     * 隐藏底部视图
     */
    private void HideBottomLayout() {
        boolean hide = getIntent().getBooleanExtra(FunctionConfig.EXTRA_PREVIEW_IS_HIDEBOTTOM,
                false);
        if (hide)
            select_bar_layout.setVisibility(View.GONE);
        else
            select_bar_layout.setVisibility(View.VISIBLE);

    }


    /**
     * 将bitmap对象写入系统图库
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory() + File.separator + "com" +
                ".yuwubao.trafficsound" +
                File.separator + "image" + File.separator);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse
                ("file://" + path)));
    }

    /**
     * 保存位图到本地
     *
     * @param bitmap
     * @param path   本地路径
     * @return void
     */
    public void SavaImage(Bitmap bitmap, String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        //文件夹不存在，则创建它
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            fileOutputStream = new FileOutputStream(path + "/" + System.currentTimeMillis() + "" +
                    ".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    Bitmap bitmap;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x123) {
                saveImageToGallery(mContext, bitmap);
                showToast("已保存到系统相册");
            }
        }

    };


    /**
     * 异步线程下载图片
     */
    class Task extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            bitmap = GetImageInputStream((String) params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Message message = new Message();
            message.what = 0x123;
            handler.sendMessage(message);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
