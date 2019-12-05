package com.tweedia.ticket.service;

import com.tweedia.ticket.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-16
 */
public interface UserService extends IService<User> {

    String login(String username, String password);

    boolean validateInput(Long operatorId) throws Exception;

    List<User> show(String name, String id,String admin, long pageNo, long pageSize);

    long total(String name, String id, String admin);

    List<User> allUserId();
}
