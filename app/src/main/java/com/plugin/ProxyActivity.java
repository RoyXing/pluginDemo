package com.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;

import com.stander.ActivityInterface;

import java.lang.reflect.Constructor;

public class ProxyActivity extends Activity {

    private ActivityInterface activityInterface;

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(getApplication()).getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(getApplication()).getDexClassLoader();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String className = getIntent().getStringExtra("className");
        try {
            Class<?> pluginActivity = getClassLoader().loadClass(className);
            Constructor<?> constructor = pluginActivity.getConstructor();
            Object instance = constructor.newInstance();
            if (instance instanceof ActivityInterface) {
                activityInterface = (ActivityInterface) instance;
                activityInterface.insertAppContext(this);

                Bundle bundle = new Bundle();
                bundle.putString("flag", "app");
                activityInterface.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityInterface.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityInterface.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityInterface.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInterface.onDestroy();
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");

        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent intent = new Intent(this, ProxyService.class);
        intent.putExtra("className", service.getStringExtra("className"));
        return super.startService(intent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        //在宿主注册广播
        String pluginReceiverClassName = receiver.getClass().getName();
        return super.registerReceiver(new ProxyReceiver(pluginReceiverClassName), filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
    }
}
