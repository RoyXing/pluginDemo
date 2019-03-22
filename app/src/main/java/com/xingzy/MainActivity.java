package com.xingzy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.plugin.lib.PluginManager;
import com.plugin.lib.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance().init(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apkPath = Utils.copyAssetAndWrite(MainActivity.this, "plugin.apk");
                // 加载apk
                PluginManager.getInstance().loadApk(apkPath);
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里除了指定类名外 还能怎么获取要跳转的activity？怎么知道的？
                Intent intent = new Intent(MainActivity.this, ProxyActivity.class);
                intent.putExtra("className", "com.xingzy.PluginActivity");
                startActivity(intent);
            }
        });
    }
}
