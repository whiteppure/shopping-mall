package com.hbsi.shopping.address.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hbsi.shopping.address.entity.UserAddress;
import com.hbsi.shopping.address.mapper.UserAddressMapper;
import com.hbsi.shopping.address.service.IUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;


    @Transactional
    public boolean deleteAddressById(Integer id ){
        int flag = userAddressMapper.delete(new UpdateWrapper<UserAddress>().eq("id", id));
        if (flag>0){
            log.debug("删除成功");
            return true;
        }
        return false;
    }


}
