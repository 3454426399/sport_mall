package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Admin;

/**
 * Service层接口-负责管理员的登录验证
 */
public interface AdminService {

    /*
     * 管理员查询
     */
    Admin selLogin(String adminName);
}
