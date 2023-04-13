package cn.edu.hstc.sport_mall.pojo;

/**
 *  实体类-收货地址
 */
public class Address {

    private int id;    //编号
    private int uid;    //用户编号
    private int city_id;    //城市编号
    private String detail;    //详细地址

    //非数据库字段
    private City city;    //城市地址

    public Address() {
    }

    public Address(int id, int uid, int city_id, String detail, City city) {
        this.id = id;
        this.uid = uid;
        this.city_id = city_id;
        this.detail = detail;
        this.city = city;
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

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", uid=" + uid +
                ", city_id=" + city_id +
                ", detail='" + detail + '\'' +
                ", city=" + city +
                '}';
    }
}
