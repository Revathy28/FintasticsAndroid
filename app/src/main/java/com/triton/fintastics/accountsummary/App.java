package com.triton.fintastics.accountsummary;

import android.app.Application;

public class App extends Application {
    static App app;

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
