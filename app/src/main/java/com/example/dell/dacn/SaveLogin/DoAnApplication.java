package com.example.dell.dacn.SaveLogin;

import android.app.Application;

/**
 * Created by DELL on 20/10/17.
 */

public class DoAnApplication extends Application{
    private static Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static Application getInstance() {
        return INSTANCE;
    }
}
