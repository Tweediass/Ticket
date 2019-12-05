package com.tweedia.ticket.mapper;

import com.tweedia.ticket.domain.Statistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-24
 */
public interface StatisticsMapper extends BaseMapper<Statistics> {
    List<Statistics> initCount();

    List<Statistics> initCounted();

    List<Statistics> initTable();
}
