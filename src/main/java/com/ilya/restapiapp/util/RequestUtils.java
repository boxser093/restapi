package com.ilya.restapiapp.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class RequestUtils {
    public static String getBody(HttpServletRequest req) throws IOException {
        BufferedReader br = req.getReader();
        String str, result = "";
        while ((str = br.readLine()) != null) {
            result += str;
        }
        return result;
    }

}
