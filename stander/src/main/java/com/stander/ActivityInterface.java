package com.stander;

import android.app.Activity;
import android.os.Bundle;

public interface ActivityInterface {

    /**
     * 注入宿主APP 环境
     *
     * @param appActivity
     */
    void insertAppContext(Activity appActivity);

    void onCreate(Bundle savedInstanceState);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
