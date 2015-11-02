package com.univtop.univtop.utils;

import com.crashlytics.android.Crashlytics;
import com.univtop.univtop.UnivtopApplication;

/**
 * Created by dungpham on 11/1/15.
 */
public class Utilities {
    public static String getLanguageCode() {
        String code = UnivtopApplication.getInstance().getResources().getConfiguration().locale.getLanguage();
        if (code == null) {
            Crashlytics.logException(new Throwable("Language code null"));
        } else if (code.length() != 2) {
            Crashlytics.logException(new Throwable("Language code was:" + code));
        }
        return code;
    }
}
