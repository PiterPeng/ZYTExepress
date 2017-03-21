package com.yuwubao.zytexpress.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yuwubao.zytexpress.R;

import java.io.File;
import java.io.IOException;

/**
 * 图片加载、缓存、管理组件
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
public class ImageLoaderKit {

    private static final String TAG = ImageLoaderKit.class
            .getSimpleName();

    private static final int M = 1024 * 1024;

    private Context context;

    private ImageLoaderKit(Context context) {
        this.context = context;
        init();
    }

    public static void create(Context context) {
        new ImageLoaderKit(context);
    }

    public void init() {

        try {
            ImageLoader.getInstance().init(getDefaultConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    private ImageLoaderConfiguration getDefaultConfig() throws IOException {
        int MAX_CACHE_MEMORY_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8);
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                context.getPackageName() + "/cache/image/");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // 降低线程的优先级，减小对UI主线程的影响
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(MAX_CACHE_MEMORY_SIZE))
                .discCache(
                        new LruDiskCache(cacheDir, new Md5FileNameGenerator(),
                                0))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
                // (5
                // s),
                // readTimeout
                // (30
                // s)超时时间
                .writeDebugLogs().build();

        return config;
    }


    public static DisplayImageOptions normalLoadOption = createNormalOptions();

    //    public static DisplayImageOptions avatorLoadOption = createAvatorOptions();
//
//
    private static final DisplayImageOptions createNormalOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_message)
                .showImageForEmptyUri(R.drawable.icon_message)
                .showImageOnFail(R.drawable.icon_message)
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }
//
//    private static final DisplayImageOptions createAvatorOptions() {
//        return new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.default_image)
//                .showImageForEmptyUri(R.drawable.default_image)
//                .showImageOnFail(R.drawable.default_image)
//                .cacheInMemory(true).cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565).build();
//    }


}
