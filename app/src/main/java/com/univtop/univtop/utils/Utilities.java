package com.univtop.univtop.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.univtop.univtop.UnivtopApplication;

/**
 * Created by dungpham on 11/1/15.
 */
public class Utilities {

    private static final String UNIVTOP_PREF = "univtop pref";
    private static final String KEY_API = "api key";
    public static String getLanguageCode() {
        String code = UnivtopApplication.getInstance().getResources().getConfiguration().locale.getLanguage();
        if (code == null) {
            Crashlytics.logException(new Throwable("Language code null"));
        } else if (code.length() != 2) {
            Crashlytics.logException(new Throwable("Language code was:" + code));
        }
        return code;
    }

    public static String getApiKey(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(UNIVTOP_PREF, Context.MODE_PRIVATE);
        return pref.getString(KEY_API, null);
    }

    public static void setApiKey(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(UNIVTOP_PREF, Context.MODE_PRIVATE);
        pref.edit().putString(KEY_API, key).commit();
    }
}
