package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Admin;
import org.apache.ibatis.annotations.Select;

/**
 * Mapper层-负责管理员的登录验证
 */
public interface AdminMapper {
    /*
     * 根据传递进来的用户名，判断是否存在该管理员
     */
    @Select("SELECT * FROM `admin` WHERE adminName = #{0} limit 1")
    Admin selLogin(String adminName);
}
