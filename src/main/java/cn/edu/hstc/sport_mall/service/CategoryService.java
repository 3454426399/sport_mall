package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Category;

import java.util.List;

/**
 * Service层接口：负责产品分类管理
 */
public interface CategoryService {

    /*
     * 查询当前页面的产品集合
     */
    List<Category> selList();

    /*
     * 新增产品分类
     */
    int insAdd(Category category);

    /*
     * 获取单个分类信息
     */
    Category selGet(int id);

    /*
     * 更新产品分类
     */
    int update(Category category);
}
