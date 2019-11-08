package com.hbsi.shopping.evaluate.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.evaluate.entity.UserEvaluate;
import com.hbsi.shopping.evaluate.service.IUserEvaluateService;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.DateUtils;
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
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@RestController
@RequestMapping("/evaluate/")
@Api(tags="用户评价模块")
public class UserEvaluateController {
	
	private final IUserEvaluateService userEvaluateService;

	public UserEvaluateController(IUserEvaluateService userEvaluateService) {
		this.userEvaluateService = userEvaluateService;
	}

	//分页查询所有评价(status= 1)
	@GetMapping("getAllEvaluate")
	@ApiOperation("分页查询所有评价(status=1,orderByDesc=userId)")
	public Response<IPage<UserEvaluate>> getAllEvaluate(
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size
			){
		try {
			Page<UserEvaluate> page = new Page<UserEvaluate>(current,size);
			IPage<UserEvaluate> userList = userEvaluateService.page(page,new QueryWrapper<UserEvaluate>().eq("status", 1).orderByDesc("usreId"));
			log.debug("查询成功");
			return new  Response<>(userList);
		} catch (Exception e) {
			log.debug("分页查询失败",e.getMessage());
			throw new BaseException(ExceptionEnum.EVALUATE_GET_FILED,"分页查询所有用户评价失败");
		}
	}
	
	//根据用户id分页查询所有评价(status= 1)
	@GetMapping("getAllEvaluateByUserId")
	@ApiOperation("根据用户id分页查询所有评价(status=1,orderByDesc=createTime)")
	public Response<IPage<UserEvaluate>> getAllEvaluateByUserId(
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size,
			@ApiParam("用户id") @RequestParam Integer userId){
		try {
			Page<UserEvaluate> page = new Page<UserEvaluate>(current,size);
			IPage<UserEvaluate> userList = userEvaluateService.page(page,new QueryWrapper<UserEvaluate>().eq("userId", userId).eq("status", 1).orderByDesc("createTime"));
			log.debug("查询成功");
			return new  Response<>(userList);
		} catch (Exception e) {
			log.debug("分页查询失败",e.getMessage());
			throw new BaseException(ExceptionEnum.EVALUATE_GET_FILED,"分页查询用户评价失败");
		}
	}
	
	
	
	
	//根据店铺分页查询所有评价(status= 1)
	@GetMapping("getAllEvaluateByShopId")
	@ApiOperation("根据店铺id分页查询所有评价(status=1,orderByDesc=createTime)")
	public Response<IPage<UserEvaluate>> getAllEvaluateByShopId(
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size,
			@ApiParam("店铺id") @RequestParam Integer shopId){
		try {
			Page<UserEvaluate> page = new Page<UserEvaluate>(current,size);
			IPage<UserEvaluate> userList = userEvaluateService.page(page,new QueryWrapper<UserEvaluate>().eq("shopId", shopId).eq("status", 1).orderByDesc("createTime"));
			log.debug("查询成功");
			return new  Response<>(userList);
		} catch (Exception e) {
			log.debug("分页查询失败",e.getMessage());
			throw new BaseException(ExceptionEnum.EVALUATE_GET_FILED,"分页查询用户评价失败");
		}
	}
	
	
	//添加评价
	@ApiOperation("用户添加评价")
	@PostMapping("addEvaluate")
	public Response<UserEvaluate> addEvaluate(@RequestBody UserEvaluate userEvaluate){
		try {
			userEvaluate.setCreateTime(DateUtils.formatDate(new Date()));
			if(userEvaluateService.save(userEvaluate)) {
				log.debug("添加评价成功");
				return new Response<>();
			}
			else {
				log.debug("添加评价失败");
				throw new BaseException(ExceptionEnum.EVALUATE_ADD_FILED,"添加评论失败");
			}
		} catch (Exception e) {
			log.debug("添加评价失败",e.getMessage());
			throw new BaseException(ExceptionEnum.EVALUATE_ADD_FILED,"添加评价失败");
		}
	}
	
	
	
	//根据用户id删除评价
	@GetMapping("deleteEvaluateByUserId")
	@ApiOperation("根据用户id删除评价")
	public Response<UserEvaluate> deleteEvaluateByUserId(@ApiParam("用户id") @RequestParam Integer userId) {
		if (ObjectUtils.isEmpty(userId)) {
			log.debug("删除用户评价失败");
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "用户id错误");
		}
		try {
			boolean flag = userEvaluateService.update(new UserEvaluate().setEvaluateStatus(-1), new UpdateWrapper<UserEvaluate>(new UserEvaluate().setUserId(userId)));
			if (flag) {
				log.debug("删除用户评价成功");
				return new Response<>();
			} else {
				log.debug("根据用户id删除评价失败");
				throw new BaseException(ExceptionEnum.EVALUATE_DELETE_FILED, "根据用户id删除评价失败");
			}
		} catch (Exception e) {
			log.debug("根据用户id删除评价失败", e.getMessage());
			throw new BaseException(ExceptionEnum.EVALUATE_DELETE_FILED, "根据用户id删除评价失败");
		}
	}
	
	
	//根据评价id删除评价
	@GetMapping("deleteEvaluateById")
	@ApiOperation("根据评价id删除评价")
	public Response<UserEvaluate> deleteEvaluateById(@ApiParam("评价id") @RequestParam Integer id){
		if (ObjectUtils.isEmpty(id)) {
			log.debug("删除用户评价失败");
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "评价id错误");
		}
		try {
			boolean flag = userEvaluateService.update(new UserEvaluate().setEvaluateStatus(-1), new UpdateWrapper<UserEvaluate>(new UserEvaluate().setId(id)));
			if (flag) {
				log.debug("删除用户评价成功");
				return new Response<>();
			} else {
				log.debug("根据评价id删除评价失败");
				throw new BaseException(ExceptionEnum.EVALUATE_DELETE_FILED, "根据评价id删除评价失败");
			}
		} catch (Exception e) {
			log.debug("根据评价id删除评价失败", e.getMessage());
			throw new BaseException(ExceptionEnum.EVALUATE_DELETE_FILED, "根据评价id删除评价失败");
		}
	}
	
	
	
	
	
}
