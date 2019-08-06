package com.jingjingke.schedule;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CommonActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将状态栏设为透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            final int nBits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            params.flags = nBits;
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setAttributes(params);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
