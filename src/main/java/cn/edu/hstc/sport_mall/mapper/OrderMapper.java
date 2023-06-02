package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Maper层-处理和订单相关的事务
 */
public interface OrderMapper {

    /*
     * 生成订单
     */
    @Insert("INSERT INTO order_(id, orderCode, address, post, receiver, mobile, userMessage, createDate, payDate, deliveryDate, confirmDate, uid, status) " +
            "VALUES(#{id}, #{orderCode}, #{address}, #{post}, #{receiver}, #{mobile}, #{userMessage}, #{createDate}, #{payDate}, #{deliveryDate}, #{confirmDate}, #{uid}, #{status})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    int insAdd(Order order);

    /*
     * 查询所有未删除的订单
     */
    @Select("SELECT id, orderCode, createDate, status FROM order_ WHERE uid = #{uid} AND status != #{status}")
    List<Order> selList(@Param("uid") int uid, @Param("status") String status);

    /*
     * 根据订单编号，查询订单
     */
    @Select("SELECT id, orderCode, address, post, receiver, mobile, createDate, payDate, deliveryDate FROM order_ WHERE id = #{id} LIMIT 1")
    Order selGet(@Param("id") int id);

    /*
     * 更新订单状态
     */
    int updateStatus(Order order);

    /*
     * 查询所有的订单
     */
    @Select("SELECT id, createDate, payDate, deliveryDate, confirmDate, status, uid FROM order_ where id > #{start} ORDER BY id DESC limit #{count}")
    List<Order> selAll(@Param("start") int start, @Param("count") int count);

    /*
     * 查询order_表的总记录数
     */
    @Select("select count(*) from order_")
    int selCount();
}
