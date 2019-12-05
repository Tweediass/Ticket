package com.tweedia.ticket.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tweedia.ticket.common.Msg;
import com.tweedia.ticket.domain.Announcement;
import com.tweedia.ticket.domain.Ticket;
import com.tweedia.ticket.domain.User;
import com.tweedia.ticket.service.AnnouncementService;
import com.tweedia.ticket.service.TicketService;
import com.tweedia.ticket.service.UserService;
import com.tweedia.ticket.service.impl.UserServiceImpl;
import com.tweedia.ticket.utils.PoiTicketsUtils;
import com.tweedia.ticket.utils.UserUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-18
 */
@RestController
@RequestMapping("/ticket")
@PreAuthorize("hasAnyAuthority('admin','user','operator')")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    AnnouncementService announcementService;

    @GetMapping("/findOne")
    /**
     * @Description: 查找一个工单
     * @Author: Tweedia
     * @Date: 2019/9/18
     */
    public Msg findOne(String id) throws Exception {
//        Ticket ticket = ticketService.getById(id);
//        List<Ticket> list = findTicketByKey(id,null,null,null,null,null,1,1,null,null);
//        return findTicketByKey(id, null, null, null, null, null, 1, 1, null, null)
//////                Msg.success().add("ticket", ticket)
////                ;
        if (!ticketService.validateInput(id)) {
            return Msg.fail("ticketId did not exist");
        }
        long uid = 0;
        if (UserServiceImpl.auth.trim().equals("user")) {
            uid = UserUtils.getCurrentUser().getId();
        }
        Ticket ticket = ticketService.findOne(id, uid);
        if (ticket == null) {
            return Msg.fail("ticket did not exist");
        }
        if (ticket.getTkId() == null || ticket.getTkId().trim().equals("")) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime time = ticket.getCreateDate();
            String tkId = "TK" + df.format(time) + (1000 + ticket.getId());
            ticket.setTkId(tkId);
            ticketService.updateById(ticket);
        }
        return Msg.success().add("ticket", ticket);
    }

    @GetMapping("/find")
    /**
     * @Description: 查找工单
     * @Author: Tweedia
     * @Date: 2019/9/18
     */
    public Msg findTicketByKey(String id, String username, String server, String questionType, String operatorName, String status,
                               @RequestParam(defaultValue = "1") long pageNo,
                               @RequestParam(defaultValue = "15") long pageSize,
                               @RequestParam(defaultValue = "") Long userId,
                               @RequestParam(defaultValue = "") Long operatorId) {
//        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
//        queryWrapper
//                .like("id", id)
//                .like("userId", userId)
//                .like("server", server)
//                .like("questionType", questionType)
//                .like("status", status)
////                .orderByDesc("createDate")
//        ;
//        if (!(operatorId.trim().equals(""))) {
//            queryWrapper.like("operatorId", operatorId);
//        }
//        Page<Ticket> page = new Page<>(pageNo, pageSize);
//        IPage<Ticket> iPage = ticketService.page(page, queryWrapper);
//        List<Ticket> list = ticketService.list(queryWrapper);

//        Long uid= UserUtils.getCurrentUser().getId();
//        System.out.println(uid);
        List<Ticket> list = ticketService.findTicket(id, username, server, questionType,
                operatorName, status, pageNo, pageSize, userId, operatorId);
        Long total = ticketService.findTicketTotal(id, username, server, questionType, operatorName, status, userId, operatorId);

        for (Ticket ticket : list) {
//            operatorName = "";
            ;
            if (ticket.getOperatorName() == null || ticket.getOperatorName().trim().equals("")) {
                ticket.setOperatorName("暂无");
            }
            if (ticket.getTkId() == null || ticket.getTkId().trim().equals("")) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime time = ticket.getCreateDate();
                String tkId = "TK" + df.format(time) + (1000 + ticket.getId());
                ticket.setTkId(tkId);
                ticketService.updateById(ticket);
            }
        }
        return Msg.success().add("ticket", list).add("total", total);
    }


    @RequestMapping(value = "/export", method = RequestMethod.GET)
    /**
     * @Description: 导出工单信息至Excel
     * @Author: Tweedia
     * @Date: 2019/11/22
     */
    public ResponseEntity<byte[]> export(String tkId, String username, String server, String questionType, String operatorName, String status, String allTk,
                                         @RequestParam(defaultValue = "1") long pageNo,
                                         @RequestParam(defaultValue = "15") long pageSize) throws IOException {
//        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<Ticket>();
        System.out.println("look!");
        if (allTk.equals("1")) {
            pageNo = 1L;
            pageSize = Long.MAX_VALUE;
        }
        List<Ticket> list = ticketService.findTicket(tkId, username, server, questionType,
                operatorName, status, pageNo, pageSize, null, null);
        return PoiTicketsUtils.exportTkExcel(list);
//        PoiTicketsUtils.testExport();
    }

    @GetMapping("/findNew")
    /**
     * @Description: 查找待分配的工单
     * @Author: Tweedia
     * @Date: 2019/9/18
     */
    public Msg findNew(String id, String username, String server, String questionType,
                       @RequestParam(defaultValue = "1") long pageNo,
                       @RequestParam(defaultValue = "15") long pageSize) {
        return findTicketByKey(id, username, server, questionType,
                null, "New", pageNo, pageSize, null, null);
    }

    @GetMapping("/findSelf")
    /**
     * @Description: 查找我的工单，包括本人发布的与本人正在处理的
     * @Author: Tweedia
     * @Date: 2019/9/20
     */
    public Msg findSelf(String id, String username, String server, String questionType, String operatorName, String status,
                        @RequestParam(defaultValue = "1") long pageNo,
                        @RequestParam(defaultValue = "15") long pageSize) {
        Long selfId = UserUtils.getCurrentUser().getId();
        return findTicketByKey(id, username, server, questionType,
                operatorName, status, pageNo, pageSize, selfId, selfId);
    }

    @Transactional
    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PostMapping("/create")
    /**
     * @Description: 发布工单
     * @Author: Tweedia
     * @Date: 2019/9/19
     */
    public Msg addTicket(Ticket ticket) throws Exception {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        if (ticket.getTitle().trim() != null && ticket.getTitle().trim() != "" && ticket.getDescription().trim() != null && ticket.getDescription().trim() != "")
        LocalDateTime createDate = LocalDateTime.now();
        ticket.setCreateDate(createDate);
        ticket.setUserId(UserUtils.getCurrentUser().getId());
        ticket.setStatus("New");

//        ticket.setReply("");
//        else {
//            throw new Exception("add ticket failed");
//        }
        if (!(ticketService.save(ticket))) {
            return Msg.fail("add ticket failed");
        }
        return Msg.success();
//        Ticket ticket1 = new Ticket();
//        ticket1
    }

