package com.ilya.restapiapp.util;

import com.google.gson.Gson;
import com.ilya.restapiapp.model.Event;

public class GsonUtils {

    private static Gson gson = new Gson();

    public static String toJSON(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String bodyRequest, Class<T> tClass) {
        return gson.fromJson(bodyRequest, tClass);
    }

}
