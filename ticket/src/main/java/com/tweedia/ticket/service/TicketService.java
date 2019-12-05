package com.tweedia.ticket.service;

import com.tweedia.ticket.domain.Ticket;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.mail.MessagingException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-18
 */
public interface TicketService extends IService<Ticket> {
    List<Ticket> findTicket(String id, String username, String server, String questionType,
                            String operatorName, String status, Long pageNo, Long pageSize,
                            Long userId, Long operatorId);

    int updateTicket(String id, String reply);

    Long findTicketTotal(String id, String username, String server, String questionType,
                         String operatorName, String status, Long userId, Long operatorId);

    void send(String sender, String receiver, String username, String id, boolean flag) throws MessagingException;

    Ticket findOne(String id, long uid);

    boolean validateInput(String id) throws Exception;
}
