package com.tweedia.ticket.service.impl;

import com.tweedia.ticket.domain.Ticket;
import com.tweedia.ticket.mapper.TicketMapper;
import com.tweedia.ticket.service.TicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tweedia.ticket.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.tweedia.ticket.service.impl.UserServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-18
 */
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    JavaMailSender jms;

    @Override
    public List<Ticket> findTicket(String id, String username, String server, String questionType,
                                   String operatorName, String status, Long pageNo, Long pageSize,
                                   Long userId, Long operatorId) {
        Long start = (pageNo - 1) * pageSize;
        return ticketMapper.findTicket(id, username, server, questionType, operatorName, status, start, pageSize, userId, operatorId);
    }

    @Override
    public int updateTicket(String id, String reply) {
        if (reply != null && !(reply.equals(""))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
            String name = "";
            System.out.println("here++" + UserServiceImpl.auth + "*****\n" + UserServiceImpl.username);
            if (UserServiceImpl.auth.trim().equals("user")) {
                name = "(用户)" + UserServiceImpl.username;
            } else if (UserServiceImpl.auth.trim().equals("admin")) {
                name = "(管理员)" + UserUtils.getCurrentUser().getName();
            } else {
                name = "(操作员)" + UserUtils.getCurrentUser().getName();
            }
            reply = df.format(new Date()) + "\r\n" + name + ": " + reply + "\r\n\r\n";
        }
        return ticketMapper.updateTicket(id, reply);
    }

    @Override
    public Long findTicketTotal(String id, String username, String server, String questionType,
                                String operatorName, String status, Long userId, Long operatorId) {
        return ticketMapper.findTicketTotal(id, username, server, questionType, operatorName, status, userId, operatorId);
    }

    @Override
    public void send(String sender, String receiver, String name, String id, boolean flag) throws MessagingException {
        //实现类不会从配置文件里面读取信息，需要自己设置
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();//直接生产一个实例
//        String users[] = {"xxxxx@qq.com","xxxxx@126.com"};
//        mailSender.setHost("smtp.qq.com");
//        mailSender.setPassword("xikcjwbybrcnbedc");
//        mailSender.setPort(25);
//        mailSender.setProtocol("smtp");
//        mailSender.setUsername("553907370@qq.com");


//        String sender=
////                "553907370@qq.com"
//                UserUtils.getCurrentUser().getEmail()
//                ;   //这个是发送人的邮箱
//        String receiver="3211201715@qq.com";  //这个是接受人的邮箱
        String title = "通知";    //标题
//        String text="<h3>xxx教练：</h3><br/><p>你有</p>";
//        String users[]={"3211201715@qq.com","364851563@qq.com"};

        MimeMessage mimeMessage = jms.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setSubject(title);
        messageHelper.setFrom(sender);
        messageHelper.setTo(receiver);
        if (flag) {
            messageHelper.setText("<h3>亲爱的" + name + "用户：</h3><br/><p>工单号为" + id + "的工单正在受理中，请注意负责人的回复。</p>", true);
        } else {
            messageHelper.setText("<h3>尊敬的负责人：" + name + "</h3><br/><p>工单号为" + id + "的工单已被回复，请注意尽快查看并处理。</p>", true);
        }
        jms.send(messageHelper.getMimeMessage());
        //return "1";
    }

    @Override
    public Ticket findOne(String id, long uid) {
        return ticketMapper.findOne(id, uid);
    }

    @Override
    public boolean validateInput(String id) throws Exception {
        int i = ticketMapper.validateInput(id);
        if (i == 0) {
            return false;
        }
        return true;
    }
}
