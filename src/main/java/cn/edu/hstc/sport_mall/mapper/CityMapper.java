package cn.edu.hstc.sport_mall.mapper;

import cn.edu.hstc.sport_mall.pojo.City;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper层-负责城市查询
 */
public interface CityMapper {

    /*
     * 查询市
     */
    @Select("SELECT `city` FROM city WHERE province = #{province} group by city")
    List<String> selCity(String province);

    /*
     * 根据省份和城市查询所有的区
     */
    @Select("SELECT county FROM city WHERE province = #{province} AND city = #{city} group by county")
    List<String> selCountyByProvinceAndCity(@Param("province") String province, @Param("city") String city);

    /*
     * 查询某直辖市的所有区
     */
    @Select("SELECT county FROM city WHERE province = '' AND city = #{city} group by county")
    List<String> selCountyByCity(@Param("city") String city);

    /*
     * 根据省、市、区县查询所有的镇
     */
    @Select("SELECT town FROM city WHERE province = #{province} AND city = #{city} AND county = #{county} group by town")
    List<String> selTownByProvince(@Param("province") String province, @Param("city") String city, @Param("county") String county);

    /*
     * 查询某直辖市下某区县的镇
     */
    @Select("SELECT town FROM city WHERE province = '' AND city = #{city} AND county = #{county} group by town")
    List<String> selTownNotByProvince(@Param("city") String city, @Param("county") String county);

    /*
     * 非直辖市，查询地址id
     */
    @Select("SELECT id FROM city WHERE province = #{province} AND city = #{city} AND county = #{county} AND town = #{town}")
    int selIdByProvince(@Param("province") String province, @Param("city") String city, @Param("county") String county, @Param("town") String town);

    /*
     * 直辖市，查询地址id
     */
    @Select("SELECT id FROM city WHERE province = '' AND city = #{city} AND county = #{county} AND town = #{town}")
    int selIdNotByProvince(@Param("city") String city, @Param("county") String county, @Param("town") String town);

    /*
     * 根据编号，查询城市(在AddreddMapper.xml中被resultmap引用)
     */
    @Select("SELECT province, city, county, town FROM city WHERE id = #{id} LIMIT 1")
    City selCityById(@Param("id") int id);
}
