package com.plugin.lib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * @author roy.xing
 * @date 2019/3/13
 */
public class PluginApk {

    public PackageInfo mPackageInfo;
    public Resources mResources;
    public AssetManager mAssetManager;
    public DexClassLoader mClassLoader;


    public PluginApk(PackageInfo mPackageInfo, Resources mResources, DexClassLoader mClassLoader) {
        this.mPackageInfo = mPackageInfo;
        this.mResources = mResources;
        this.mAssetManager = mResources.getAssets();
        this.mClassLoader = mClassLoader;
    }
}
