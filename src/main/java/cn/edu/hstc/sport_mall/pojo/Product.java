package cn.edu.hstc.sport_mall.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  实体类-产品
 */
public class Product implements Serializable {
    private int id;    //产品编号
    private String name;    //产品名称
    private String subTitle;    //小标题
    private float originalPrice;    //价格
    private float promotePrice;    //促销价
    private int stock;    //库存
    private int cid;    //产品所属分类
    private Date createDate;    //上市时间
    private int saleCount;    //产品月销量
    private int reviewCount;    //产品的评价数量

    /* 非数据库字段，产品所属的分类信息 */
    private Category category;
    /* 非数据库字段，产品图片 */
    private ProductImage firstProductImage;
    /* 产品的单个图片集合 */
    private List<ProductImage> singleProductList;
    /* 产品的详情图片集合 */
    private List<ProductImage> detailProductList;

    public Product() {
    }

    public Product(int id, String name, String subTitle, float originalPrice, float promotePrice, int stock, int cid, Date createDate, int saleCount, int reviewCount, Category category, ProductImage firstProductImage, List<ProductImage> singleProductList, List<ProductImage> detailProductList) {
        this.id = id;
        this.name = name;
        this.subTitle = subTitle;
        this.originalPrice = originalPrice;
        this.promotePrice = promotePrice;
        this.stock = stock;
        this.cid = cid;
        this.createDate = createDate;
        this.saleCount = saleCount;
        this.reviewCount = reviewCount;
        this.category = category;
        this.firstProductImage = firstProductImage;
        this.singleProductList = singleProductList;
        this.detailProductList = detailProductList;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public List<ProductImage> getSingleProductList() {
        return singleProductList;
    }

    public void setSingleProductList(List<ProductImage> singleProductList) {
        this.singleProductList = singleProductList;
    }

    public List<ProductImage> getDetailProductList() {
        return detailProductList;
    }

    public void setDetailProductList(List<ProductImage> detailProductList) {
        this.detailProductList = detailProductList;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", originalPrice=" + originalPrice +
                ", promotePrice=" + promotePrice +
                ", stock=" + stock +
                ", cid=" + cid +
                ", createDate=" + createDate +
                ", saleCount=" + saleCount +
                ", category=" + category +
                ", firstProductImage=" + firstProductImage +
                ", singleProductList=" + singleProductList +
                ", detailProductList=" + detailProductList +
                ", reviewCount=" + reviewCount +
                '}';
    }
}