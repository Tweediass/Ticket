package com.tweedia.ticket.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tweedia.ticket.common.Msg;
import com.tweedia.ticket.domain.Statistics;
import com.tweedia.ticket.domain.Ticket;
import com.tweedia.ticket.domain.User;
import com.tweedia.ticket.service.StatisticsService;
import com.tweedia.ticket.service.TicketService;
import com.tweedia.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/statistics")
@PreAuthorize("hasAnyAuthority('admin')")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Transactional
    @GetMapping("/init")
    /**
     * @Description: 查询并初始化各个工作人员的工单情况
     * @Author: Tweedia
     * @Date: 2019/9/26
     */
    public Msg init(@RequestParam(defaultValue = "1") long pageNo) {
        //初始化statistics表的用户信息
        List<Statistics> list0 = statisticsService.initTable();
        for (Statistics s : list0) {
            UpdateWrapper<Statistics> updateWrapper = new UpdateWrapper<>();
            long operatorId = s.getOperatorId();
            updateWrapper.eq("operatorId",operatorId);
            if (s.getOperatorName() == null || s.getOperatorName().trim().equals("")) {
                s.setOperatorName("未命用户" + operatorId);
            }
            if (!statisticsService.update(s, updateWrapper))
                statisticsService.save(s);
        }

        List<Statistics> list1 = statisticsService.initCount();
        List<Statistics> list2 = statisticsService.initCounted();
        for (Statistics s : list1) {
            UpdateWrapper<Statistics> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("operatorId", s.getOperatorId());
            if (!statisticsService.update(s, updateWrapper))
                statisticsService.save(s);
        }
        for (Statistics s : list2) {
            UpdateWrapper<Statistics> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("operatorId", s.getOperatorId());
            if (!statisticsService.update(s, updateWrapper))
                statisticsService.save(s);
        }

        //分页查找
        IPage<Statistics> iPage = statisticsService.page(new Page<Statistics>(pageNo, 5L),
                new QueryWrapper<Statistics>().orderByDesc("countProcessed"));

        List<Statistics> list = iPage.getRecords();
        long total = iPage.getTotal();
        return Msg.success().add("statistics", list).add("total", total);
    }

    @GetMapping("/initAll")
    /**
     * @Description: 统计工单处理情况
     * @Author: Tweedia
     * @Date: 2019/9/26
     */
    public Msg initAll() {
        int New = ticketService.count(new QueryWrapper<Ticket>().eq("status", "New"));//2
        int Processing = ticketService.count(new QueryWrapper<Ticket>().eq("status", "Processing"));//4
        int Assigned = ticketService.count(new QueryWrapper<Ticket>().eq("status", "Assigned"));//1
        int Closed = ticketService.count(new QueryWrapper<Ticket>().eq("status", "Closed"));//3
        return Msg.success().add("New", New).add("Processing", Processing).add("Assigned", Assigned).add("Closed", Closed);
    }
}

