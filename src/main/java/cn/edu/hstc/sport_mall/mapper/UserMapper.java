package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper层-用户管理
 */
public interface UserMapper {

    /*
     * 查询用户的编号 & 用户名
     */
    @Select("SELECT id, phone FROM `user` order by id")
    List<User> selList();

    /*
     * 判断手机号码是否已经注册过了
     */
    @Select("SELECT * FROM `user` WHERE phone = #{phone} LIMIT 1")
    User selExist(@Param("phone") String phone);

    /*
     * 用户注册-新增一个用户
     */
    @Insert("INSERT INTO `user`(id, phone, `password`, salt) VALUES(DEFAULT, #{phone}, #{password}, #{salt})")
    int insRegister(User user);

    /*
     * 重置密码
     */
    @Update("UPDATE  `user` SET salt = #{salt}, password = #{password} WHERE phone = #{phone}")
    int updResetPassword(User user);

    /*
     * 根据主键查询用户的手机号码
     */
    @Select("SELECT phone FROM `user` WHERE id = #{id} LIMIT 1")
    User selById(@Param("id") int id);
}
