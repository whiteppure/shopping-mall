package com.hbsi.shopping.vo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbsi.shopping.vo.entity.UserAddressVo;

public interface IUserAddressVoService extends IService<UserAddressVo>{
	
	
	List<UserAddressVo> getAllUserAddresByUserId(Integer userId);
}
