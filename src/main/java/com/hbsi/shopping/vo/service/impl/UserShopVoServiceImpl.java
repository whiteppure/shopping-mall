package com.hbsi.shopping.vo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.shopping.vo.entity.UserShopVo;
import com.hbsi.shopping.vo.mapper.UserShopVoMapper;
import com.hbsi.shopping.vo.service.IUserShopVoService;

@Service
public class UserShopVoServiceImpl extends ServiceImpl<UserShopVoMapper, UserShopVo> implements IUserShopVoService{

	@Override
	public UserShopVo getUserShopByUserId(Integer userId) {
		return this.baseMapper.getUserShopByUserId(userId);
	}

}
