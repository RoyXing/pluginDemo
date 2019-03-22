package com.plugin.lib;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * @author roy.xing
 * @date 2019/3/13
 */
public class ProxyActivity extends Activity {

    private String className;
    private PluginApk pluginApk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = getIntent().getStringExtra("className");
        pluginApk = PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (pluginApk == null) {
            throw new RuntimeException("请先加载插件APK");
        }

        try {
            Class<?> clazz = pluginApk.mClassLoader.loadClass(className);
            Object object = clazz.newInstance();
            if (object instanceof Plugin) {
                Plugin plugin = (Plugin) object;
                plugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", Plugin.FROM_EXTERNAL);
                plugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return pluginApk != null ? pluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return pluginApk != null ? pluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return pluginApk != null ? pluginApk.mClassLoader : super.getClassLoader();
    }
}
