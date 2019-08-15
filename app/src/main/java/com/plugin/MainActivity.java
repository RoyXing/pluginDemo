package com.plugin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void loadPlugin(View view) {
        PluginManager.getInstance(getApplication()).loadPlugin();
    }

    public void jump(View view) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");

        //获取插件包内Activity
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        ActivityInfo activity = packageInfo.activities[0];
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", activity.name);
        startActivity(intent);
    }

    public void loadStaticReceiver(View view) {
        PluginManager.getInstance(getApplication()).parserApkAction();
    }

    public void sendStaticReceiver(View view) {
        Intent intent = new Intent();
        intent.setAction("com.plugin_package_static.ACTION");
        sendBroadcast(intent);
    }
}
