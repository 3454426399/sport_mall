package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.PropertyValue;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mapper层-产品属性值设置
 */
public interface PropertyValueMapper {

    /*
     * 查询指定产品的属性值信息
     */
    List<PropertyValue> selByPid(@Param("pid") int pid);
    
    /*
     * 修改指定编号的产品属性值
     */
    @Update("UPDATE propertyvalue SET value = #{value} WHERE id = #{id}")
    int update(@Param("value") String value, @Param("id") int id);

    /*
     * 为指定产品添加新属性，并设置属性值
     */
    @Insert("INSERT INTO propertyvalue VALUES(DEFAULT, #{pid}, #{ptid}, #{value})")
    int insAdd(PropertyValue propertyValue);

    /*
     * 删除产品的属性
     */
    @Delete("DELETE FROM propertyvalue WHERE pid = #{pid} AND ptid = #{ptid}")
    int delProductProperty(@Param("pid") int pid, @Param("ptid") int ptid);
}
