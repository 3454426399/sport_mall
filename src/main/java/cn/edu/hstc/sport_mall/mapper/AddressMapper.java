package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper层-处理和常用地址相关的业务
 */
public interface AddressMapper {

    /*
     * 为用户添加一个常用地址
     */
    @Insert("INSERT INTO address(id, uid, city_id, detail) VALUES(DEFAULT, #{uid}, #{city_id}, #{detail})")
    int insAdd(Address address);

    /*
     * 判断地址是否已经导入过了
     */
    @Select("SELECT id FROM address WHERE uid = #{uid} AND city_id = #{city_id} AND detail = #{detail} LIMIT 1")
    Address selExist(Address address);

    /*
     * 查询指定用户的常用地址
     */
    List<Address> selAddressByUser(@Param("uid") int uid);
}
