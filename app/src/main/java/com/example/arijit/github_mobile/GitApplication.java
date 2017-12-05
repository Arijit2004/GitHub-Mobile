package com.example.arijit.github_mobile;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Created by arijit on 03/12/17.
 */

public class GitApplication extends Application {

    private static Context context;

    private static GitApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        context = this;
//        initImageLoader();
    }

    private void initImageLoader() {
        StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                getApplicationContext());

        config.threadPoolSize(2);
        config.denyCacheImageMultipleSizesInMemory();

        config.memoryCache(new LruMemoryCache(2 * 1024 * 1024));
        config.memoryCacheSize(2 * 1024 * 1024);
        config.memoryCacheSizePercentage(25);
        config.diskCacheSize(50 * 1024 * 1024);
        config.diskCacheFileCount(100);

        //init image display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();

        config.defaultDisplayImageOptions(options);

        ImageLoader.getInstance().init(config.build());
    }

    public static Context getContext() {
        return context;
    }

    public static synchronized GitApplication getInstance() {
        return mInstance;
    }

}
