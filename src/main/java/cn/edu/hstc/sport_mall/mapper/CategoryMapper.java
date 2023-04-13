package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper层-负责产品分类管理
 */
public interface CategoryMapper {

    /*
     * 根据起始位置 & 欲查询的个数，查询出当前页面的分类集合
     */
    @Select("SELECT * FROM category ORDER BY id")
    List<Category> selList();

    /*
     * 根据管理员提交的信息，新增一个产品分类
     */
    @Insert("INSERT INTO category VALUES(DEFAULT, #{name})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    int insAdd(Category category);

    /*
     * 根据产品分类编号，返回该产品分类的信息
     */
    @Select("SELECT * FROM category WHERE id = #{id} LIMIT 1")
    Category selGet(int id);

    /*
     * 修改编号id对应的产品分类的信息
     */
    @Update("UPDATE category SET name = #{name} WHERE id = #{id}")
    int update(Category category);
}
