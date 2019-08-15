package com.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.stander.ReceiverInterface;

public class Plugin_TestBroadcast extends BroadcastReceiver implements ReceiverInterface {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是插件里面的动态广播，我收到广播了", Toast.LENGTH_SHORT).show();
    }
}
