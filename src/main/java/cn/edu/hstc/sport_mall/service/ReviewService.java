package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Review;

import java.util.List;

/**
 * Service层接口-处理和评论相关的业务
 */
public interface ReviewService {

    /*
     * 查询指定编号的产品的评论信息
     */
    List<Review> selByPid(int pid);
    
    /*
     * 查询指定编号的产品的评论数量
     */
    int selCount(int pid);

    /*
     * 添加评论
     */
    int insAdd(Review review);
}
