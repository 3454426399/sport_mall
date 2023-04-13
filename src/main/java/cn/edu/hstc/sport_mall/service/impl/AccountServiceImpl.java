package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.AccountMapper;
import cn.edu.hstc.sport_mall.pojo.*;
import cn.edu.hstc.sport_mall.service.*;
import org.apache.shiro.SecurityUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Service层实现类-处理和支付账号相关的业务
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /*
     * 查询用户的支付账号，一个用户有一个支付账号
     */
    @Override
    public Account selByUser(int uid) {
        return accountMapper.selByUser(uid);
    }

    /*
     * 加密用户输入的支付密码
     */
    @Override
    public String selEncryptionPassword(String password) {
        return accountMapper.selEncryptionPassword(password);
    }

    /*
     * 支付事务
     */
    @Override
    public int updatePay(Order order, Account account, Statistics statistics) throws IOException {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        accountMapper.updPay(account);    //账户扣费

        List<OrderItem> ois = orderItemService.selByOid(user.getId(), order.getId());

        for (OrderItem orderItem : ois){
            statistics.setSale( statistics.getSale() + orderItem.getNumber());
            orderItem.getProduct().setStock( orderItem.getProduct().getStock() - orderItem.getNumber() );
            orderItem.getProduct().setSaleCount( orderItem.getProduct().getSaleCount() + orderItem.getNumber() );
            productService.update(orderItem.getProduct());    //更新产品的库存和月销量

            UpdateRequest request = new UpdateRequest();
            request.doc(
                    XContentFactory.jsonBuilder()
                            .startObject()
                            .field("saleCount",orderItem.getProduct().getSaleCount())
                            .endObject()
            );
            UpdateQuery updateQuery = new UpdateQueryBuilder()
                    .withUpdateRequest(request)
                    .withClass(cn.edu.hstc.sport_mall.espojo.Product.class)
                    .withId(new Integer(orderItem.getProduct().getId()).toString())
                    .build();
            elasticsearchOperations.update(updateQuery);
        }

        statisticsService.update(statistics);    //后台统计-日销量和收入

        //更新订单的状态
        order.setPayDate(new Date());
        order.setStatus(OrderService.waitDelivery);
        int result = orderService.updateStatus(order);

        return result;
    }
}
