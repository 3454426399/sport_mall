package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.OrderItem;
import cn.edu.hstc.sport_mall.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mapper层-处理与订单项相关的事务
 */
public interface OrderItemMapper {

    /*
     * 根据传入的用户编号，查询该用户还未生成订单的订单项集合
     */
    List<OrderItem> selListByUser(@Param("uid") int uid);

    /*
     * 存在对应产品的订单项，则在该订单项的基础上进行数量累加
     */
    @Update("UPDATE orderitem SET `number` = #{number} WHERE id = #{id}")
    int update(OrderItem orderItem);

    /*
     * 不存在对应产品的订单项，则创建新的订单项
     */
    @Insert("INSERT INTO orderitem(id, pid, oid, uid, `number`) VALUES(DEFAULT, #{pid}, #{oid}, #{uid}, #{number})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    int insAdd(OrderItem orderItem);

    /*
     * 根据订单项编号，查询对应的订单项信息
     */
    OrderItem selGet(@Param("oiid") int oiid);

    /*
     * 查询用户购物车中的产品数量
     */
    @Select("SELECT IFNULL(SUM(number),0) FROM orderitem WHERE uid = #{uid} AND oid is NULL")
    int selCount(@Param("uid") int uid);

    /*
     * 删除指定编号的订单项
     */
    @Delete("DELETE FROM orderitem WHERE id = #{id}")
    int delete(@Param("id") int id);

    /*
     * 订单生成，关联它的订单项
     */
    @Update("UPDATE orderitem SET oid = #{oid} WHERE id = #{id}")
    int updateOid(OrderItem orderItem);

    /*
     * 查询和订单号相关的所有订单项，同时查询每个订单项关联的产品，以及每个产品的所属分类信息
     */
    List<OrderItem> selByOid(@Param("uid") int uid, @Param("oid") int oid);

    /*
     * 查询和订单号相关的所有订单项，同时查询每个订单项关联的产品，但不包含每个产品的所属分类信息
     */
    List<OrderItem> selByOidNotIncluCategory(@Param("uid") int uid, @Param("oid") int oid);

    /*
     * 根据用户编号和订单编号，查询该订单的所有产品的评价数量
     */
    @Select("select id, reviewCount from product where id in (select pid from orderitem where uid = #{uid} and oid = #{oid})")
    List<Product> selFirstProductByUidAndOid(@Param("uid") int uid, @Param("oid") int oid);
}
