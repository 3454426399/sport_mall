package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.ProductImageMapper;
import cn.edu.hstc.sport_mall.pojo.ProductImage;
import cn.edu.hstc.sport_mall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-产品图片管理
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;

    /*
     * 根据传入的产品编号和图片类型查询产品的图片信息
     */
    @Override
    public List<ProductImage> selList(ProductImage productImage) {
        return productImageMapper.selList(productImage);
    }

    /*
     * 根据产品图片编号，查询单个产品图片信息
     */
    @Override
    public ProductImage selById(int id) {
        return productImageMapper.selById(id);
    }

    /*
     * 根据传入的产品编号 & 图片类型，为指定产品添加新图片
     */
    @Override
    public int insAdd(ProductImage productImage) {
        return productImageMapper.insAdd(productImage);
    }

    /*
     * 删除指定的产品图片
     */
    @Override
    public int delete(int id) {
        return productImageMapper.delete(id);
    }

    /*
     * 根据产品编号，查询产品的第一张type_single图片
     */

    @Override
    public ProductImage selGetByPid(int pid) {
        return productImageMapper.selByPid(pid);
    }

    /*
     * 根据分类编号，查询该分类下所有产品的第一张图片
     */
    @Override
    public List<ProductImage> selFirstImgForCategory(int cid) {
        return productImageMapper.selFirstImgForCategory(cid);
    }
}
