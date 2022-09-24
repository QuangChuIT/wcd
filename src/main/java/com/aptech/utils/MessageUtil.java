package com.aptech.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil {
    private static ResourceBundle bundle;

    private static void init() {
        Locale locale = new Locale("vi", "VN");
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static String getProperty(String key) {
        init();
        return bundle.getString(key);
    }

    public static String getProperty(String key, Object... args) {
        init();
        return String.format(bundle.getString(key), args);
    }

}
