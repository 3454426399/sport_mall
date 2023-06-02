# sport_mall

sport_mall是一个体育商城系统，是模仿天猫、京东等购物商城做的项目。

## 技术栈

Spring、SpringMVC、MyBtis、Bootstrap、JSP、Mysql。

## 数据库设计

sport_mall项目包含了15张数据表，它们分别是 account(支付账户表)、address(收货地址表)、admin(系统管理员表)、category(商品分类表)、category_property(分类属性表)、city(城市表)、order_(订单表)、orderitem(订单项表)、product(商品表)、productimage(商品图片表)、property(属性表)、propertyvalue(属性值表)、review(评论表)、statistics(统计表)、user(用户表)，其中：

**user表**：id(主键)、phone(手机号码)、password(密码)、salt(盐值)

**account表**：id(主键)、uid(用户id)、money(余额)、password(支付密码)

**city表**：id(主键)、province(省)、city(市)、county(区)、town(镇)

**address表**：id(主键)、uid(用户id)、city_id(城市id)、detail(详细地址)

**admin表**：id(主键)、adminName(管理员用户名)、adminPassword(管理员密码)、salt(盐值)

**category表**：id(主键)、name(分类名称)

**property表**：id(主键)、name(属性名称)

**category_property表**：id(主键)、cid(分类id)、ptid(属性id)

**product表**：id(主键)、name(商品名称)、subTitle(小标题)、originalPrice(原价)、promotePrice(促销价)、createDate(上市时间)、stock(库存)、cid(分类id)、reviewCount(评论数量)、saleCount(月销量)

**productimage表**：id(主键)、pid(商品id)、type(图片类型)、

**propertyvalue表**：id(主键)、pid(商品id)、ptid(属性id)、value(属性值)

**order_表**：id(主键)、orderCode(订单号)、address(收货地址)、post(邮政编码)、receiver(联系人)、mobile(手机号码)、userMessage(备注)、uid(用户id)、createDate(创建时间)、payDate(支付时间)、deliveryDate(发货时间)、confirmDate(确认发货时间)、status(订单状态)

**orderitem表**：id(主键)、uid(用户id)、pid(商品id)、oid(订单id)、number(商品数量)

**review表**：id(主键)、uid(用户id)、pid(商品id)、content(评论内容)、createDate(评论时间)

**statistics表**：id(主键)、year(年)、month(月)、day(日)、visit(当日访问量)、sale(当日销售量)、income(当日收入)

## 业务描述

`sport_mall` 分为业务系统和后台管理系统。

- 业务系统

  用户可以访问首页面、查看所有的商品分类、查看某个商品分类下的商品(可以按照综合、人气、月销量、新品、价格进行排序，也可以按照价格区间进行筛选)、通过关键字搜索商品、查看商品的详细信息。

  用户可以通过注册成为系统用户、注册成功之后可以进行登录(账号密码登录、短信验证码登录)、当忘记密码时用户可以重置密码、用户可以退出登录状态。

  用户可以将商品添加至购物车、可以将购物车中的商品移除、可以选中购物车中的商品进行结算。

  用户可以下订单，在提交订单时可以将收货地址添加为常用地址，可以将常用地址作为本次收货地址、用户根据订单状态管理自己的订单(所有订单、待支付、待发货、待收货、待评价)、用户可以对购买的商品进行评价，也可以看到其他人对该商品的所有评价。

  

- 后台管理系统

  管理员需要通过账号、密码、图形验证码进行登录。

  管理员可以查看所有的商品分类、可以添加一个新的商品分类、可以对商品分类进行编辑、可以查看某个商品分类的所有属性、可以为某个商品分类添加一个新的属性、可以对某个商品分类的某个属性进行编辑、可以删除某个商品分类的某个属性。

  管理员可以对某个商品分类下的所有商品进行管理(查看/新增/编辑商品信息、查看/新增/删除商品图片、为商品绑定属性、为商品解绑属性、查看/编辑商品某个属性的属性值)。

  管理员可以查看所有的用户信息、订单信息、可以对订单发货。

  管理员可以按照年/月/日为单位对访问量、销售量、收入进行统计。

  管理员可以按照日期、关键字对系统日志进行查看，可以按照日期为单位对系统日志进行删除。

## 安全性、可靠性、数据保密性

- 安全性

  1. 系统用户在未登录前只能访问有限的资源，只有登录后才能管理购物车和订单；系统管理员只有登录后才能做后台管理。
  2. 系统屏蔽了404错误和500错误的详细信息，自定义错误页面返回给用户。
  3. 密码强度保护，要求密码强度8-16位，需要包含大小写字母、数字和特殊符号。

- 可靠性

  1. 对用户输入数据有提示和检查，能做出正确的处理。
  2. 删除操作，弹框让用户再次确认，防止误操作

- 数据保密性

  用户的密码在数据库中使用加密存储，密码算法使用的是SHA-1散列算法，加密次数为1024次，加密过程中混入了随机盐值。

