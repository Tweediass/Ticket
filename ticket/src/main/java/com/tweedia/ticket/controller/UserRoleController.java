package com.tweedia.ticket.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tweedia.ticket.domain.UserRole;
import com.tweedia.ticket.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-25
 */
@RestController
@RequestMapping("/userRole")
@PreAuthorize("hasAnyAuthority('admin','user','operator')")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PutMapping("/updateRole")
    @PreAuthorize("hasAnyAuthority('admin')")
    public void updateRole(long uid, long rid) throws Exception {
        //修改值
        UserRole userRole = new UserRole();
        userRole.setRid(rid);

        //修改条件
        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid);

        if (!userRoleService.update(userRole, updateWrapper)) {
            throw new Exception("Update role fail");
        }
    }
}

