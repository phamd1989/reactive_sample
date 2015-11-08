package com.univtop.univtop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.univtop.univtop.UnivtopApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dungpham on 11/1/15.
 */
public class PrefManager {
    private static volatile PrefManager instance = null;
    private SharedPreferences prefs;
    private static final String UNIVTOP_PREF = "univtop pref";

    public static PrefManager getInstance() {
        if (instance == null) {
            synchronized (PrefManager.class) {
                // Double check
                if (instance == null) {
                    instance = new PrefManager();
                }
            }
        }
        return instance;
    }

    private PrefManager() {
        prefs = UnivtopApplication.getInstance().getSharedPreferences(
                UNIVTOP_PREF, Context.MODE_MULTI_PROCESS);
    }

    public Set<String> getPrefStringSet(String key) {
        if (prefs != null) {
            return prefs.getStringSet(key, null);
        }
        return null;
    }

    public String getPrefString(String key) {
        return getPrefString(key, "");
    }

    public String getPrefString(String key, String fallback) {
        if (prefs != null) {
            return prefs.getString(key, fallback);
        }

        return fallback;
    }

    public long getPrefLong(String key) {
        return getPrefLong(key, -1);
    }

    public long getPrefLong(String key, long fallback) {
        if (prefs != null) {
            return prefs.getLong(key, fallback);
        }

        return fallback;
    }

    public int getPrefInt(String key, int fallback) {
        if (prefs != null) {
            return prefs.getInt(key, fallback);
        }

        return fallback;
    }

    public boolean getPrefBoolean(String key, boolean fallback) {
        if (prefs != null) {
            return prefs.getBoolean(key, fallback);
        }

        return fallback;
    }

    public boolean getPrefBoolean(String key) {
        return getPrefBoolean(key, false);
    }


    public void setGCMRegistrationID(String rid) {
        if (prefs != null) {
            prefs.edit().putString(Keys.GCM_RID, rid).apply();
        }
    }

    public String getGCMRegistrationID() {
        if (prefs != null) {
            return prefs.getString(Keys.GCM_RID, null);
        }
        return null;
    }

    public boolean getGCMRegOnServer() {
        if (prefs != null) {
            return prefs.getBoolean(Keys.GCM_REG_ON_SERVER, false);
        }
        return false;
    }

    public void setGCMRegOnServer(boolean bool) {
        if (prefs != null) {
            prefs.edit().putBoolean(Keys.GCM_REG_ON_SERVER, bool).apply();
        }
    }

    public void setPref(String key, Object obj) {
        if (prefs != null) {
            if (obj == null) {
                prefs.edit().remove(key).apply();
            } else if (obj instanceof Boolean) {
                prefs.edit().putBoolean(key, (Boolean) obj).apply();
            } else if (obj instanceof Integer) {
                prefs.edit().putInt(key, (Integer) obj).apply();
            } else if (obj instanceof String) {
                prefs.edit().putString(key, (String) obj).apply();
            } else if (obj instanceof Long) {
                prefs.edit().putLong(key, (Long) obj).apply();
            } else if (obj instanceof List) {
                String listString = TextUtils.join(",ck,", ((List<String>) obj).toArray(new String[((List<String>) obj).size()])); //TODO dont serialize with ,ck,
                if (listString.isEmpty()) {
                    listString = null;
                }

                prefs.edit().putString(key, listString).apply();
            } else if (obj instanceof Set) {
                prefs.edit().putStringSet(key, (Set<String>) obj).apply();
            }
        }
    }

    public void clearPrefs() {
        prefs.edit().clear().apply();
    }

    public List<String> getPrefStringList(String key) {
        if (prefs != null) {
            String str = prefs.getString(key, null);
            if (str != null) {
                ArrayList<String> ret = new ArrayList<>();
                for (String s : Arrays.asList(str.split(",ck,"))) {
                    ret.add(s);
                }
                return ret;  //TODO dont serialize with ,ck,
            }
        }

        return new ArrayList<>();
    }

    public static String getUserId() {

        return PrefManager.getInstance().getPrefString(Keys.USER_ID, null);
    }

    public final static class Keys {
        public static final String EXPERIMENTS = "EXPERIMENTS";
        public static final String LOCATION_LONG = "LOCATION_LONG";
        public static final String LOCATION_LAT = "LOCATION_LAT";
        public static final String GCM_REG_ON_SERVER = "GCM_REG_ON_SERVER";
        public static final String GCM_RID = "GCM_RID";
        public static final String USER_ID = "USER_ID";
    }
}
