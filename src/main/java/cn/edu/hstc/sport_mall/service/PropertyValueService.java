package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.PropertyValue;

import java.util.List;

/**
 * Service层接口-产品属性值设置
 */
public interface PropertyValueService {

    /*
     * 查询指定产品的属性值信息
     */
    List<PropertyValue> selByPid(int pid);

    /*
     * 修改指定编号的产品属性值
     */
    int update(String value, int id);

    /*
     * 为指定产品添加新属性，并设置属性值
     */
    int insAdd(PropertyValue propertyValue);

    /*
     * 删除产品的属性
     */
    int delProductProperty(int pid, int ptid);
}
