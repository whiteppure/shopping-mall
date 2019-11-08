package com.hbsi.shopping.power.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.power.entity.UserPower;
import com.hbsi.shopping.power.service.IUserPowerService;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@RestController
@RequestMapping("/power/")
@Api(tags="权限信息模块")
public class UserPowerController {
	
	@Autowired
	private IUserPowerService userPowerService;
	
	
	/**
	 * 不分页查询所有权限
	 * @author while
	 * @data 下午3:31:29
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("不分页查询所有权限名称")
	@GetMapping("getAllPower")
	public Response<List<String>> getAllPower(){
		try {
			List<String> powerNameList = userPowerService.getAllPowerName();
			log.debug("查询成功");
			return new Response<> (powerNameList);
		} catch (Exception e) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.POWER_GET_FILED,"不分页查询所有的权限名称失败");
		}
	}
	
	
	
	/**
	 * 根据权限id查询权限信息
	 * @author while
	 * @data 下午9:12:30
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根据权限id查询权限信息")
	@GetMapping("getPowerById")
	public Response<UserPower> getPowerById(@ApiParam("权限id") @RequestParam Integer id){
		try {
			if(ObjectUtils.isEmpty(id)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.POWER_GET_FILED,"权限id错误");
			}
			UserPower power = userPowerService.getOne(new QueryWrapper<UserPower>().eq("id", id));
			log.debug("查询成功");
			return new Response<> (power);
		} catch (Exception e) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.POWER_GET_FILED,"根据id查询权限信息失败");
		}
	}
	
	
	
	/**
	 * 根据用户id修改权限信息
	 * @author while
	 * @data 下午7:14:59
	 * @param  @param userPower
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根据用户id修改权限信息")
	@PostMapping("addUserPower")
	public Response<UserPower> updUserPowerByUserId(@RequestBody UserPower userPower){
		try {
			UserPower power= new UserPower();
			power.setPowerDescription(userPower.getPowerDescription());
			power.setPowerName(userPower.getPowerName());
			//根据用户id修改用户权限
			boolean flag = userPowerService.update(power,new UpdateWrapper<UserPower>(new UserPower().setUserId(userPower.getUserId())));
			if(flag){
				log.debug("修改权限成功");
				return new Response<>();
			}else {
				log.debug("修改权限失败");
				throw new BaseException(ExceptionEnum.POWER_UPDATE_FILED);
			}
		} catch (Exception e) {
			log.debug("修改权限失败",e.getMessage());
			throw new BaseException(ExceptionEnum.POWER_UPDATE_FILED);
		}
	}
	
	
	
	
	
	/**
	 * 根据权限id修改权限信息
	 * @author while
	 * @data 下午7:18:12
	 * @param  @param userPower
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根据权限id修改权限信息")
	@PostMapping("updUserPower")
	public Response<UserPower> updUserPowerById(@RequestBody UserPower userPower){
		try {
			UserPower power= new UserPower();
			power.setPowerDescription(userPower.getPowerDescription());
			power.setPowerName(userPower.getPowerName());
			//根据用户id修改用户权限
			boolean flag = userPowerService.update(power,new UpdateWrapper<UserPower>().eq("id", userPower.getId()));
			if(flag){
				log.debug("修改权限成功");
				return new Response<>();
			}else {
				log.debug("修改权限失败");
				throw new BaseException(ExceptionEnum.POWER_UPDATE_FILED);
			}
		} catch (Exception e) {
			log.debug("修改权限失败",e.getMessage());
			throw new BaseException(ExceptionEnum.POWER_UPDATE_FILED);
		}
	}
	
	
	/**
	 * 根据权限id删除用户权限
	 * @author while
	 * @data 下午7:50:42
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根据权限id删除用户权限")
	@GetMapping("deleteUserPowerById")
	public Response<UserPower> deleteUserPowerById(@ApiParam("权限id") @RequestParam Integer id ){
		try {
			if(ObjectUtils.isEmpty(id)) {
				log.debug("删除权限失败");
				throw new  BaseException(ExceptionEnum.POWER_DELETE_FILED,"权限id错误");
			}
			boolean flag = userPowerService.remove(new UpdateWrapper<UserPower>().eq("id", id));
			if(flag) {
				log.debug("删除权限成功");
				return new Response<>();
			}else {
				log.debug("删除权限失败");
				throw new  BaseException(ExceptionEnum.POWER_DELETE_FILED);
			}
		} catch (Exception e) {
			log.debug("删除权限失败",e.getMessage());
			throw new  BaseException(ExceptionEnum.POWER_DELETE_FILED);
		}
	}
	
	
}
