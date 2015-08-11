package com.univtop.univtop;

import android.app.Application;
import android.content.Context;

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

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        UnivtopApplication.accessToken = accessToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UnivtopApplication.context = this;
    }

    public static API getClient() {
        if (client == null) {
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(baseURL).build();
            client = restAdapter.create(API.class);
        }
        return client;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
