package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.User;

import java.util.List;

/**
 * Service层接口-用户管理
 */
public interface UserService {

    /*
     * 分页查询用户列表
     */
    List<User> selList();

    /*
     * 判断手机号是否已经注册过了
     */
    User selExist(String phone);

    /*
     * 用户注册
     */
    int insRegister(User user);

    /*
     * 重置密码
     */
    int updResetPassword(User user);

    /*
     * 根据用户编号，查询用户信息
     */
    User selById(int id);
}
