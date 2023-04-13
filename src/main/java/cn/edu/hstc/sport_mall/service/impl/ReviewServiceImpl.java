package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.ReviewMapper;
import cn.edu.hstc.sport_mall.pojo.Review;
import cn.edu.hstc.sport_mall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-处理和评论相关的业务
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /*
     * 根据传入的产品编号，查询该产品的所有评论信息
     */
    @Override
    public List<Review> selByPid(int pid) {
        return reviewMapper.selByPid(pid);
    }

    /*
     * 根据传入的产品编号，查询该产品的评论数量
     */
    @Override
    public int selCount(int pid) {
        return reviewMapper.selCountByPid(pid);
    }

    /*
     * 添加评论
     */
    @Override
    public int insAdd(Review review) {
        int result = reviewMapper.insAdd(review);
        redisTemplate.delete("reviews" + review.getPid());

        return result;
    }
}
