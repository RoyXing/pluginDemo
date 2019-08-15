package com.stander;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity implements ActivityInterface {

    public Activity appActivity;

    @Override
    public void insertAppContext(Activity appActivity) {
        this.appActivity = appActivity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }

    public void setContentView(int layoutId) {
        appActivity.setContentView(layoutId);
    }

    public View findViewById(int resId) {
        return appActivity.findViewById(resId);
    }

    public void startActivity(Intent intent) {
        intent.putExtra("className", intent.getComponent().getClassName());
        appActivity.startActivity(intent);
    }

    public ComponentName startService(Intent service) {
        Intent intent = new Intent();
        intent.putExtra("className", service.getComponent().getClassName());

        return appActivity.startService(intent);
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return appActivity.registerReceiver(receiver, filter);
    }

    public void sendBroadcast(Intent intent) {
        appActivity.sendBroadcast(intent);
    }
}
