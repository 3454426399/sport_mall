package cn.edu.hstc.sport_mall.pojo;

import java.io.Serializable;
import java.util.Objects;

/**
 *  实体类-分类属性
 */
public class Property implements Serializable {

    private int id;    //编号
    private String name;    //属性名称

    public Property() {
    }

    public Property(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return name.equals(property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
