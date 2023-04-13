package cn.edu.hstc.sport_mall.pojo;

/**
 *  实体类-账户
 */
public class Account {

    private int id;    //编号
    private int uid;    //用户编号
    private float money;    //金额
    private String password;    //支付密码

    public Account() {
    }

    public Account(int id, int uid, float money, String password) {
        this.id = id;
        this.uid = uid;
        this.money = money;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", uid=" + uid +
                ", money=" + money +
                ", password='" + password + '\'' +
                '}';
    }
}
