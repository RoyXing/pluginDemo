package com.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.stander.ReceiverInterface;

public class ProxyReceiver extends BroadcastReceiver {

    //插件里面的Receiver 全类名
    private String pluginReceiverClassName;

    public ProxyReceiver(String pluginReceiverClassName) {
        this.pluginReceiverClassName = pluginReceiverClassName;
        Log.e("roy", pluginReceiverClassName);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Class<?> pluginReceiver = PluginManager.getInstance(context).getDexClassLoader().loadClass(pluginReceiverClassName);
            Object pluginObject = pluginReceiver.newInstance();
            if (pluginObject instanceof ReceiverInterface){
                ReceiverInterface receiverInterface = (ReceiverInterface) pluginObject;
                receiverInterface.onReceive(context,intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
