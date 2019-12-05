package com.tweedia.ticket.mapper;

import com.tweedia.ticket.domain.Ticket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-18
 */
public interface TicketMapper extends BaseMapper<Ticket> {

    List<Ticket> findTicket(String id, String username, String server, String questionType,
                            String operatorName, String status, Long start, Long pageSize,
                            Long userId, Long operatorId);

    int updateTicket(String id, String reply);

    Long findTicketTotal(String id, String username, String server, String questionType,
                         String operatorName, String status, Long userId, Long operatorId);

    Ticket findOne(String id, long uid);

    @Select("select count(*) from ticket where id=#{id}")
    int validateInput(String id);
}
