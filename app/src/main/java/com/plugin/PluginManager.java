package com.plugin;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;

public class PluginManager {

    private static final String TAG = PluginManager.class.getSimpleName();
    private static PluginManager instance;
    private Context context;
    private DexClassLoader dexClassLoader;
    private Resources resources;

    private PluginManager(Application application) {
        this.context = application.getApplicationContext();
    }

    private PluginManager(Context application) {
        this.context = application;
    }

    public static PluginManager getInstance(Application application) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(application);
                }
            }
        }
        return instance;
    }

    public static PluginManager getInstance(Context application) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(application);
                }
            }
        }
        return instance;
    }

    public void loadPlugin() {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
            if (!file.exists()) {
                Log.e(TAG, "插件包不存在...");
                return;
            }

            String pluginPath = file.getAbsolutePath();

            //dexClassLoader缓存目录 /data/data/包名/pluginDir
            File pluginDir = context.getDir("pluginDir", Context.MODE_PRIVATE);
            dexClassLoader = new DexClassLoader(pluginPath, pluginDir.getAbsolutePath(), null, context.getClassLoader());

            //加载资源
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, pluginPath);

            Resources r = context.getResources();

            //使用宿主的环境 加载插件资源的Resources
            resources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }

    /**
     * 通过反射获取插件包的信息
     */
    public void parserApkAction() {
        try {
            //执行public Package parsePackage(File packageFile, int flags, boolean useCaches)
            //拿到Package对象
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
            if (!file.exists()) {
                Log.e(TAG, "插件包不存在...");
                return;
            }
            Class<?> clazz = Class.forName("android.content.pm.PackageParser");
            Object newInstance = clazz.newInstance();

            Method parsePackage = clazz.getMethod("parsePackage", File.class, int.class);
            Object mPackage = parsePackage.invoke(newInstance, file, PackageManager.GET_ACTIVITIES);

            //得到静态广播的集合
            Field receivers = mPackage.getClass().getDeclaredField("receivers");
            ArrayList arrayList = (ArrayList) receivers.get(mPackage);

            //此处Activity是packageParser内部类
            Class<?> componentClasses = Class.forName("android.content.pm.PackageParser$Component");
            Field declaredField = componentClasses.getDeclaredField("intents");

            Class<?> packageUserState = Class.forName("android.content.pm.PackageUserState");

            Class<?> mUserHandle = Class.forName("android.os.UserHandle");
            int userId = (int) mUserHandle.getMethod("getCallingUserId").invoke(null);

            for (Object mActivity : arrayList) {//mActivity --> <receiver android:name=".Plugin_StaticReceiver">
                //获取<intent-filter> 通过反射
                ArrayList<IntentFilter> intents = (ArrayList) declaredField.get(mActivity);
                //获取android:name activityInfo
                //执行 ActivityInfo generateActivityInfo(Activity a, int flags,PackageUserState state, int userId)
                Method generateActivityInfoMethod = clazz.getMethod("generateActivityInfo", mActivity.getClass(), int.class, packageUserState, int.class);
                //执行此方法 拿到ActivityInfo
                generateActivityInfoMethod.setAccessible(true);
                ActivityInfo activityInfo = (ActivityInfo) generateActivityInfoMethod.invoke(null, mActivity, 0, packageUserState.newInstance(), userId);
                String name = activityInfo.name;

                Class<?> mStateReceiverClass = getDexClassLoader().loadClass(name);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) mStateReceiverClass.newInstance();
                for (IntentFilter intent : intents) {
                    context.registerReceiver(broadcastReceiver, intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
