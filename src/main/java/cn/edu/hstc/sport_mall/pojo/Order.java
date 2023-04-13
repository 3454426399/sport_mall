package cn.edu.hstc.sport_mall.pojo;

import cn.edu.hstc.sport_mall.service.OrderService;

import java.util.Date;
import java.util.List;

/**
 *  实体类-订单
 */
public class Order {

    private int id;    //编号
    private String orderCode;    //订单号
    private String address;    //收货详细地址
    private String post;    //邮政编码
    private String receiver;    //收货人名称
    private String mobile;    //收货人联系方式
    private String userMessage;    //用户备注
    private Date createDate;    //订单生成时间
    private Date payDate;    //订单支付时间
    private Date deliveryDate;    //订单发货时间
    private Date confirmDate;    //订单确认时间
    private int uid;    //用户编号
    private String status;    //订单状态

    //非数据库字段
    private List<OrderItem> orderItems;

    private User user;

    private float total;

    private int totalNumber;

    public Order() {
    }

    public Order(int id, String orderCode, String address, String post, String receiver, String mobile, String userMessage, Date createDate, Date payDate, Date deliveryDate, Date confirmDate, int uid, String status, List<OrderItem> orderItems, User user, float total, int totalNumber) {
        this.id = id;
        this.orderCode = orderCode;
        this.address = address;
        this.post = post;
        this.receiver = receiver;
        this.mobile = mobile;
        this.userMessage = userMessage;
        this.createDate = createDate;
        this.payDate = payDate;
        this.deliveryDate = deliveryDate;
        this.confirmDate = confirmDate;
        this.uid = uid;
        this.status = status;
        this.orderItems = orderItems;
        this.user = user;
        this.total = total;
        this.totalNumber = totalNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", address='" + address + '\'' +
                ", post='" + post + '\'' +
                ", receiver='" + receiver + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", createDate=" + createDate +
                ", payDate=" + payDate +
                ", deliveryDate=" + deliveryDate +
                ", confirmDate=" + confirmDate +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                ", orderItems=" + orderItems +
                ", user=" + user +
                ", total=" + total +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public String getStatusDesc(){
        String desc ="未知";
        switch(status){
            case OrderService.waitPay:
                desc="待付款";
                break;
            case OrderService.waitDelivery:
                desc="待发货";
                break;
            case OrderService.waitConfirm:
                desc="待收货";
                break;
            case OrderService.waitReview:
                desc="等评价";
                break;
            case OrderService.finish:
                desc="完成";
                break;
            case OrderService.delete:
                desc="刪除";
                break;
            default:
                desc="未知";
        }
        return desc;
    }
}
