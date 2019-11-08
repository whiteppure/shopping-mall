package com.hbsi.shopping.vo.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.shopping.vo.entity.UserPowerVo;
import com.hbsi.shopping.vo.mapper.UserRolePowerVoMapper;
import com.hbsi.shopping.vo.service.IUserRolePowerVoService;

@Service
public class UserRolePowerVoServiceImpl extends ServiceImpl<UserRolePowerVoMapper, UserPowerVo> implements IUserRolePowerVoService{

	@Override
	public Page<UserPowerVo> getAllUserInfoByPage(Page<UserPowerVo> page) {
		return page.setRecords(this.baseMapper.getAllUserInfoByPage(page));
	}

	@Override
	public UserPowerVo getUserInfoByUserId(Integer id) {
		return this.baseMapper.getUserInfoByUserId(id);
	}

}
