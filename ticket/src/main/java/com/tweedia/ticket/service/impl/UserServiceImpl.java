package com.tweedia.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tweedia.ticket.domain.Role;
import com.tweedia.ticket.domain.User;
import com.tweedia.ticket.mapper.UserMapper;
import com.tweedia.ticket.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tweedia.ticket.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static String auth;
    public static String username;
    public static long uid;
//    public static boolean enable;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.findUserAndRoleByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        auth = user.getRoles().get(0).getName();
        username = user.getUsername();
        uid = user.getId();
//        enable = user.getEnable();
//        System.out.println("*" + user.getEnable() + "*");
        return user;
    }

    // 登录
    @Override
    public String login(String username, String password) {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public boolean validateInput(Long operatorId) throws Exception {
        int i = userMapper.validateInput(operatorId);
        if (i == 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> show(String name, String id, String admin, long pageNo, long pageSize) {
        long start = (pageNo - 1) * pageSize;
        return userMapper.show(name, id, admin, start, pageSize);
    }

    @Override
    public long total(String name, String id, String admin) {
        return userMapper.total(name, id, admin);
    }

    @Override
    public List<User> allUserId() {
        return userMapper.allUserId();
    }
}
