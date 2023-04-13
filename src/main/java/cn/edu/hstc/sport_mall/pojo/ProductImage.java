package cn.edu.hstc.sport_mall.pojo;

import java.io.Serializable;

/**
 *  实体类-产品图片
 */
public class ProductImage implements Serializable {

    private int id;    //编号
    private int pid;    //产品编号
    private String type;    //图片类型

    public ProductImage() {
    }

    public ProductImage(int id, int pid, String type) {
        this.id = id;
        this.pid = pid;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "productImage{" +
                "id=" + id +
                ", pid=" + pid +
                ", type='" + type + '\'' +
                '}';
    }
}
