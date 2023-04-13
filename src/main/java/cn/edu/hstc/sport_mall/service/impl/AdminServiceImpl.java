package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.AdminMapper;
import cn.edu.hstc.sport_mall.pojo.Admin;
import cn.edu.hstc.sport_mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service层实现类-负责管理员的登录验证
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /*
     * 管理员查询
     * 根据输入的用户名，查询是否存在该管理员
     */
    @Override
    public Admin selLogin(String adminName) {
        return adminMapper.selLogin(adminName);
    }
}
