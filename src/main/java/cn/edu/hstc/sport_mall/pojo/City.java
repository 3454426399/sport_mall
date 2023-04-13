package cn.edu.hstc.sport_mall.pojo;

/**
 *  实体类-城市
 */
public class City {

    private int id;    //城市编号
    private String province;    //省份
    private String city;    //市区
    private String county;    //区县
    private String town;    //城镇

    public City() {
    }

    public City(int id, String province, String city, String county, String town) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.county = county;
        this.town = town;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", town='" + town + '\'' +
                '}';
    }
}
