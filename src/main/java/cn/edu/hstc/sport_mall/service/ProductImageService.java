package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.ProductImage;

import java.util.List;

/**
 * Service层接口-产品图片管理
 */
public interface ProductImageService {

    /*
     * 查询产品的图片信息
     */
    List<ProductImage> selList(ProductImage productImage);

    /*
     * 查询单个产品图片信息
     */
    ProductImage selById(int id);
    
    /*
     * 为指定产品添加新图片
     */
    int insAdd(ProductImage productImage);

    /*
     * 删除指定的产品图片
     */
    int delete(int id);

    /*
     * 查询产品的第一张type_single图片
     */
    ProductImage selGetByPid(int pid);

    /*
     * 根据分类编号，查询该分类下所有产品的第一张图片
     */
    List<ProductImage> selFirstImgForCategory(int cid);
}
