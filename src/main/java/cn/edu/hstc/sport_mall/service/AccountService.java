package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Account;
import cn.edu.hstc.sport_mall.pojo.Order;
import cn.edu.hstc.sport_mall.pojo.Statistics;

import java.io.IOException;

/**
 * Service层接口-处理和支付账号相关的事务
 */
public interface AccountService {

    /*
     * 查询用户的支付账号
     */
    Account selByUser(int uid);

    /*
     * 对用户输入的支付密码加密
     */
    String selEncryptionPassword(String password);
    
    /*
     * 支付事务
     */
    int updatePay(Order order, Account account, Statistics statistics) throws IOException;
}
