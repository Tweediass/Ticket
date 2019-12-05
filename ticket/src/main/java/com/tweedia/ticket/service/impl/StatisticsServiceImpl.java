package com.tweedia.ticket.service.impl;

import com.tweedia.ticket.domain.Statistics;
import com.tweedia.ticket.mapper.StatisticsMapper;
import com.tweedia.ticket.service.StatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-24
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {

    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public List<Statistics> initCount() {
        return statisticsMapper.initCount();
    }

    @Override
    public List<Statistics> initCounted() {
        return statisticsMapper.initCounted();
    }

    @Override
    public List<Statistics> initTable() {
        return statisticsMapper.initTable();
    }
}
