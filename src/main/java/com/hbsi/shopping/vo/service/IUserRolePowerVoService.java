package com.hbsi.shopping.vo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbsi.shopping.vo.entity.UserPowerVo;

public interface IUserRolePowerVoService extends IService<UserPowerVo>{
	
	Page<UserPowerVo> getAllUserInfoByPage(Page<UserPowerVo> page);
	
	UserPowerVo getUserInfoByUserId(Integer id );
}
