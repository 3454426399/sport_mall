package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Property;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper层：分类属性管理
 */
public interface PropertyMapper {

    /*
     * 根据传入的产品分类编号，查询该产品分类的属性集合
     */
    List<Property> selList(@Param("cid") int cid);

    /*
     * 调用存储过程，根据传入的属性名和产品编号，先往属性表中添加属性，再为产品添加该属性
     */
    int insAdd(@Param("name") String name, @Param("cid") int cid);

    /*
     * 根据传入的编号，查询对应的属性信息
     */
    @Select("SELECT * FROM property WHERE id = #{id} LIMIT 1")
    Property selGet(@Param("id") int id);

    /*
     * 根据传入的属性编号，更新该属性的信息
     */
    @Update("UPDATE property SET name = #{name} WHERE id = #{id} LIMIT 1")
    int update(Property property);

    /*
     * 删除指定分类的指定属性
     */
    @Delete("DELETE FROM category_property WHERE cid = #{cid} AND ptid = #{ptid}")
    int delCategoryProperty(@Param("cid") int cid, @Param("ptid") int ptid);
}
