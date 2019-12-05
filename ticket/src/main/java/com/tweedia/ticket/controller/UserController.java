package com.tweedia.ticket.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tweedia.ticket.common.Msg;
import com.tweedia.ticket.domain.User;
import com.tweedia.ticket.domain.UserRole;
import com.tweedia.ticket.service.UserRoleService;
import com.tweedia.ticket.service.UserService;
import com.tweedia.ticket.service.impl.UserServiceImpl;
import com.tweedia.ticket.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @PreAuthorize("hasAnyAuthority('admin','operator')")
    @GetMapping("/showUser")
    /**
     * @Description: 展示所有普通用户信息
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public Msg showUser(@RequestParam(defaultValue = "1") Long pageNo, String id) {
        List<User> list = userService.show("user", id, null, pageNo, 5L);
        long total = userService.total("user", id, null);
        return Msg.success().add("user", list).add("total", total);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/showOperator")
    /**
     * @Description: 展示所有操作员信息
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public Msg showOperator(@RequestParam(defaultValue = "1") long pageNo, String id) {

        String admin = "admin";

        List<User> list = userService.show("operator", id, admin, pageNo, 8L);
        long total = userService.total("operator", id, admin);
//        for (User user : list) {
//            if (user.getName() == null || user.getName().trim().equals("")) {
//                user.setName("未设置");
//            }
//        }
//        IPage<User> iPage=userService.page(new Page<User>(pageNo,5L),
//                new QueryWrapper<User>().eq(""))

        return Msg.success().add("operator", list).add("total", total);
    }


    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @Transactional
    @PostMapping("/addUser")
    @PreAuthorize("hasAnyAuthority('admin','operator')")
    /**
     * @Description:增加普通用户
     * @Author: Tweedia
     * @Date: 2019/9/25
     */
    public void addUser(User user) throws Exception {
        if (!(userService.save(user))) {
            throw new Exception("add user failed");
        } else {
            UserRole userRole = new UserRole();
            userRole.setRid(3L);
            long uid = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername())).getId();
            userRole.setUid(uid);
            if (!(userRoleService.save(userRole))) {
                throw new Exception("add user failed");
            }
        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @Transactional
    @PostMapping("/addOperator/{rid}")
    @PreAuthorize("hasAnyAuthority('admin')")
    /**
     * @Description: 增加操作员
     * @Author: Tweedia
     * @Date: 2019/9/25
     */
    public void addOperator(User operator, @PathVariable("rid") long rid) throws Exception {

        //新增用户（admin/operator/user）
        userService.save(operator);
//        if(rid==1&&UserServiceImpl.auth.equals("operator"))

        //赋予用户角色（权限）
        UserRole userRole = new UserRole();
        userRole.setRid(rid);
        long uid = userService.getOne(new QueryWrapper<User>().eq("username", operator.getUsername())).getId();
        userRole.setUid(uid);
        userRoleService.save(userRole);
//        if (!(userRoleService.save(userRole))) {
//            throw new Exception("add operator failed");
//        }

//        if (!(userService.save(operator))) {
//            throw new Exception("add operator failed");
//        } else {
//            UserRole userRole = new UserRole();
//            userRole.setRid(2L);
//            long uid = userService.getOne(new QueryWrapper<User>().eq("username", operator.getUsername())).getId();
//            userRole.setUid(uid);
//            if (!(userRoleService.save(userRole))) {
//                throw new Exception("add operator failed");
//            }
//        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
//    @Transactional
    @PutMapping("/deleteUser")
    @PreAuthorize("hasAnyAuthority('admin','operator')")
    /**
     * @Description: 删除普通用户
     * @Author: Tweedia
     * @Date: 2019/9/25
     */
    public void deleteUser(Long id) throws Exception {
        User user = new User();
        user.setEnable(false);

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.eq("id", id);

        if (!userService.update(user, updateWrapper)) {
            throw new Exception("delete user failed");
        }
//        else {
//            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("uid", id);
//            if (!(userRoleService.remove(queryWrapper))) {
//                throw new Exception("delete user failed");
//            }
//        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @DeleteMapping("/deleteOperator")
    @PreAuthorize("hasAnyAuthority('admin')")
    /**
     * @Description: 删除操作员
     * @Author: Tweedia
     * @Date: 2019/9/25
     */
    public void deleteOperator(Long id) throws Exception {
        if (!(userService.removeById(id))) {
            throw new Exception("delete operator failed");
        }
//        else {
//            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("uid", id);
//            if (!(userRoleService.remove(queryWrapper))) {
//                throw new Exception("delete operator failed");
//            }
//        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @Transactional
    @PutMapping("/updateUser/{rid}")
    @PreAuthorize("hasAnyAuthority('admin','operator')")
    /**
     * @Description: 修改普通用户信息
     * @Author: Tweedia
     * @Date: 2019/9/25
     */
    public void updateUser(User user, @PathVariable("rid") long rid) throws Exception {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        long uid = user.getId();
        updateWrapper.eq("id", uid);
//        if (!(userService.update(user, updateWrapper))) {
//            throw new Exception("update user failed");
//        }
        userService.update(user, updateWrapper);

        //判定是否修改用户角色，或者是是否拥有修改为此角色的权限
        if (rid == 3) {
            return;
        } else if (rid == 1 && UserServiceImpl.auth.equals("operator")) {
            throw new Exception("update user failed");
        }

        //修改值
        UserRole userRole = new UserRole();
        userRole.setRid(rid);

        //修改条件
        UpdateWrapper<UserRole> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("uid", uid);
        userRoleService.update(userRole, updateWrapper2);
//        else {
//            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("uid", id);
//            if (!(userRoleService.remove(queryWrapper))) {
//                throw new Exception("delete user failed");
//            }
//        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
//    @Transactional
    @PutMapping("/updateOperator")
    @PreAuthorize("hasAnyAuthority('admin')")
    /**
     * @Description: 修改操作员信息
     * @Author: Tweedia
     * @Date: 2019/9/25
     */
    public void updateOperator(User operator) throws Exception {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", operator.getId());

        if (!(userService.update(operator, updateWrapper))) {
            throw new Exception("update operator failed");
        }
//        else {
//            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("uid", id);
//            if (!(userRoleService.remove(queryWrapper))) {
//                throw new Exception("delete user failed");
//            }
//        }
    }

    @GetMapping("/initUser")
    /**
     * @Description: 返回当前用户的所有信息
     * @Author: Tweedia
     * @Date: 2019/11/16
     */
    public Msg initUser() {
        return Msg.success().add("user", UserUtils.getCurrentUser());
    }
}

