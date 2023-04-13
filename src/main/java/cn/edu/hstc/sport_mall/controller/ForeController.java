package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.*;
import cn.edu.hstc.sport_mall.service.*;
import cn.edu.hstc.sport_mall.util.HttpClientUtil;

import cn.edu.hstc.sport_mall.util.PageUtil;
import cn.edu.hstc.sport_mall.util.ShiroUtil;
import cn.edu.hstc.sport_mall.util.StringUtil;
import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Controller层-处理前台页面的路径
 */
@Controller
public class ForeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CityService cityService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Autowired
    private RedisTemplate redisTemplate;


    /*
     * 数据迁移
     */
    @ResponseBody
    @RequestMapping("cp_mysql_to_es")
    public String cp_mysql_to_es() {
        int start = 1, end = 5000;

        // 4825278
        while (start <= 4825278) {
            List<Product> productList = productService.cp_mysql_to_es(start, end);
            List<IndexQuery> queries = new LinkedList<>();

            for (Product p : productList) {
                cn.edu.hstc.sport_mall.espojo.Product esp = new cn.edu.hstc.sport_mall.espojo.Product();

                esp.setId(p.getId());
                esp.setName(p.getName());
                esp.setPromotePrice(p.getPromotePrice());
                esp.setSaleCount(p.getSaleCount());
                esp.setReviewCount(p.getReviewCount());
                esp.setFirstProductImage(p.getFirstProductImage());

                IndexQuery indexQuery = new IndexQueryBuilder()
                        .withIndexName("product")
                        .withType("product")
                        .withObject(esp)
                        .build();
                queries.add(indexQuery);
            }
            if (queries.size() > 0) {
                elasticsearchOperations.bulkIndex(queries);
            }

            start = end + 1;
            end += 5000;
        }

        return "copy data of mysql to es successfully !";
    }

    /*
     * 请求商城首页所需要的资源，并返回商城首页
     */
    @RequestMapping("forehome")
    public String home(Model model) throws Exception {
        List<Category> categoryList = categoryService.selList();   //获取所有的产品分类

        productService.fill(categoryList);    //为分类绑定其产品信息
        model.addAttribute("cs", categoryList);
        model.addAttribute("categorycount", categoryList.size());

        return "fore/home";
    }

    /*
     * 请求转发 到 注册第一步页面
     */
    @RequestMapping("foreRegisterPhone")
    public String registerPhone(){
        return "fore/register_phone";
    }

    /*
     * 请求转发 到 注册第二步页面
     */
    @RequestMapping("foreRegisterPassword")
    public String registerPassword(Model model, String phone){
        model.addAttribute("phone", phone);

        return "fore/register";
    }

    /*
     * 用户注册-新增一个用户
     */
    @RequestMapping("foreRegister")
    @ResponseBody
    public String register(User user, HttpServletRequest request){
        String sequence = ShiroUtil.createRandomSequence();    //生成随机序列
        String salt = ByteSource.Util.bytes(sequence).toString();    //把随机序列根据特定算法转化为盐值

        user.setSalt(salt);
        user.setPassword(ShiroUtil.encrypt(user.getPassword(), user.getSalt()));    //对用户密码进行加密
        userService.insRegister(user);    //新增用户
        request.setAttribute("logMsg", user.getPhone() + "注册成功");

        return "success";
    }

    /*
     * 注册成功，跳转至成功页面
     */
    @RequestMapping("foreRegisterSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    /*
     * 请求转发 到 登录页面
     */
    @RequestMapping("foreLoginPage")
    public String loginPage(){
        return "fore/login";
    }

    /*
     * 请求转发 到 忘记密码页面
     */
    @RequestMapping("forgetPassword")
    public String forgetPassword(){
        return "fore/forgetPassword";
    }

    /*
     * 请求转发 到 重置密码页面
     */
    @RequestMapping("foreResetPasswordPage")
    public String resetPasswordPage(Model model, String phone){
        model.addAttribute("phone", phone);

        return "fore/resetPasswordPage";
    }

    /*
     * 重置密码
     */
    @RequestMapping("foreResetPassword")
    @ResponseBody
    public String resetPassword(User user, HttpServletRequest request){
        String sequence = ShiroUtil.createRandomSequence();
        String salt = ByteSource.Util.bytes(sequence).toString();

        user.setSalt(salt);
        user.setPassword(ShiroUtil.encrypt(user.getPassword(), user.getSalt()));
        userService.updResetPassword(user);
        request.setAttribute("logMsg", user.getPhone() + "重置了密码");

        return "success";
    }

    /*
     * 密码重置成功，跳转至成功页面
     */
    @RequestMapping("foreResetPasswordSuccess")
    public String resetPasswordSuccess(){
        return "fore/resetSuccess";
    }

    /*
     * 退出-用户退出登录状态
     */
    @RequestMapping("forelogout")
    public String logout(HttpServletRequest request){
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getSession().getAttribute("user");

        request.setAttribute("logMsg", user.getPhone() + "退出......");
        currentUser.logout();
        return "redirect:forehome";
    }

    /*
     * 通过elasticsearch搜索引擎实现商品搜索，以及关键字的高亮显示
     */
    @RequestMapping("foresearch")
    public String search(Model model, @RequestParam(defaultValue = "篮球") String keyword, int pageNo) {
        //构造查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("product")
                .withQuery(QueryBuilders.termQuery("name", keyword))
                .withSort(SortBuilders.fieldSort("saleCount").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pageNo, 60))
                .build();

        //总数量
        long total = elasticsearchOperations.count(
                new NativeSearchQueryBuilder()
                        .withIndices("product")
                        .withQuery(QueryBuilders.termQuery("name", keyword))
                        .build()
        );
        long totalPage = (total % 60 == 0)?(total / 60):(total / 60 + 1);    //总页数
        if (totalPage == 0)    //避免浏览器解析jsp页面时出错
            totalPage = 1;
        //查询结果集
        List<cn.edu.hstc.sport_mall.espojo.Product> productList = elasticsearchOperations.queryForList(searchQuery, cn.edu.hstc.sport_mall.espojo.Product.class);
        for (cn.edu.hstc.sport_mall.espojo.Product product : productList) {    //渲染，对关键字作高亮显示处理
            product.setName(StringUtil.decorate(product.getName(), keyword, "red"));
        }

        model.addAttribute("ps", productList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("total", total);

        return "fore/searchResult";
    }

    /*
     * 响应产品页面所需要的各种资源
     */
    @RequestMapping("foreproduct")
    public String product(int pid, Model model){
        if (redisTemplate.opsForValue().get("p-" + pid) != null && redisTemplate.opsForValue().get("pvs-" + pid) != null
                    && redisTemplate.opsForValue().get("reviews-" + pid) != null){
            model.addAttribute("reviews", redisTemplate.opsForValue().get("reviews-" + pid));
            model.addAttribute("p", redisTemplate.opsForValue().get("p-" + pid));
            model.addAttribute("pvs", redisTemplate.opsForValue().get("pvs-" + pid));

            return "fore/product";
        }
        Product product = productService.selGet(pid);
        ProductImage productImage = new ProductImage();

        //查询产品的单个图片集合
        productImage.setPid(pid);
        productImage.setType("type_single");
        product.setSingleProductList( productImageService.selList(productImage) );
        product.setFirstProductImage( product.getSingleProductList().get(0) );    //根查询该产品的第一张 type_single类型的图片

        //查询产品的详情图片集合
        productImage.setType("type_detail");
        product.setDetailProductList( productImageService.selList(productImage) );

        List<PropertyValue> pvs = propertyValueService.selByPid(pid);    //查询产品的属性值信息
        List<Review> reviews = reviewService.selByPid(pid);    //查询该产品的评论信息

        redisTemplate.opsForValue().set("reviews-" + pid, reviews, 300, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("p-" + pid, product, 300, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("pvs-" + pid, pvs, 300, TimeUnit.SECONDS);

        model.addAttribute("reviews", reviews);
        model.addAttribute("p", product);
        model.addAttribute("pvs", pvs);

        return "fore/product";
    }

    /*
     * 判断用户是否已经登录
     */
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLoginState(){
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()){
            return "false";
        }else {
            return "success";
        }
    }

    /*
     * 根据不同条件对产品进行筛选、排序
     */
    @RequestMapping("forecategory")
    public String category(int cid, String sort, PageUtil pageUtil, Model model){
        Category category = categoryService.selGet(cid);    //查询编号对应的分类信息

        category.setProducts( productService.selListForFore(cid) );    //查询该分类的所有产品信息
        List<ProductImage> pis = productImageService.selFirstImgForCategory(cid);
        int size = pis.size();
        for (int i = 0; i < size; i++){
            category.getProducts().get(i).setFirstProductImage(pis.get(i));
        }

        switch (sort){
            case "all":    //综合比较器：以‘销售练 * 评论量’作为标准，从大到小排序
                Collections.sort(category.getProducts(), (p1, p2) -> p2.getSaleCount() * p2.getReviewCount() -
                        p1.getSaleCount() * p1.getReviewCount());
                break;
            case "review":    //人气比较器：以评论量作为标准，从多到少排序
                Collections.sort(category.getProducts(), (p1, p2) -> p2.getReviewCount() - p1.getReviewCount());
                break;
            case "date":    //新品比较器：比时间作为标准，从近到远排序
                Collections.sort(category.getProducts(), (p1, p2) -> p2.getCreateDate().compareTo(p1.getCreateDate()));
                break;
            case "saleCount":    //销量比较器(默认)：以销售练作为标准，从多到少排序
                Collections.sort(category.getProducts(), (p1, p2) -> p2.getSaleCount() - p1.getSaleCount());
                break;
            case "price":    //价格比较器，以价格作为标准，从低到高排序
                Collections.sort(category.getProducts(), (p1, p2) -> (int) (p1.getPromotePrice() - p2.getPromotePrice()));
                break;
        }

        model.addAttribute("c", category);
        return "fore/category";
    }

    /*
     * 立即购买触发事件-更新订单项或生成新订单项
     */
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num){
        Subject currentUser = SecurityUtils.getSubject();

        int oiid = 0;
        boolean found = false;
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        List<OrderItem> orderItemList = orderItemService.selListByUser(user.getId());    //查询该用户所有未生成订单的订单项

        for (OrderItem orderItem : orderItemList){
            if (orderItem.getPid() == pid){    //存在符合的订单项，则更新该订单项的产品数量
                found = true;
                orderItem.setNumber( orderItem.getNumber() + num );
                orderItemService.update(orderItem);
                oiid = orderItem.getId();
                break;
            }
        }

        if (!found){    //没有符合的订单项，则生成新的订单项
            OrderItem orderItem = new OrderItem();

            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItem.setUid(user.getId());
            orderItemService.insAdd(orderItem);
            oiid = orderItem.getId();
        }
        return "redirect:forebuy?oiid="+oiid;
    }

    /*
     * 生成订单项集合，价格总计，跳转至结算页面
     */
    @RequestMapping("forebuy")
    public String buy(Model model, String[] oiid){
        Session session = SecurityUtils.getSubject().getSession();

        float total = 0;
        List<OrderItem> ois = new ArrayList<>();

        for (String stroiid : oiid){    //当从购物车过来时，可能有多个订单项，这里对每个订单项依次处理
            OrderItem orderItem = orderItemService.selGet(Integer.parseInt(stroiid));
            orderItem.getProduct().setFirstProductImage(productImageService.selGetByPid(orderItem.getProduct().getId()));
            ois.add(orderItem);    //把订单项添加到订单项集合中
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();    //累计价格
        }

        session.setAttribute("ois", ois);
        model.addAttribute("total", total);

        return "fore/buy";
    }

    /*
     * 以JSON格式返回用户的常用地址
     */
    @RequestMapping(value = "foreGetCommonAddress", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCommonAddress(){
        Session session = SecurityUtils.getSubject().getSession();

        User user = (User) session.getAttribute("user");
        List<Address> adds = addressService.selAddressByUser(user.getId());    //查询该用户的常用地址学习
        List<String> addressList = new ArrayList<>();

        for (Address address : adds){    //格式化
            String str = address.getCity().getProvince() + "-" + address.getCity().getCity() + "-" + address.getCity().getCounty() + "-"
                    + address.getCity().getTown() + "-" + address.getDetail();
            addressList.add(str);
        }

        return JSONUtils.toJSONString(addressList);
    }

    /*
     * 查询指定省份下的所有城市
     */
    @RequestMapping(value = "foreShowCity", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String showCity(String province){
        List<String> cityList = new ArrayList<>();

        cityList = cityService.selGetCity(province);
        return JSONUtils.toJSONString(cityList);
    }

    /*
     * 查询指定省份、城市下的所有区县
     */
    @RequestMapping(value = "foreShowCounty", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String showCounty(String province, String city){
        List<String> countyList = new ArrayList<>();

        if (province.equals("请选择"))    //直辖市下的所有区县
            countyList = cityService.selCountyByCity(city);
        else
            countyList = cityService.selCountyByProvinceAndCity(province, city);

        return JSONUtils.toJSONString(countyList);
    }

    /*
     * 查询指定省份、城市、区县下的所有城镇
     */
    @RequestMapping(value = "foreShowTown", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String showTown(String province, String city, String county){
        List<String> townList = new ArrayList<>();

        if (province.equals("请选择"))    //指定直辖市、区县下的所有城镇
            townList = cityService.selTownNotByProvince(city, county);
        else
            townList = cityService.selTownByProvince(province, city, county);

        return JSONUtils.toJSONString(townList);
    }

    /*
     * 为用户添加一个常用地址
     */
    @RequestMapping("foreAddCommonAddress")
    @ResponseBody
    public String addCommonAddress(String province, String city, String county, String town, String detailAddress){
        int city_id = 0;
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        //查询用户是否已经添加了该地址
        if (province.equals("请选择"))    //隶属于直辖市
            city_id = cityService.selIdNotByProvince(city, county, town);
        else    //隶属于省份
            city_id = cityService.selIdByProvince(province, city, county, town);

        Address address = new Address();
        address.setUid(user.getId());
        address.setCity_id(city_id);
        address.setDetail(detailAddress);

        Address address1 = addressService.selExist(address);
        if (address1 != null){    //已经添加了，就不需要再添加
            return "false";
        }
        addressService.insAdd(address);    //还未添加，则添加

        //格式化，用于返回前端进行刷新
        if (province.equals("请选择"))
            return city + "-" + county + "-" + town + "-" + detailAddress;
        else
            return province + "-" + city + "-" + county + "-" + town + "-" + detailAddress;
    }

    /*
     * 把产品添加到购物车
     */
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num){
        boolean found = false;
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        List<OrderItem> orderItemList = orderItemService.selListByUser(user.getId());    //查询该用户所有未生成订单的订单项

        for (OrderItem orderItem : orderItemList){
            if (orderItem.getPid() == pid){    //存在符合的订单项，则更新该订单项的产品数量
                found = true;
                orderItem.setNumber( orderItem.getNumber() + num );
                orderItemService.update(orderItem);
                break;
            }
        }

        if (!found){    //没有符合的订单项，则生成新的订单项
            OrderItem orderItem = new OrderItem();

            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItem.setUid(user.getId());
            orderItemService.insAdd(orderItem);
        }

        return "success";
    }

    /*
     * 查看购物车
     */
    @RequestMapping("forecart")
    public String cart(Model model){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        List<OrderItem> ois = orderItemService.selListByUser(user.getId());

        for (OrderItem orderItem : ois){    //查询该用户所有订单项详情

            orderItem.getProduct().setFirstProductImage(productImageService.selGetByPid(orderItem.getProduct().getId()));
        }
        model.addAttribute("ois", ois);

        return "fore/cart";
    }

    /*
     * 更新订单项产品数量
     */
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(int pid, int num){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        List<OrderItem> ois = orderItemService.selListByUser(user.getId());    //找出用户所有订单项
        for (OrderItem orderItem : ois){    //遍历，找出指定pid的订单项
            if (orderItem.getPid() == pid){
                orderItem.setNumber(num);
                orderItemService.update(orderItem);    //更新
                break;
            }
        }
        return "success";
    }

    /*
     * 删除购物车中的订单项
     */
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid){
        int result = orderItemService.delete(oiid);

        return result>0?"success":"false";
    }

    /*
     * 结算-生成订单
     */
    @RequestMapping("forecreateOrder")
    @ResponseBody
    public String createOrder(Order order){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        Date date = new Date();
        order.setOrderCode(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date) + RandomUtils.nextInt(10000));
        order.setCreateDate(date);
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);    //设置订单位待支付状态
        List<OrderItem> ois = (List<OrderItem>) SecurityUtils.getSubject().getSession().getAttribute("ois");

        float total = orderService.insAdd(order, ois);    //结算并生成订单

        return "forealipay?oid="+order.getId() +"&total="+total;    //到支付页面
    }

    /*
     * 跳转至支付页面
     */
    @RequestMapping("forealipay")
    public String alipay(){
        return "fore/alipay";
    }

    /*
     * 付款
     */
    @RequestMapping("forepayed")
    @ResponseBody
    public String payed(int oid, float total, String password) throws Exception {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        int result = 0;

        Order order = orderService.selGet(oid);    //查询订单
        Account account = accountService.selByUser(user.getId());    //查询用户的支付账户

        if (!account.getPassword().equals( accountService.selEncryptionPassword(password) )){
            return "支付密码错误";
        }else if (account.getMoney() < total){
            return "账号余额不足";
        }else {
            Statistics statistics = statisticsService.select();
            statistics.setIncome(statistics.getIncome() + total);    //实际的更新在有事务控制的accountService.updataPay方法中

            account.setMoney(account.getMoney() - total);
            result = accountService.updatePay(order, account, statistics);    //事务管理
        }

        return result>0?"success":"false";
    }

    /*
     * 跳转至支付成功页面
     */
    @RequestMapping("paySuccess")
    public String paySuccess(){

        return "fore/payed";
    }

    /*
     * 查询我的订单
     */
    @RequestMapping("forebought")
    public String bought(Model model){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        List<Order> orders = orderService.selList(user.getId(), OrderService.delete);    //查询用户所有未删除的订单

        for (Order order : orders){
            order.setOrderItems( orderItemService.selByOid(user.getId(), order.getId()));    //为订单填充订单项

            for (OrderItem orderItem : order.getOrderItems()){
                order.setTotal( order.getTotal() + orderItem.getProduct().getPromotePrice() * orderItem.getNumber() );
                order.setTotalNumber( order.getTotalNumber() + orderItem.getNumber() );
                //查询订单项的产品图片
                orderItem.getProduct().setFirstProductImage( productImageService.selGetByPid( orderItem.getProduct().getId() ) );
            }
        }
        model.addAttribute("os", orders);

        return "fore/bought";    //跳转至我的订单页面
    }

    /*
     * 获取资源并跳转至确认收货页面
     */
    @RequestMapping("foreconfirmPay")
    public String confirmPay(int oid, Model model){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Order order = orderService.selGet(oid);

        order.setOrderItems( orderItemService.selByOid(user.getId(), oid));
        for (OrderItem orderItem : order.getOrderItems()){
            order.setTotal( order.getTotal() + orderItem.getProduct().getPromotePrice() * orderItem.getNumber() );    //计算订单总价
            order.setTotalNumber( order.getTotalNumber() + orderItem.getNumber() );    //计算订单产品总数
            //查询订单项的产品图片
            orderItem.getProduct().setFirstProductImage( productImageService.selGetByPid( orderItem.getProduct().getId() ) );
        }

        model.addAttribute("o", order);
        return "fore/confirmPay";    //跳转至确认页面
    }

    /*
     * 确认收货，更新订单
     */
    @RequestMapping("foreorderConfirmed")
    public String confirmOrder(int oid){
        Order order = orderService.selGet(oid);

        order.setConfirmDate(new Date());
        order.setStatus(OrderService.waitReview);    //把订单设置为待评价状态
        orderService.updateStatus(order);    //更新订单状态

        return "fore/orderConfirmed";
    }

    /*
     * 删除订单-不会真的删除，只是修改订单的状态为delete
     */
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid){
        Order order = orderService.selGet(oid);

        order.setStatus(OrderService.delete);    //修改订单状态为已删除状态
        orderService.updateStatus(order);

        return "success";
    }

    /*
     * 查看评论
     */
    @RequestMapping("forereview")
    public String review(int oid, Model model){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Order order = orderService.selGet(oid);

        order.setOrderItems( orderItemService.selByOid(user.getId(), oid));
        for (OrderItem orderItem : order.getOrderItems()){
            order.setTotal( order.getTotal() + orderItem.getProduct().getPromotePrice() * orderItem.getNumber() );    //计算订单总价
            order.setTotalNumber( order.getTotalNumber() + orderItem.getNumber() );    //计算订单产品总数
            //查询订单项的产品图片
            orderItem.getProduct().setFirstProductImage( productImageService.selGetByPid( orderItem.getProduct().getId() ) );
        }

        Product product = order.getOrderItems().get(0).getProduct();
        List<Review> reviewList = reviewService.selByPid(product.getId());
        product.setReviewCount(reviewList.size());

        model.addAttribute("p" ,product);
        model.addAttribute("o", order);
        model.addAttribute("reviews", reviewList);

        return "fore/review";
    }

    /*
     * 发表评论
     */
    @RequestMapping("foredoreview")
    public String doReview(int oid, int pid, String content) throws Exception {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        //生成并提交评论
        Review review = new Review();
        review.setUid(user.getId());
        review.setPid(pid);
        review.setUser(user);
        review.setContent(content);
        review.setCreateDate(new Date());
        reviewService.insAdd(review);

        //更新数据库、es中订单对应产品的评价数量
        List<Product> productList = orderItemService.selFirstProductByUidAndOid(user.getId(), oid);
        for (Product product : productList) {
            product.setReviewCount(product.getReviewCount() + 1);
            productService.update(product);

            UpdateRequest request = new UpdateRequest();
            request.doc(
                    XContentFactory.jsonBuilder()
                            .startObject()
                            .field("reviewCount",product.getReviewCount())
                            .endObject()
            );
            UpdateQuery updateQuery = new UpdateQueryBuilder()
                    .withUpdateRequest(request)
                    .withClass(cn.edu.hstc.sport_mall.espojo.Product.class)
                    .withId(new Integer(product.getId()).toString())
                    .build();
            elasticsearchOperations.update(updateQuery);
        }

        //更新订单状态
        Order order = orderService.selGet(oid);
        order.setStatus(OrderService.finish);
        orderService.updateStatus(order);

        return "redirect:forereview?oid="+oid;
    }

    /*
     * 实现发送手机验证码功能
     */
    @RequestMapping("foreSendMobilCheckCode")
    @ResponseBody
    public String sendMobilCheckCode(String phone){
        Session session = SecurityUtils.getSubject().getSession();    //Shiro管理session
        HttpClientUtil client = HttpClientUtil.getInstance();
        String mobilCheckCode = createMobilCheckCode();    //生成的短信验证码
        String message = "验证码：" + mobilCheckCode;    //短信内容

        //调用网建SMS短信通接口发送短信验证码
        int result = client.sendMsgUtf8("xiaozekai", "d41d8cd98f00b204e980", message, phone);

        if(result>0){
            session.setAttribute("mobilCheckCode", mobilCheckCode);    //把短信验证码添加到session种
            session.setTimeout(60 * 1000);    //设置有效期为1分钟

            return "success";
        }else{    //短信发送失败
            return "false";
        }
    }

    /*
     * 实现校验手机验证码功能
     */
    @RequestMapping("foreCheckPhoneCode")
    @ResponseBody
    public String CheckPhoneCode(String phoneCode){
        Session session = SecurityUtils.getSubject().getSession();    //Shiro管理session
        String currentPhoneCode = (String) session.getAttribute("mobilCheckCode");

        //短信验证码失效 或 短信验证码错误
        if (currentPhoneCode == null || !phoneCode.equals(currentPhoneCode)){
            return "false";
        }else {
            return "success";
        }
    }

    /**
     * 生成由6个随机数构成的短信验证码
     */
    public static String createMobilCheckCode(){
        String mobilCheckCode = "";
        Random random = new Random();

        for (int i = 0; i < 6; i++){
            mobilCheckCode += random.nextInt(10);
        }

        return mobilCheckCode;
    }
}