package com.example.christoforos.myapplication.backend.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by christoforos on 12/12/2016.
 */

public class JsonParser {

    public static String getString(JSONObject jsonObject, String key){
        String value = null;
        if(jsonObject == null){
            return null;
        }
        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }
}
