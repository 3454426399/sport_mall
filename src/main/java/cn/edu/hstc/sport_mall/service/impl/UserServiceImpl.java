package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.UserMapper;
import cn.edu.hstc.sport_mall.pojo.User;
import cn.edu.hstc.sport_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-用户管理
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /*
     * 分页查询用户列表-编号 & 用户名
     */
    @Override
    public List<User> selList() {
        return userMapper.selList();
    }

    /*
     * 判断手机号是否已经注册过了
     */
    @Override
    public User selExist(String phone) {
        return userMapper.selExist(phone);
    }

    /*
     * 用户注册-新增一个用户
     */
    @Override
    public int insRegister(User user) {
        return userMapper.insRegister(user);
    }

    /*
     * 重置密码
     */

    @Override
    public int updResetPassword(User user) {
        return userMapper.updResetPassword(user);
    }

    /*
     * 根据用户编号，查询用户信息
     */
    @Override
    public User selById(int id) {
        return userMapper.selById(id);
    }
}
