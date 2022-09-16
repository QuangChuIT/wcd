package com.aptech.utils;

import java.io.InputStream;
import java.util.Properties;

public class SystemConfig {
    private static SystemConfig INSTANCE;

    private SystemConfig() {

    }

    public static SystemConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SystemConfig();
        }
        return INSTANCE;
    }

    public String getProperty(String key) {
        Properties properties = new Properties();
        try {
            InputStream is = this.getClass().getResourceAsStream("/application.properties");
            properties.load(is);
            return properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(SystemConfig.getInstance().getProperty("DB_URL"));
    }

}
