package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Order;
import cn.edu.hstc.sport_mall.pojo.OrderItem;

import java.util.List;

/**
 * Service层接口-处理和订单相关的业务
 */
public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    /*
     * 结算并生成订单
     */
    float insAdd(Order order, List<OrderItem> ois);

    /*
     * 查询用户所有未删除的订单
     */
    List<Order> selList(int uid, String status);

    /*
     * 查询订单
     */
    Order selGet(int id);

    /*
     * 更新订单状态
     */
    int updateStatus(Order order);

    /*
     * 查询所有的订单
     */
    List<Order> selAll(int start, int count);

    /*
     * 查询order_表的总记录数
     */
    int selCount();

}
