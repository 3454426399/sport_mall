package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Category;
import cn.edu.hstc.sport_mall.pojo.Product;

import java.util.List;

/**
 * Service层接口-产品管理
 */
public interface ProductService {

    /*
     * 根据传入的分类编号，查询该分类的所有商品信息
     */
    List<Product> selListForFore(int cid);

    /*
     * 根据传入的分类编号，后台分页查询并按id排序
     */
    List<Product> selListForAdmin(int cid);

    /*
     * 为指定分类添加一个新产品
     */
    int insAdd(Product product);

    /*
     * 根据编号获取单个产品信息，包括所属分类信息
     */
    Product selGet(int id);

    /*
     * 根据编号获取单个产品信息，但不包括所属分类信息
     */
    Product selGetNotIncluCategory(int id);

    /*
     * 更新指定产品的信息
     */
    int update(Product product);

    /*
     * 为每个分类绑定其产品信息
     */
    public void fill(Category category);

    /*
     * 为分类集合的每一个分类绑定其产品信息
     */
    public void fill(List<Category> categories);

    /*
     * 数据迁移
     */
    List<Product> cp_mysql_to_es(int start, int end);
}
