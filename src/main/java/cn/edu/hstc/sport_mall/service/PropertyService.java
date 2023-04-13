package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Property;

import java.util.List;

/**
 * Service层接口：分类属性管理
 */
public interface PropertyService {

    /*
     * 查询当前页面的产品集合
     */
    List<Property> selList(int cid);

    /*
     * 为指定分类添加新属性
     */
    int insAdd(String name, int cid);

    /*
     * 获取单个属性信息
     */
    Property selGet(int id);

    /*
     * 更新属性信息
     */
    int updata(Property property);

    /*
     * 删除指定分类的指定属性
     */
    int delCategoryProperty(int cid, int ptid);
}
