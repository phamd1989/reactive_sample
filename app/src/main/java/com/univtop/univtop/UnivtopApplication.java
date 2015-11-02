package com.univtop.univtop;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.univtop.univtop.activities.AbstractBaseActivity;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import io.fabric.sdk.android.Fabric;
import retrofit.RestAdapter;

/**
 * Created by duncapham on 8/9/15.
 */
public class UnivtopApplication extends Application {
    private static Context context;
    public static String accessToken;
    public static API client = null;
    public static String TAG = "univtop";
    private static final String baseURL = "http://development.univtop.com/api/v1";
    static UnivtopApplication mInstance;
    private Handler mainThreadHandler;
    public AbstractBaseActivity currentActivity;

    public UnivtopApplication() {
        super();
        UnivtopApplication.mInstance = this;
    }

    public static UnivtopApplication getInstance() {
        return mInstance;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        UnivtopApplication.accessToken = accessToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Crashlytics.logException(new Throwable("ON CREATE"));
        mainThreadHandler = new Handler(this.getMainLooper());
        ImagePipelineConfig ipconfig = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, ipconfig);
    }

    public static API getClient() {
        if (client == null) {
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(baseURL).build();
            client = restAdapter.create(API.class);
        }
        return client;
    }

    public void setCurrentActivity(AbstractBaseActivity activity) {
        this.currentActivity = activity;
    }

    public Handler getMainThreadHandler() {
        return mainThreadHandler;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
