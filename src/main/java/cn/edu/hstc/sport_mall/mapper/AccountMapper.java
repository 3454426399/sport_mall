package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Mapper层-处理和支付账号相关的事务
 */
public interface AccountMapper {

    /*
     * 查询用户的支付账号
     */
    @Select("SELECT id, money, password FROM account WHERE uid = #{uid} LIMIT 1")
    Account selByUser(@Param("uid") int uid);

    /*
     * 对用户输入的支付密码进行加密，以便和数据库中的正确密码比价是否正确
     */
    @Select("SELECT SHA(#{password})")
    String selEncryptionPassword(@Param("password") String password);

    /*
     * 更新账户余额
     */
    @Update("UPDATE account SET money = #{money} WHERE id = #{id}")
    int updPay(Account account);
}
