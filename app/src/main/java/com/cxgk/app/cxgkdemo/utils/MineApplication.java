package com.cxgk.app.cxgkdemo.utils;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.core.ImagePipelineConfig;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/4.
 */
public class MineApplication extends Application {


    public static List<Activity> activities = new ArrayList<>();
    // 创建一个TAG，方便调试或Log
    private static final String TAG = MineApplication.class.getSimpleName();

    // 创建一个static ApplicationController对象，便于全局访问
    private static MineApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext());
        mInstance = this;
        createDir();
        frescoInit();
        LogUtils.isDebug(true);
    }

    private void frescoInit() {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext()).
                setMaxCacheSize(30 * 1024 * 1024).setBaseDirectoryName("fresco_cache").setBaseDirectoryPathSupplier(new Supplier<File>() {
            @Override
            public File get() {
                return getApplicationContext().getCacheDir();
            }
        }).build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setMainDiskCacheConfig(diskCacheConfig).setImageCacheStatsTracker(new ImageCacheStatsTracker() {
                    @Override
                    public void onBitmapCachePut() {

                    }

                    @Override
                    public void onBitmapCacheHit() {

                    }

                    @Override
                    public void onBitmapCacheMiss() {

                    }

                    @Override
                    public void onMemoryCachePut() {

                    }

                    @Override
                    public void onMemoryCacheHit() {

                    }

                    @Override
                    public void onMemoryCacheMiss() {

                    }

                    @Override
                    public void onStagingAreaHit() {

                    }

                    @Override
                    public void onStagingAreaMiss() {

                    }

                    @Override
                    public void onDiskCacheHit() {

                    }

                    @Override
                    public void onDiskCacheMiss() {

                    }

                    @Override
                    public void onDiskCacheGetFail() {

                    }

                    @Override
                    public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> bitmapMemoryCache) {

                    }

                    @Override
                    public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> encodedMemoryCache) {

                    }
                }).setDownsampleEnabled(true).setBitmapsConfig(Bitmap.Config.RGB_565).build();
        Fresco.initialize(getApplicationContext(), imagePipelineConfig);
    }

    /**
     * 创建目录
     */
    private void createDir() {
        File downDIr = new File(Environment.getExternalStorageDirectory() + "/" + Constants.dir_base + "/" + Constants.dir_downloads);
        File imgDir = new File(Environment.getExternalStorageDirectory() + "/" + Constants.dir_base + "/" + Constants.dir_images);
        File logsDir = new File(Environment.getExternalStorageDirectory() + "/" + Constants.dir_base + "/" + Constants.dir_log);
        if (!downDIr.isDirectory()) {
            downDIr.mkdirs();
        }
        if (!imgDir.isDirectory()) {
            imgDir.mkdirs();
        }
        if (!logsDir.isDirectory()) {
            imgDir.mkdirs();
        } else {
            //            for (File file : logsDir.listFiles()) {
            //                file.delete();
            //            }
        }
    }

    /**
     * 以下为需要我们自己封装的添加请求取消请求等方法
     */

    /**
     * 用于返回一个ApplicationController单例
     *
     * @return
     */
    public static synchronized MineApplication getInstance() {
        return mInstance;
    }


    public static void exitApp() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }


}
