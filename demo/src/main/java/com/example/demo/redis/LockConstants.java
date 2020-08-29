package com.example.demo.redis;

public class LockConstants {
    public static final String OK = "OK";

    // key不存在是设置
    public static final String NOT_EXIST = "NX";
    // key存在时设置
    public static final String EXIST = "XX";

    // 过期时间单位
    public static final String SECONDS = "EX";
    public static final String MILLISECONDS = "PX";
}
