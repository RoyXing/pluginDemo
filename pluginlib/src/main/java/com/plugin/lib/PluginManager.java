package com.plugin.lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @author roy.xing
 * @date 2019/3/13
 */
public class PluginManager {

    private static volatile PluginManager instance;

    private Context mContext;

    private PluginApk pluginApk;

    private PluginManager() {
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    /**
     * 加载插件apk
     *
     * @param apkPath
     */
    public void loadApk(String apkPath) {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            return;
        }
        DexClassLoader classLoader = createDexClassLoader(apkPath);
        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = createResources(assetManager);
        pluginApk = new PluginApk(packageInfo, resources, classLoader);
    }

    /**
     * 创建访问插件apk的DexClassLoader对象
     *
     * @param apkPath
     * @return
     */
    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

    /**
     * 创建访问插件apk资源的AssetManager对象
     *
     * @param apkPath
     * @return
     */
    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建访问插件apk资源的Resources对象
     *
     * @param assetManager
     * @return
     */
    private Resources createResources(AssetManager assetManager) {
        Resources resources = mContext.getResources();
        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    public PluginApk getPluginApk() {
        return pluginApk;
    }
}
