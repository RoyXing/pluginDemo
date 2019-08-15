package com.plugin_package;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stander.BaseActivity;

public class Plugin_MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_activity_main);
        String flag = savedInstanceState.getString("flag");
        Toast.makeText(appActivity, flag, Toast.LENGTH_SHORT).show();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appActivity, Plugin_TestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(appActivity, Plugin_TestService.class));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter filter = new IntentFilter();
                filter.addAction("com.plugin_package.ACTION");
                registerReceiver(new Plugin_TestBroadcast(), filter);
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.plugin_package.ACTION");
                sendBroadcast(intent);
            }
        });
    }

}
