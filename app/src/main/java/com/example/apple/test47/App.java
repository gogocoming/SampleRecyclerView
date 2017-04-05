package com.example.apple.test47;

import android.app.Application;

import com.antfortune.freeline.FreelineCore;


/**
 * 作者：gogocoming on 17/4/4 08:35
 * 邮箱：gogocoming@163.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this);
    }
}