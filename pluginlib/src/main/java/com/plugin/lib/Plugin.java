package com.plugin.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author roy.xing
 * @date 2019/3/13
 */
public interface Plugin {

    int FROM_INTERNAL = 0;
    int FROM_EXTERNAL = 1;

    void attach(Activity activity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
