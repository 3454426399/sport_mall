package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.CategoryMapper;
import cn.edu.hstc.sport_mall.pojo.Category;
import cn.edu.hstc.sport_mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类：负责产品分类管理
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /*
     * 查询当前页面的产品集合
     */
    @Override
    public List<Category> selList() {
        return categoryMapper.selList();
    }

    /*
     * 新增产品分类
     */
    @Override
    public int insAdd(Category category) {
        return categoryMapper.insAdd(category);
    }

    /*
     * 获取单个分类信息
     * 传入 id，返回对应的单个分类信息
     */
    @Override
    public Category selGet(int id) {
        return categoryMapper.selGet(id);
    }

    /*
     * 更新产品分类
     * 根据编号id更新对应的产品分类信息
     */
    @Override
    public int update(Category category) {
        return categoryMapper.update(category);
    }
}
