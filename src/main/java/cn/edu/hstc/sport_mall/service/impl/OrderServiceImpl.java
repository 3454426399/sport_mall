package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.OrderMapper;
import cn.edu.hstc.sport_mall.pojo.Order;
import cn.edu.hstc.sport_mall.pojo.OrderItem;
import cn.edu.hstc.sport_mall.service.OrderItemService;
import cn.edu.hstc.sport_mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-处理和订单相关的事务
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemService orderItemService;

    /*
     * 生成新订单事务
     */
    @Override
    public float insAdd(Order order, List<OrderItem> ois) {
        float total = 0;
        int result = orderMapper.insAdd(order);    //生成订单

        if (result > 0){

            for (OrderItem orderItem : ois){    //关联和订单相关的订单项
                orderItem.setOid(order.getId());
                orderItemService.updateOid(orderItem);
                total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();    //结算
            }
        }

        return total;
    }

    /*
     * 查询用户所有未删除的订单
     */
    @Override
    public List<Order> selList(int uid, String status) {
        return orderMapper.selList(uid, status);
    }

    /*
     * 根据订单编号，查询订单
     */
    @Override
    public Order selGet(int id) {
        return orderMapper.selGet(id);
    }

    /*
     * 更新订单状态
     */
    @Override
    public int updateStatus(Order order) {
        return orderMapper.updateStatus(order);
    }

    /*
     * 查询所有订单
     */
    @Override
    public List<Order> selAll(int start, int count) {
        return orderMapper.selAll(start, count);
    }

    /*
     * 查询order_表的总记录数
     */

    @Override
    public int selCount() {
        return orderMapper.selCount();
    }
}
