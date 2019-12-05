package com.tweedia.ticket.common;
/**
 * @Description: 常量
 * @Author: Tweedia
 * @Date: 2019/9/17
 */
public class Const {

    public static final long EXPIRATION_TIME = 432_000_000;     // 5天(以毫秒ms计)
    public static final String SECRET = "Q43BNV82V7";      // JWT密码

    public static final String TOKEN_PREFIX = "Bearer";         // Token前缀
    public static final String HEADER_STRING = "Authorization"; // 存放Token的Header Key
}
