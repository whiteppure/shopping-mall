package com.hbsi.shopping.vo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbsi.shopping.vo.entity.UserShopVo;

public interface IUserShopVoService extends IService<UserShopVo>{
	UserShopVo getUserShopByUserId(Integer userId);
}
