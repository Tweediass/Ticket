package com.tweedia.ticket.controller;

import com.tweedia.ticket.service.TicketService;
import com.tweedia.ticket.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "hello jwt !";
    }
    @GetMapping("/admin")
    public String admin() {
        return "hello admin !";
    }

    @Autowired
    UserServiceImpl userService;
//
//    @Autowired
//    TicketService ticketService;

    // 登录
    @RequestMapping(value = "/authentication/login", method = RequestMethod.POST)
    public Map<String,Object> createToken (String username,String password) throws AuthenticationException {
        Map<String, Object> map = new HashMap<>();
        // String userId = userDetail.get("username");
        // String password = userDetail.get("password");

        String token = userService.login(username, password);
        map.put("token", token);
        map.put("username", UserServiceImpl.username);
        map.put("auth", UserServiceImpl.auth);
        map.put("uid", UserServiceImpl.uid);
        return map;
    }
}
