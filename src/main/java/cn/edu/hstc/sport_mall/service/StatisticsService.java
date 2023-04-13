package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Statistics;

import java.util.List;

/**
 * Service层-处理和后台统计相关的业务
 */
public interface StatisticsService {

    /*
     * 更新访问量、日销售量、日收入
     */
    int update(Statistics statistics);

    /*
     * 查询当前日期的统计记录
     */
    Statistics select();

    /*
     * 按日统计
     */
    List<Statistics> selByDay(int year, int month);

    /*
     * 按月统计
     */
    List<Statistics> selByMonth(int year);
}
