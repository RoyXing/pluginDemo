package com.stander;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BaseService extends Service implements ServiceInterface {

    private Service service;

    public void insertAppContext(Service service) {
        this.service = service;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }


    @Override
    public void onDestroy() {

    }
}
