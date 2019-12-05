package com.tweedia.ticket.service;

import com.tweedia.ticket.domain.Statistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-24
 */
public interface StatisticsService extends IService<Statistics> {
    List<Statistics> initCount();

    List<Statistics> initCounted();

    List<Statistics> initTable();
}
