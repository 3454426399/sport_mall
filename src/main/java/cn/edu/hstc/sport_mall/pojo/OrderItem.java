package cn.edu.hstc.sport_mall.pojo;

/**
 *  实体类-订单项
 */
public class OrderItem {

    private int id;    //编号
    private int pid;    //产品编号
    private Integer oid;    //订单编号(当订单未生成时，此属性值未null)
    private int uid;    //用户编号
    private int number;    //数量

    //非数据库字段
    private Product product;    //该订单项所对应的产品

    public OrderItem() {
    }

    public OrderItem(int id, int pid, Integer oid, int uid, int number, Product product) {
        this.id = id;
        this.pid = pid;
        this.oid = oid;
        this.uid = uid;
        this.number = number;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", pid=" + pid +
                ", oid=" + oid +
                ", uid=" + uid +
                ", number=" + number +
                ", product=" + product +
                '}';
    }
}
