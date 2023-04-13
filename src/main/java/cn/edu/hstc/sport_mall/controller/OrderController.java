package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.Order;
import cn.edu.hstc.sport_mall.pojo.OrderItem;
import cn.edu.hstc.sport_mall.service.OrderItemService;
import cn.edu.hstc.sport_mall.service.OrderService;
import cn.edu.hstc.sport_mall.service.ProductImageService;
import cn.edu.hstc.sport_mall.service.UserService;
import cn.edu.hstc.sport_mall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * Controller层-订单管理
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private UserService userService;

    /*
     * 查询所有订单
     */
    @RequestMapping("admin_order_list")
    public String list(PageUtil pageUtil, Model model){
        List<Order> os = orderService.selAll(pageUtil.getStart(), pageUtil.getCount());    //返回当前页面的订单集合
        int total = orderService.selCount();    //查询总记录数
        pageUtil.setTotal(total);

        for (Order order : os){
            order.setOrderItems(orderItemService.selByOidNotIncluCategory(order.getUid(), order.getId()));    //为订单填充订单项
            order.setUser(userService.selById(order.getOrderItems().get(0).getUid()));

            for (OrderItem orderItem : order.getOrderItems()){
                order.setTotal(order.getTotal() + orderItem.getProduct().getPromotePrice() * orderItem.getNumber());    //计算订单总价
                order.setTotalNumber( order.getTotalNumber() + orderItem.getNumber() );    //计算订单产品总数
                //查询订单项的产品图片
                orderItem.getProduct().setFirstProductImage(productImageService.selGetByPid( orderItem.getProduct().getId()));
            }
        }

        model.addAttribute("os", os);
        model.addAttribute("page", pageUtil);

        return "admin/listOrder";
    }

    /*
     * 发货，并更新订单为待确认状态
     */
    @RequestMapping("admin_order_delivery")
    public String delivery(Order order){
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.updateStatus(order);

        return "redirect:admin_order_list";
    }
}