package com.example.demo.config;

public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> dynamicDataSourceHolder = new ThreadLocal<>();

    public static void setKey(String key) {
        dynamicDataSourceHolder.set(key);
    }

    public static String getKey() {
       String key = dynamicDataSourceHolder.get();
       return key == null ? "master" : key;
    }

    public static void RemoveKey() {
        dynamicDataSourceHolder.remove();
    }
}
