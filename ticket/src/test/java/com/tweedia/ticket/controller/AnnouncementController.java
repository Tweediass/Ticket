package com.tweedia.ticket.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tweedia.ticket.common.Msg;
import com.tweedia.ticket.domain.Announcement;
import com.tweedia.ticket.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Tweedia
 * @since 2019-10-15
 */
@RestController
@RequestMapping("/announcement")
@PreAuthorize("hasAnyAuthority('admin','user','operator')")
public class AnnouncementController {
//    @Autowired
//    AnnouncementService announcementService;
//
//    @PreAuthorize("hasAnyAuthority('admin','operator')")
//    @GetMapping("/show")
//    /**
//     * @Description: 工单受理的公告展示
//     * @Author: Tweedia
//     * @Date: 2019/9/23
//     */
//    public Msg show(String createDate,
//                    @RequestParam(defaultValue = "1") Long pageNo,
//                    @RequestParam(defaultValue = "5") Long pageSize) {
//        List<Announcement> list = announcementService.findAnnouncement(createDate, pageNo, pageSize);
//
//        int total = announcementService.count(new QueryWrapper<Announcement>().le("createDate", createDate));
//        return Msg.success().add("announcement", list).add("total", total);
//    }
}

