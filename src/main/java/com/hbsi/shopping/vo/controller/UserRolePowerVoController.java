package com.hbsi.shopping.vo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.Response;
import com.hbsi.shopping.vo.entity.UserPowerVo;
import com.hbsi.shopping.vo.service.IUserRolePowerVoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags="用户信息模块")
@RequestMapping("/userRolePowerVo/")
public class UserRolePowerVoController {
	
	@Autowired
	private IUserRolePowerVoService userRolePowerVoService;
	
	
	
	@ApiOperation("分页查询所有用户信息")
	@GetMapping("userInfo")
	public Response<Page<UserPowerVo>> getAllUserInfoBypage(
			@ApiParam("当前页") @RequestParam Integer current ,
			@ApiParam("每页数量") @RequestParam Integer size
			){
		try {
			Page<UserPowerVo>	page = new Page<UserPowerVo>(current,size);
			Page<UserPowerVo> userInfoList = userRolePowerVoService.getAllUserInfoByPage(page);
			log.debug("查询成功");
			return new Response<>(userInfoList);
		} catch (Exception e) {
			log.debug("查询用户信息失败",e.getMessage());
			throw new BaseException(ExceptionEnum.USER_ROLE_POWER_GET_FILDE,"分页查询所有用户信息,多表查询失败");
		}
	}
	
	
	/**
	 * 根据用户id查询用户信息失败
	 * @author while
	 * @data 下午5:18:52	
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@GetMapping("getUserInfoByUserId")
	@ApiOperation("根据用户id查询用户信息")
	public Response<UserPowerVo> getUserInfoByUserId(@ApiParam("用户id") @RequestParam Integer id ){
		try {
			if(ObjectUtils.isEmpty(id)) {
				log.debug("查询错误");
				throw new BaseException(ExceptionEnum.USER_ROLE_POWER_GET_FILDE,"用户id错误");
			}
			UserPowerVo userInfo = userRolePowerVoService.getUserInfoByUserId(id);
			log.debug("查询成功");
			return new Response<>(userInfo);
		} catch (Exception e) {
			log.debug("查询用户信息失败",e.getMessage());
			throw new BaseException(ExceptionEnum.USER_ROLE_POWER_GET_FILDE,"根据用户id查询用户信息失败,多表查询失败");
		}
	}
	
}
