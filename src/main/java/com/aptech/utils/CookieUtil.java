package com.aptech.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CookieUtil {

    public static String getValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String getUserLogon(HttpServletRequest request) {
        String value =  getValue("wcd-token", request);
        if (value != null) {
            byte[] asBytes = Base64.getDecoder().decode(value);
            String userInfo = new String(asBytes, StandardCharsets.UTF_8);
            JSONObject object = new JSONObject(userInfo);
            return object.getString("name");
        }
        return "";
    }

    public static Cookie createCookie(String name, String value, int maxAge, String domain, boolean secure, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        cookie.setPath("/");
        if (!domain.equals("")) {
            cookie.setDomain(domain);
        }
        return cookie;
    }

    public static void clearCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
