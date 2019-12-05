package com.tweedia.ticket.mapper;

import com.tweedia.ticket.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-16
 */
public interface UserMapper extends BaseMapper<User> {
    User findUserAndRoleByUsername(String username);

//    @Select("select count(*) from user where id=#{operatorId} and ")
    int validateInput(Long operatorId);

    List<User> show(String name, String id, String admin, long start, long pageSize);

    long total(String name, String id, String admin);

    List<User> allUserId();
}
