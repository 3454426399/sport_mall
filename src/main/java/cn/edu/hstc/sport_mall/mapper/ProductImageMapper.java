package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mapper层-产品图片管理
 */
public interface ProductImageMapper {

    /*
     * 根据传入的产品编号 & 图片类型查询对应的产品图片集合
     */
    @Select("SELECT * FROM productimage WHERE pid = #{pid} AND type = #{type} ORDER BY id DESC")
    List<ProductImage> selList(ProductImage productImage);

    /*
     * 根据传入的图片编号，查询指定的产品图片信息
     */
    @Select("SELECT * FROM productimage WHERE id = #{id} LIMIT 1")
    ProductImage selById(@Param("id") int id);

    /*
     * 根据传入的产品编号 & 图片类型，为指定产品添加新图片
     */
    @Insert("INSERT INTO productimage VALUES(DEFAULT, #{pid}, #{type})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    int insAdd(ProductImage productImage);

    /*
     * 根据传入的图片编号，删除指定的图片
     */
    @Delete("DELETE FROM productimage WHERE id = #{id}")
    int delete(@Param("id") int id);

    /*
     * 根据产品编号，查询该产品的第一张 type_single类型的图片
     */
    @Select("SELECT * FROM productimage WHERE pid = #{pid} AND type = 'type_single' ORDER BY id LIMIT 1")
    ProductImage selByPid(int pid);

    /*
     * 根据分类编号，查询该分类下所有产品的第一张图片
     */
    @Select("select pi.id id from product p " +
            "inner join (select pid, min(id) id  from productimage where pid in (select id from product where cid = #{cid}) group by pid) pi " +
            "on p.id = pi.pid " +
            "where cid = #{cid} " +
            "order by saleCount desc")
    List<ProductImage> selFirstImgForCategory(@Param("cid") int cid);
}
