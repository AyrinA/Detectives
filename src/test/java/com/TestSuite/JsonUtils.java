package com.TestSuite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static JSONArray getJSONArray(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getJSONArray(key);
        } catch (JSONException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int getInt(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getInt(key);
        } catch (JSONException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int getArrayLength(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has(key)) {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                return jsonArray.length();
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static boolean getBooleanSuccess(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getBoolean(key);
        } catch (JSONException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}




