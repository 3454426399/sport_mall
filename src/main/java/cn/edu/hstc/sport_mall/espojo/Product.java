package cn.edu.hstc.sport_mall.espojo;

import cn.edu.hstc.sport_mall.pojo.ProductImage;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 *  es实体类-产品
 */
@Document(indexName = "product", type = "product", shards = 5, replicas = 0)
public class Product implements Serializable {
    @Field(type = FieldType.Integer, index = false)
    private int id;    //产品编号
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;    //产品名称
    @Field(type = FieldType.Float, index = false)
    private float promotePrice;    //促销价
    @Field(type = FieldType.Integer)
    private int saleCount;    //产品月销量
    @Field(type = FieldType.Integer, index = false)
    private int reviewCount;    //产品的评价数量
    @Field(type = FieldType.Object)
    private ProductImage firstProductImage;

    public Product() {
    }

    public Product(int id, String name, float promotePrice, int saleCount, int reviewCount) {
        this.id = id;
        this.name = name;
        this.promotePrice = promotePrice;
        this.saleCount = saleCount;
        this.reviewCount = reviewCount;
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

    public float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", promotePrice=" + promotePrice +
                ", saleCount=" + saleCount +
                ", reviewCount=" + reviewCount +
                ", firstProductImage=" + firstProductImage +
                '}';
    }
}