package com.hbsi.shopping.power.service.impl;

import com.hbsi.shopping.power.entity.UserPower;
import com.hbsi.shopping.power.mapper.UserPowerMapper;
import com.hbsi.shopping.power.service.IUserPowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Service
public class UserPowerServiceImpl extends ServiceImpl<UserPowerMapper, UserPower> implements IUserPowerService {

	@Override
	public List<String> getAllPowerName() {
		return this.baseMapper.getAllPowerName();
	}
	
}
