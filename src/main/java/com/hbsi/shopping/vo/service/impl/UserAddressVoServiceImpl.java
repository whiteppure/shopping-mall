package com.hbsi.shopping.vo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.shopping.vo.entity.UserAddressVo;
import com.hbsi.shopping.vo.mapper.UserAddressVoMapper;
import com.hbsi.shopping.vo.service.IUserAddressVoService;

@Service
public class UserAddressVoServiceImpl extends ServiceImpl<UserAddressVoMapper, UserAddressVo> implements IUserAddressVoService{

	@Override
	public List<UserAddressVo> getAllUserAddresByUserId(Integer userId) {
		return this.baseMapper.getAllUserAddresByUserId(userId);
	}

}