//    @Transactional
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
//    @PutMapping("/update")
//    /**
//     * @Description: 修改工单信息，包括工单状态，负责人，回复
//     * @Author: Tweedia
//     * @Date: 2019/9/20
//     */
//    public void updateTicket(String id, String status, String operatorId, String reply) throws Exception {
//        boolean flag = false;
//        if (operatorId != null && operatorId.trim().equals("")) {
//            userService.validateInput(operatorId);
////            if (status.equals("Processing")) {
////                flag = true;
////            }
//        }
//        if (ticketService.updateTicket(id, status, operatorId, reply) != 1) {
//            throw new Exception("update ticket failed");
//        } else if (status.equals("Processing")) {
//            //修改值
//            Announcement announcement = new Announcement();
//            Long selfId = UserUtils.getCurrentUser().getId();
//            announcement.setOperatorId(selfId);
//            announcement.setTicketId(Long.parseLong(id));
//            announcement.setCreateDate(LocalDateTime.now());
////            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
////            df.format(new Date())
//
////            //设置条件
////            UpdateWrapper<Announcement> updateWrapper = new UpdateWrapper<>();
////            updateWrapper
//            if (!(announcementService.save(announcement))) {
//                throw new Exception("update announcement failed");
//            }
//        }
//    }

    @PreAuthorize("hasAnyAuthority('admin','operator')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PutMapping("/updateClosed")
    /**
     * @Description: 修改工单状态为关闭，关闭工单
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public void updateClosed(String tkId, Long tid) throws Exception {
        //修改值
        Ticket ticket = new Ticket();
        ticket.setStatus("Closed");

        //设置条件
        UpdateWrapper<Ticket> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("tkId", tkId);
        if (!(ticketService.update(ticket, updateWrapper))) {
            throw new Exception("closed ticket failed");
        }

        //修改值
        Long selfId = UserUtils.getCurrentUser().getId();
        Announcement announcement = new Announcement();
        announcement.setPubId(selfId);
        announcement.setTitle("工单关闭公告");
        announcement.setMessage("工单号为 " + tkId + " 的工单已被关闭,请注意,谢谢。");
        announcement.setCreateDate(LocalDateTime.now());
        announcement.setTid(tid);
        //设置条件
        if (!(announcementService.save(announcement))) {
            throw new Exception("update announcement failed");
        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PutMapping("/updateAssigned")
    /**
     * @Description: 修改工单状态为已解决，解决工单
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public void updateAssigned(String tkId, Long tid) throws Exception {
        //修改值
        Ticket ticket = new Ticket();
        ticket.setStatus("Assigned");
        ticket.setEvaluation("满意(默认)");

        //设置条件
        UpdateWrapper<Ticket> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("tkId", tkId);
        if (!(ticketService.update(ticket, updateWrapper))) {
            throw new Exception("Assigned ticket failed");
        }

        //修改值
        Long selfId = UserUtils.getCurrentUser().getId();
        Announcement announcement = new Announcement();
        announcement.setPubId(selfId);
        announcement.setTitle("工单解决公告");
        announcement.setMessage("工单号为 " + tkId + " 的工单已被解决,请对此次服务进行评价,谢谢。");
        announcement.setCreateDate(LocalDateTime.now());
        announcement.setTid(tid);
        //设置条件
        if (!(announcementService.save(announcement))) {
            throw new Exception("update announcement failed");
        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PutMapping("/updateReply")
    /**
     * @Description: 提交工单的回复情况
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public void updateReply(String id, String reply) throws Exception {
        if (ticketService.updateTicket(id, reply) != 1) {
            throw new Exception("Update ticket failed");
        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PutMapping("/updateScore")
    /**
     * @Description: 提交工单的评分情况
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public Msg updateScore(String tkId, String evaluation, String opinion) throws Exception {
        //修改值
        Ticket ticket = new Ticket();
        ticket.setEvaluation(evaluation);
        ticket.setOpinion(opinion);

        //设置条件
        UpdateWrapper<Ticket> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", tkId);
        if (!(ticketService.update(ticket, updateWrapper))) {
            return Msg.fail("evaluate ticket failed");
//            throw new Exception();
        }
        return Msg.success();
    }


    //    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @Transactional
    @PutMapping("/updateProcessing")
    /**
     * @Description: 开始处理工单
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public Msg updateProcessing(String tkId) throws Exception {

        Long selfId = UserUtils.getCurrentUser().getId();//获取操作员的用户id

        //为方便test，将其注释
//        Ticket ticket0 = ticketService.getOne(new QueryWrapper<Ticket>().eq("id", id));
//        if (selfId.equals(ticket0.getUserId())) {
//            return Msg.fail("发布人与受理人不能为同一人");
//        }

        //修改值
        Ticket ticket = new Ticket();

        ticket.setStatus("Processing");
        ticket.setOperatorId(selfId);

        //设置条件
        UpdateWrapper<Ticket> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("tkId", tkId);
//        try {
        if (!(ticketService.update(ticket, updateWrapper))) {
            return Msg.fail("处理工单失败");
//                throw new Exception("处理工单失败");
        }
//        }
//        catch (Exception ex) {
//            System.out.println(ex);
//            return Msg.fail("处理工单失败");
//        }

        //获取发布人的id
        Ticket ticket1 = ticketService.getOne(new QueryWrapper<Ticket>().eq("tkId", tkId));
        long userId = ticket1.getUserId();

        //修改值
        Announcement announcement = new Announcement();

        announcement.setPubId(selfId);
        announcement.setTitle("工单处理公告");
        announcement.setMessage("工单号为 " + tkId + " 的工单已由操作员 " + selfId + " 处理。");
        announcement.setTid(userId);
        announcement.setCreateDate(LocalDateTime.now());
//            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
//            df.format(new Date())

//        try {
        if (!(announcementService.save(announcement))) {
//            System.out.println(announcementService.save(announcement));
//                throw new Exception("公告同步异常");
            return Msg.fail("公告同步失败，处理工单失败");
//            throw new Exception("update announcement failed");
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        catch (Exception ex) {
//            System.out.println(ex);
//            return Msg.fail("公告同步异常，处理工单失败");
//        }
//            //设置条件
//            UpdateWrapper<Announcement> updateWrapper = new UpdateWrapper<>();
//            updateWrapper
//        if (!(announcementService.save(announcement))) {
//            System.out.println(announcementService.save(announcement));
//            return Msg.fail("公告同步异常，处理工单失败");
////            throw new Exception("update announcement failed");
//        }
        return Msg.success();
    }

    @PreAuthorize("hasAnyAuthority('admin','operator')")
    @Transactional
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PutMapping("/updateOperator")
    /**
     * @Description: 修改工单负责人，流转工单
     * @Author: Tweedia
     * @Date: 2019/9/21
     */
    public Msg updateOperator(String tkId, Long operatorId, Long tid) throws Exception {

        Long selfId = UserUtils.getCurrentUser().getId();//获取操作员的用户id
        if (userService.validateInput(operatorId)) {
            return Msg.fail("请填写正确且有效的操作员编号");
//            throw new Exception("update ticket failed");
        }

        //修改值
        Ticket ticket = new Ticket();
        ticket.setOperatorId(operatorId);

        //设置条件
        UpdateWrapper<Ticket> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("tkId", tkId);
        if (!(ticketService.update(ticket, updateWrapper))) {
            throw new Exception("Update ticket failed");
        }

        //修改值
        Announcement announcement = new Announcement();
        announcement.setPubId(selfId);
        announcement.setTitle("工单转派公告");
        announcement.setMessage("工单号为 " + tkId + " 的工单已由操作员 " + selfId + " 转派给操作员 " + operatorId + " 处理。");
        announcement.setCreateDate(LocalDateTime.now());
        announcement.setTid(tid);
        //设置条件
        if (!(announcementService.save(announcement))) {
            throw new Exception("update announcement failed");
        }
        return Msg.success();
    }

    @Transactional
    @ResponseStatus(value = HttpStatus.NO_CONTENT)//204
    @PostMapping("/send")
//    @PreAuthorize("hasAnyAuthority('admin','operator')")
    /**
     * @Description: 发出邮箱的消息推送通知
     * @Author: Tweedia
     * @Date: 2019/9/26
     */
    public void send(String customerName, Long customerId, String customerEmail, String tkId,
                     String operatorName, Long operatorId) throws Exception {
//        System.out.println("look");
        String sender = userService.getById(UserUtils.getCurrentUser().getId()).getEmail();//获取发信人的邮箱
        String receiver, name;
        boolean flag = true;
        if (UserServiceImpl.auth.equals("user")) {
            receiver = userService.getById(operatorId).getEmail();
            name = operatorName;
            flag = false;
        } else {
            receiver = customerEmail;
            name = customerName;
        }
        System.out.println(sender + "**" + receiver + "**" + name + "**" + tkId + "**" + flag);
        ticketService.send(sender, receiver, name, tkId, flag);

        //创建公告
        Announcement announcement = new Announcement();
        announcement.setPubId(UserUtils.getCurrentUser().getId());//获取当前用户id
        announcement.setTitle("消息推送公告");
        if (flag) {
            announcement.setMessage("亲爱的" + name + "用户，工单号为" + tkId + "的工单正在受理中，请注意负责人的回复。");
            announcement.setTid(customerId);
        } else {
            announcement.setMessage("尊敬的负责人：" + name + "，工单号为" + tkId + "的工单已被回复，请注意尽快查看并处理。");
            announcement.setTid(operatorId);
        }
        announcement.setCreateDate(LocalDateTime.now());

        if (!(announcementService.save(announcement))) {
            throw new Exception("update announcement failed");
        }
    }

}