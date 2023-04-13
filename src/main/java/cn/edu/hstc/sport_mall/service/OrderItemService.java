package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.OrderItem;
import cn.edu.hstc.sport_mall.pojo.Product;

import java.util.List;

/**
 * Service层接口-处理和订单项相关的业务
 */
public interface OrderItemService {

    /*
     * 查询某个用户还未生成订单的订单项集合
     */
    List<OrderItem> selListByUser(int uid);

    /*
     * 更新订单项中产品的数量
     */
    int update(OrderItem orderItem);
    
    /*
     * 添加新的订单项
     */
    int insAdd(OrderItem orderItem);
    
    /*
     * 查询订单项
     */
    OrderItem selGet(int oiid);

    /*
     * 查询用户购物车中产品数量
     */
    int selCount(int uid);

    /*
     * 删除指定编号的订单项
     */
    int delete(int id);

    /*
     * 生成订单，关联它的订单项
     */
    int updateOid(OrderItem orderItem);

    /*
     * 查询和订单号相关的所有订单项
     */
    List<OrderItem> selByOid(int uid, int oid);

    /*
     * 查询和订单号相关的所有订单项，但不包含产品所属分类信息
     */
    List<OrderItem> selByOidNotIncluCategory(int uid, int oid);

    /*
     * 根据用户编号和订单编号，查询该订单的第一个产品的评价数量
     */
    List<Product> selFirstProductByUidAndOid(int uid, int oid);
}
