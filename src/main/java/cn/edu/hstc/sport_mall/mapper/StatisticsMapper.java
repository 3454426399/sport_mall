package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Statistics;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper层-处理后台统计相关事务
 */
public interface StatisticsMapper {

    /*
     * 更新后台统计
     */
    int update(Statistics statistics);

    /*
     * 查询最后一条，也就是当前日期的统计记录
     */
    @Select("SELECT * FROM statistics ORDER BY id DESC LIMIT 1")
    Statistics select();

    /*
     * 后台统计 - 按日统计
     */
    @Select("SELECT `day`, visit, sale, income FROM statistics where `year` = #{year} and `month` = #{month} ORDER BY id ASC")
    List<Statistics> selByDay(@Param("year") int year, @Param("month") int month);

    /*
     * 后台统计 - 按月统计
     */
    @Select("SELECT `month`, SUM(visit) AS visit, SUM(sale) AS sale, SUM(income) AS income FROM statistics where `year` = #{year} GROUP BY `month` ORDER BY `month` ASC")
    List<Statistics> selByMonth(@Param("year") int year);
}
