package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.PropertyValueMapper;
import cn.edu.hstc.sport_mall.pojo.PropertyValue;
import cn.edu.hstc.sport_mall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-产品属性值设置
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    /*
     * 根据传入的产品编号，查询该产品的属性值集合
     */
    @Override
    public List<PropertyValue> selByPid(int pid) {
        return propertyValueMapper.selByPid(pid);
    }
    
    /*
     * 根据产品属性值的编号，修改对应商品的属性值
     */
    @Override
    public int update(String value, int id) {
        return propertyValueMapper.update(value, id);
    }

    /*
     * 为指定产品添加新属性，并设置属性值
     */
    @Override
    public int insAdd(PropertyValue propertyValue) {
        return propertyValueMapper.insAdd(propertyValue);
    }

    /*
     * 删除产品的属性
     */
    @Override
    public int delProductProperty(int pid, int ptid) {
        return propertyValueMapper.delProductProperty(pid, ptid);
    }
}
