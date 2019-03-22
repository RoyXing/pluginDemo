package com.plugin.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author roy.xing
 * @date 2019/3/13
 */
public class PluginActivity extends Activity implements Plugin {

    private int mFrom = FROM_INTERNAL;
    private Activity mProxyActivity;

    @Override
    public void attach(Activity activity) {
        mProxyActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFrom = savedInstanceState.getInt("FROM");
        }

        if (mFrom == FROM_INTERNAL) {
            super.onCreate(savedInstanceState);
            mProxyActivity = this;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            mProxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERNAL) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == FROM_INTERNAL) {
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom == FROM_INTERNAL) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mFrom == FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == FROM_INTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == FROM_INTERNAL) {
            super.onDestroy();
        }
    }
}
