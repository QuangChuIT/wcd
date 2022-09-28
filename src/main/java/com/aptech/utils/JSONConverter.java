package com.aptech.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class JSONConverter {
    public static String readJson(HttpServletRequest request) {
        String data = "";
        String line = "";
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            data = builder.toString();
        } catch (Exception e) {
            LOGGER.error("Error read json from request", e);
        }
        return data;
    }

    private final static Logger LOGGER = LogManager.getLogger(JSONConverter.class);
}
