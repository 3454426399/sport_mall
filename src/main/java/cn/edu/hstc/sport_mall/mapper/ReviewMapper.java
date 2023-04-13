package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper层-处理和用户评论相关的业务
 */
public interface ReviewMapper {

    /*
     * 查询指定编号产品的所有评论，并按评论时间降序排序
     */
    List<Review> selByPid(@Param("pid") int pid);

    /*
     * 查询指定编号产品的评论数量
     */
    @Select("SELECT COUNT(*) FROM review WHERE pid = #{pid}")
    int selCountByPid(@Param("pid") int pid);

    /*
     * 添加评论
     */
    @Insert("INSERT INTO review(id, uid, pid, content, createDate) VALUES (DEFAULT, #{uid}, #{pid}, #{content}, #{createDate})")
    int insAdd(Review review);

}
