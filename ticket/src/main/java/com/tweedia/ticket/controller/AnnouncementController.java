package com.tweedia.ticket.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tweedia.ticket.common.Msg;
import com.tweedia.ticket.domain.Announcement;
import com.tweedia.ticket.domain.User;
import com.tweedia.ticket.service.AnnouncementService;
import com.tweedia.ticket.service.UserService;
import com.tweedia.ticket.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tweedia
 * @since 2019-10-15
 */
@RestController
@RequestMapping("/announcement")
@PreAuthorize("hasAnyAuthority('admin','user','operator')")
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementService;

    @Autowired
    UserService userService;

//    @PreAuthorize("hasAnyAuthority('admin','operator')")
    @GetMapping("/announcement")
    /**
     * @Description: 工单受理的公告展示
     * @Author: Tweedia
     * @Date: 2019/9/23
     */
    public Msg show(String date, @RequestParam(defaultValue = "1") long pageNo,
                    @RequestParam(defaultValue = "20") long pageSize) {

        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", UserUtils.getCurrentUser().getId());
        if (date != null && !date.equals("")) {
            queryWrapper.le("createDate", date);
        }

        //分页查找
        IPage<Announcement> iPage = announcementService.page(new Page<Announcement>(pageNo, pageSize),
                queryWrapper.orderByDesc("createDate"));

        List<Announcement> list = iPage.getRecords();
        long total = iPage.getTotal();
        return Msg.success().add("announcement", list).add("total", total);
    }

    @GetMapping("/check")
    /**
     * @Description: 获取是否有新的消息通知
     * @Author: Tweedia
     * @Date: 2019/9/23
     */
    public Msg check() {
        boolean flag = false;

        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", UserUtils.getCurrentUser().getId());

        List<Announcement> list = announcementService.list(queryWrapper);
        for (Announcement an : list) {
            if (an.getState()) {
                flag = true;
                break;
            }
        }
        return Msg.success().add("flag", flag);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/announcement")
    @Transactional
    /**
     * @Description: 发布系统通知
     * @Author: Tweedia
     * @Date: 2019/10/18
     */
    public void add(String title, String message,String toWho) {

        //创建新通知
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setMessage(message);
        announcement.setPubId(UserUtils.getCurrentUser().getId());
        announcement.setCreateDate(LocalDateTime.now());

        List<User> allId;

        if (toWho.equals("1")) {
            allId = userService.allUserId();//获取所有工作人员的信息
        } else {
            allId = userService.list(null);//获取所有人员（包括普通人员）的信息
        }

        for (User user:allId) {
            announcement.setTid(user.getId());
            announcementService.save(announcement);
        }
    }

    @PutMapping("/announcement")
    /**
     * @Description: 提示信息改为已阅状态
     * @Author: Tweedia
     * @Date: 2019/10/28
     */
    public Msg update(long id) {
        //修改值
        Announcement announcement = new Announcement();
        announcement.setState(false);

        UpdateWrapper<Announcement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);

//        System.out.println("look" + announcement);

        if (!announcementService.update(announcement, updateWrapper)) {
            return Msg.fail();
        }
        return Msg.success();
    }
}

