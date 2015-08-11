package com.univtop.univtop.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by duncapham on 8/9/15.
 */
public class JsonUtil {
    public static JSONObject getJsonObject(String keyWord, JSONObject obj) {
        try {
            JSONObject newObj = obj.getJSONObject(keyWord);
            return newObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(String keyWord, JSONObject obj) {
        try {
            String str = obj.getString(keyWord);
            return str;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getJsonArray(String keyWord, JSONObject obj) {
        try {
            JSONArray arrObj = obj.getJSONArray(keyWord);
            return arrObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
