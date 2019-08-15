package com.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Plugin_StaticReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是插件里面的静态广播，我收到广播了", Toast.LENGTH_SHORT).show();

    }
}
