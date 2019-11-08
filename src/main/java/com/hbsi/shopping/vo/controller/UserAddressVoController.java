package com.hbsi.shopping.vo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.Response;
import com.hbsi.shopping.vo.entity.UserAddressVo;
import com.hbsi.shopping.vo.service.IUserAddressVoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags="用户收货信息模块地址")
@RequestMapping("/userAddressVo/")
public class UserAddressVoController {
	
	@Autowired
	private IUserAddressVoService userAddressVoService;

	
	@GetMapping("getUserAddressByUserId")
	@ApiOperation("根据用户id查询用户收货地址信息")
	public Response<List<UserAddressVo>> getUserAddressByUserId(@ApiParam("用户id") @RequestParam Integer userId){
		try {
			if(ObjectUtils.isEmpty(userId)) {
				log.debug("查询错误");
				throw new BaseException(ExceptionEnum.USER_ADDRESS_GET_FILDE,"userId参数错误");
			}
			List<UserAddressVo> addressList = userAddressVoService.getAllUserAddresByUserId(userId);
			log.debug("查询成功");
			return new Response<>(addressList);
		} catch (Exception e) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.USER_ADDRESS_GET_FILDE,"查用户的收货地址失败,多表查询失败");
		}
	}
	
	
	
}
