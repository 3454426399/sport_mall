package cn.edu.hstc.sport_mall.service;

import java.util.List;

/**
 * Service层接口-城市查询
 */
public interface CityService {

    /*
     * 查询市
     */
    List<String> selGetCity(String province);

    /*
     * 查询区县
     */
    List<String> selCountyByProvinceAndCity(String province, String city);

    /*
     * 查询某区县
     */
    List<String> selCountyByCity(String city);

    /*
     * 查询城镇
     */
    List<String> selTownByProvince(String province, String city, String county);

    /*
     * 查询城镇
     */
    List<String> selTownNotByProvince(String city, String county);

    /*
     * 非直辖市，查询id
     */
    int selIdByProvince(String province, String city, String county, String town);

    /*
     * 直辖市，查询id
     */
    int selIdNotByProvince(String city, String county, String town);
}
