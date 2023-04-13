package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.OrderItemMapper;
import cn.edu.hstc.sport_mall.pojo.OrderItem;
import cn.edu.hstc.sport_mall.pojo.Product;
import cn.edu.hstc.sport_mall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-处理和订单项相关的业务
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    /*
     * 根据传入的用户编号，查询该用户还未生成订单的订单项集合
     */
    @Override
    public List<OrderItem> selListByUser(int uid) {
        return orderItemMapper.selListByUser(uid);
    }
    
    /*
     * 存在对应产品的订单项，则在该订单项的基础上进行数量累加
     */
    @Override
    public int update(OrderItem orderItem) {
        return orderItemMapper.update(orderItem);
    }

    /*
     * 不存在对应产品的订单项，则创建新的订单项
     */
    @Override
    public int insAdd(OrderItem orderItem) {
        return orderItemMapper.insAdd(orderItem);
    }

    /*
     * 根据订单项编号，查询对应的订单项信息
     */
    @Override
    public OrderItem selGet(int oiid) {
        return orderItemMapper.selGet(oiid);
    }

    /*
     * 根据传入的用户编号，查询该用户购物车中产品数量
     */
    @Override
    public int selCount(int uid) {
        return orderItemMapper.selCount(uid);
    }

    /*
     * 删除指定编号的订单项
     */
    @Override
    public int delete(int id) {
        return orderItemMapper.delete(id);
    }

    /*
     * 生成订单，关联它的订单项
     */
    @Override
    public int updateOid(OrderItem orderItem) {
        return orderItemMapper.updateOid(orderItem);
    }

    /*
     * 查询和订单号相关的所有订单项
     */
    @Override
    public List<OrderItem> selByOid(int uid, int oid) {
        return orderItemMapper.selByOid(uid, oid);
    }

    /*
     * 查询和订单号相关的所有订单项，同时查询每个订单项关联的产品，但不包含每个产品的所属分类信息
     */
    @Override
    public List<OrderItem> selByOidNotIncluCategory(int uid, int oid) {
        return orderItemMapper.selByOidNotIncluCategory(uid, oid);
    }

    /*
     * 根据用户编号和订单编号，查询该订单的第一个产品的评价数量
     */
    @Override
    public List<Product> selFirstProductByUidAndOid(int uid, int oid) {
        return orderItemMapper.selFirstProductByUidAndOid(uid, oid);
    }
}
