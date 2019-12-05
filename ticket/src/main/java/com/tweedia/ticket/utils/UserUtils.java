package com.tweedia.ticket.utils;

import com.tweedia.ticket.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * @Description: 获取当前登录用户的信息
 * @Author: Tweedia
 * @Date: 2019/9/20
 */
public class UserUtils {
    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
