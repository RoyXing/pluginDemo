package com.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.stander.ServiceInterface;

import java.lang.reflect.Constructor;

public class ProxyService extends Service {

    private ServiceInterface serviceInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        if (serviceInterface != null)
            serviceInterface.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String className = intent.getStringExtra("className");
        try {
            Class<?> serviceClass = PluginManager.getInstance(getApplication()).getDexClassLoader().loadClass(className);
            Constructor<?> constructor = serviceClass.getConstructor();
            Object o = constructor.newInstance();

            if (o instanceof ServiceInterface) {
                serviceInterface = (ServiceInterface) o;
                serviceInterface.insertAppContext(this);
                serviceInterface.onStartCommand(intent, flags, startId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceInterface.onDestroy();
    }
}
