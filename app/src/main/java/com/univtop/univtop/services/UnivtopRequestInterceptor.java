package com.univtop.univtop.services;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.univtop.univtop.UnivtopApplication;
import com.univtop.univtop.utils.PrefManager;
import com.univtop.univtop.utils.Utilities;

import retrofit.RequestInterceptor;

/**
 * Created by dungpham on 11/1/15.
 */
public class UnivtopRequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Accept", "application/json");

        String lat = PrefManager.getInstance().getPrefString(PrefManager.Keys.LOCATION_LAT, null);
        String lon = PrefManager.getInstance().getPrefString(PrefManager.Keys.LOCATION_LONG, null);
        if (lat != null && lon != null) {
            request.addHeader("X-LATITUDE", lat);
            request.addHeader("X-LONGITUDE", lon);
        }
        request.addHeader("Accept-Language", Utilities.getLanguageCode());

        try {
            PackageInfo pInfo = null;
            pInfo = UnivtopApplication.getInstance().getPackageManager().getPackageInfo(UnivtopApplication.getInstance().getPackageName(), 0);
            request.addHeader("X-ANDROID-VERSION", String.valueOf(pInfo.versionCode));
            if (pInfo.versionName != null) {
                request.addHeader("User-agent", "Android v" + pInfo.versionName + " - SDK:" + Build.VERSION.SDK_INT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
