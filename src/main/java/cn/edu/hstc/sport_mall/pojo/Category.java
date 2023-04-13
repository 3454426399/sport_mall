package cn.edu.hstc.sport_mall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 *  实体类-产品分类
 */
public class Category implements Serializable {
    private int id;    //分类编号
    private String name;    //分类名称

    //非数据库字段
    private List<Property> propertyList;    //该分类的属性集合
    private List<Product> products;    //该分类下的所有产品

    public Category() {
    }

    public Category(int id, String name, List<Property> propertyList, List<Product> products) {
        this.id = id;
        this.name = name;
        this.propertyList = propertyList;
        this.products = products;
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

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /*
     * 这个函数有用到，在productsByCategory.jsp页面中
     */
    public int getProductNumber(){
        return products.size();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", propertyList=" + propertyList +
                ", products=" + products +
                '}';
    }
}