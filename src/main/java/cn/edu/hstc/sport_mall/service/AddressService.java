package cn.edu.hstc.sport_mall.service;

import cn.edu.hstc.sport_mall.pojo.Address;

import java.util.List;

/**
 * Service层接口-处理与常用地址管理相关的业务
 */
public interface AddressService {

    /*
     * 添加一个常用地址
     */
    int insAdd(Address address);

    /*
     * 判断地址是否已经添加过了
     */
    Address selExist(Address address);

    /*
     * 查询用户的常用地址
     */
    List<Address> selAddressByUser(int uid);
}
