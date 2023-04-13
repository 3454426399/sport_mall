package cn.edu.hstc.sport_mall.pojo;

import java.io.Serializable;

/**
 *  实体类-产品属性值
 */
public class PropertyValue implements Serializable {

    private int id;    //编号
    private int pid;    //产品编号
    private int ptid;    //属性编号
    private String value;    //属性值

    /* 非数据库字段，属性编号对应的属性信息 */
    private Property property;

    public PropertyValue() {
    }

    public PropertyValue(int id, int pid, int ptid, String value, Property property) {
        this.id = id;
        this.pid = pid;
        this.ptid = ptid;
        this.value = value;
        this.property = property;
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

    public int getPtid() {
        return ptid;
    }

    public void setPtid(int ptid) {
        this.ptid = ptid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }



    @Override
    public String toString() {
        return "PropertyValue{" +
                "id=" + id +
                ", pid=" + pid +
                ", ptid=" + ptid +
                ", value='" + value + '\'' +
                ", property=" + property +
                '}';
    }
}
