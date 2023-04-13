package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.StatisticsMapper;
import cn.edu.hstc.sport_mall.pojo.Statistics;
import cn.edu.hstc.sport_mall.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-处理和后台统计相关的业务
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    /*
     * 更新访问量、日销售量、日收入
     */
    @Override
    public int update(Statistics statistics) {
        return statisticsMapper.update(statistics);
    }

    /*
     * 查询当前日期的统计记录
     */
    @Override
    public Statistics select() {
        return statisticsMapper.select();
    }

    /*
     * 按日统计
     */
    @Override
    public List<Statistics> selByDay(int year, int month) {
        return statisticsMapper.selByDay(year, month);
    }

    /*
     * 按月统计
     */
    @Override
    public List<Statistics> selByMonth(int year) {
        return statisticsMapper.selByMonth(year);
    }
}
