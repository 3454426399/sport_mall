package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.ProductMapper;
import cn.edu.hstc.sport_mall.pojo.Category;
import cn.edu.hstc.sport_mall.pojo.Product;
import cn.edu.hstc.sport_mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-产品管理
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /*
     * 根据传入的分类编号，查询该分类的所有商品信息
     */
    @Override
    public List<Product> selListForFore(int cid) {
        return productMapper.selListForFore(cid);
    }

    /*
     * 根据传入的分类编号，后台分页查询并按id排序
     */
    @Override
    public List<Product> selListForAdmin(int cid) {
        return productMapper.selListForAdmin(cid);
    }

    /*
     * 为指定分类添加一个新产品
     */
    @Override
    public int insAdd(Product product) {
        return productMapper.insAdd(product);
    }

    /*
     * 根据编号获取单个产品信息
     */
    @Override
    public Product selGet(int id) {
        return productMapper.selGet(id);
    }

    @Override
    public Product selGetNotIncluCategory(int id) {
        return productMapper.selGetNotIncluCategory(id);
    }

    /*
     * 更新指定产品的信息
     */
    @Override
    public int update(Product product) {
        int result = productMapper.update(product);
        redisTemplate.delete("p-" + product.getId());

        return result;
    }

    /*
     * 为分类集合的每一个分类绑定其产品信息
     */
    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories){
            fill(category);
        }
    }

    /*
     * 为每个分类绑定其产品信息
     */
    @Override
    public void fill(Category category) {
        List<Product> productList = productMapper.selListLimitFive(category.getId());

        category.setProducts(productList);
    }

    /*
     * 数据迁移
     */
    @Override
    public List<Product> cp_mysql_to_es(int start, int end) {
        return productMapper.cp_mysql_to_es(start, end);
    }
}
