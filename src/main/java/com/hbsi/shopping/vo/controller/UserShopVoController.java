package com.hbsi.shopping.vo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.Response;
import com.hbsi.shopping.vo.entity.UserShopVo;
import com.hbsi.shopping.vo.service.IUserShopVoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/userShopVo/")
@Api(tags="用户店铺信息模块")
public class UserShopVoController {
	
	@Autowired
	private IUserShopVoService userShopVoService;
	
	@GetMapping("getAllUserShopInfoByUserId")
	@ApiOperation("根据用户id查询用户和店铺的信息")
	public Response<UserShopVo> getAllUserShopInfoByUserId(@ApiParam("用户id") @RequestParam Integer userId){
		try {
			if (ObjectUtils.isEmpty(userId)) {
				log.debug("查询错误");
				throw new BaseException(ExceptionEnum.USER_SHOP_GET_FILED,"用户id参数错误");
			}
			UserShopVo userShop = userShopVoService.getUserShopByUserId(userId);
			log.debug("查询成功");
			return new Response<>(userShop);
		} catch (Exception e) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.USER_SHOP_GET_FILED,"根据用户id查询用户和店铺的信息失败");
		}
	}
	
	
	
	
}
