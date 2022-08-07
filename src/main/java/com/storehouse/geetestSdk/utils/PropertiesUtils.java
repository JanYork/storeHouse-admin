package com.storehouse.geetestSdk.utils;

import java.util.Properties;

public class PropertiesUtils {
    private static final String CONFIG_FILE_NAME = "GeetestConfig.properties";

    private static Properties props;

    static {
        props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key.trim()).trim();
    }

    public static String get(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        if (value == null || value.isEmpty()) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static void set(String key, String value) {
        props.setProperty(key.trim(), value.trim());
    }

}
