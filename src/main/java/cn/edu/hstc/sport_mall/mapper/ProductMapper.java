package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper层-产品管理
 */
public interface ProductMapper {

    /*
     * 根据传入的分类编号，查询该分类的前5个商品信息
     */
    List<Product> selListLimitFive(@Param("cid") int cid);


    /*
     * 根据传入的分类编号，查询该分类的所有商品信息
     */
    @Select("SELECT id, `name`, saleCount, reviewCount, promotePrice, createDate FROM product WHERE cid = #{cid} ORDER BY saleCount desc")
    List<Product> selListForFore(@Param("cid") int cid);

    /*
     * 根据传入的分类编号，后台分页查询并按id排序
     */
    List<Product> selListForAdmin(@Param("cid") int cid);

    /*
     * 根据传入的信息，为指定的产品分类添加一个新产品
     */
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("INSERT INTO product VALUES(DEFAULT,#{name},#{subTitle},#{originalPrice},#{promotePrice},#{stock},#{cid},#{createDate}, 0, 0)")
    int insAdd(Product product);

    /*
     * 根据编号获取单个产品信息，同时根据该产品的分类编号，查询所属分类的信息
     */
    Product selGet(@Param("id") int id);

    /*
     * 根据编号获取单个产品信息，同时根据该产品的分类编号，但不查询所属分类的信息
     */
    @Select("SELECT id, `name`, subTitle, originalPrice, promotePrice, stock, saleCount FROM product WHERE id = #{id} LIMIT 1")
    Product selGetNotIncluCategory(@Param("id") int id);

    /*
     * 更新指定编号的产品的信息
     */
    int update(Product product);

    /*
     * 数据迁移
     */
    List<Product> cp_mysql_to_es(@Param("start") int start, @Param("end") int end);
}
