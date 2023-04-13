package cn.edu.hstc.sport_mall.service.impl;

import cn.edu.hstc.sport_mall.mapper.AddressMapper;
import cn.edu.hstc.sport_mall.pojo.Address;
import cn.edu.hstc.sport_mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service层实现类-处理与常用地址相关的业务
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /*
     * 添加一个常用地址
     */
    @Override
    public int insAdd(Address address) {
        return addressMapper.insAdd(address);
    }

    /*
     * 判断地址是否已经添加过了
     */
    @Override
    public Address selExist(Address address) {
        return addressMapper.selExist(address);
    }

    /*
     * 根据用户编号，查询其常用地址
     */
    @Override
    public List<Address> selAddressByUser(int uid) {
        return addressMapper.selAddressByUser(uid);
    }
}
