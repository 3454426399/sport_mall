package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.CityMapper;
import cn.edu.hstc.sport_mall.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-负责城市查询
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    /*
     * 根据省份查询其所有城市
     */
    @Override
    public List<String> selGetCity(String province) {
        return cityMapper.selCity(province);
    }

    /*
     * 根据省份和城市查询对应的区和县
     */
    @Override
    public List<String> selCountyByCity(String city) {
        return cityMapper.selCountyByCity(city);
    }

    /*
     * 查询某直辖市的所有区县
     */
    @Override
    public List<String> selCountyByProvinceAndCity(String province, String city) {
        return cityMapper.selCountyByProvinceAndCity(province, city);
    }

    /*
     * 根据省、市、区县查询所有的镇
     */
    @Override
    public List<String> selTownByProvince(String province, String city, String county) {
        return cityMapper.selTownByProvince(province, city, county);
    }

    /*
     * 查询某直辖市下某区县的镇
     */
    @Override
    public List<String> selTownNotByProvince(String city, String county) {
        return cityMapper.selTownNotByProvince(city, county);
    }

    /*
     * 非直辖市，查询id
     */
    @Override
    public int selIdByProvince(String province, String city, String county, String town) {
        return cityMapper.selIdByProvince(province, city, county, town);
    }

    /*
     * 直辖市，查询id
     */
    @Override
    public int selIdNotByProvince(String city, String county, String town) {
        return cityMapper.selIdNotByProvince(city, county, town);
    }
}
