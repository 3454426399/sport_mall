package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.PropertyMapper;
import cn.edu.hstc.sport_mall.pojo.Property;
import cn.edu.hstc.sport_mall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类：分类属性管理
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    /*
     * 查询当前页面的分类属性
     */
    @Override
    public List<Property> selList(int cid) {
        return propertyMapper.selList(cid);
    }

    /*
     * 为指定分类添加新属性
     */
    @Override
    public int insAdd(String name, int cid) {
        return propertyMapper.insAdd(name, cid);
    }

    /*
     * 获取单个属性信息
     * 根据编号id对应的属性信息
     */
    @Override
    public Property selGet(int id) {
        return propertyMapper.selGet(id);
    }

    /*
     * 更新属性信息
     * 根据编号id更新对应的属性信息
     */
    @Override
    public int updata(Property property) {
        return propertyMapper.update(property);
    }

    /*
     * 删除指定分类的指定属性
     */
    @Override
    public int delCategoryProperty(int cid, int ptid) {
        return propertyMapper.delCategoryProperty(cid, ptid);
    }
}
