package com.hbsi.shopping.role.controller;


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
import com.hbsi.shopping.role.entity.UserRole;
import com.hbsi.shopping.role.service.IUserRoleService;
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
@RequestMapping("/role/")
@Api(tags="用户角色模块")
public class UserRoleController {
	@Autowired
	private IUserRoleService  userRoleService;
	
	
	/**
	 * 查询所有的角色名称
	 * @author while
	 * @data 下午9:00:45
	 * @param  @return
	 * @description TODO
	 */
	@GetMapping("getAllRoleName")
	@ApiOperation("查询所有的角色名称")
	public Response<List<String>> getAllRoleName(){
		try {
			List<String> allRoleName = userRoleService.getAllRoleName();
			log.debug("查询成功");
			return new Response<>(allRoleName);
		} catch (Exception e) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.ROLE_GET_FILED,"查询所有的角色名称失败");
		}
	}
	
	/**
	 * 根据角色id查询角色信息
	 * @author while
	 * @data 下午9:15:54
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根据角色id查询角色信息")
	@GetMapping("getRoleById")
	public Response<UserRole> getRoleById(@ApiParam("角色id") @RequestParam Integer id){
		try {
			if(ObjectUtils.isEmpty(id)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.ROLE_GET_FILED,"角色id错误");
			}
			UserRole power = userRoleService.getOne(new QueryWrapper<UserRole>().eq("id", id));
			log.debug("查询成功");
			return new Response<> (power);
		} catch (Exception e) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.ROLE_GET_FILED,"根据角色id查询角色信息失败");
		}
	}
	
	
	/**
	 * 根据角色id修改角色信息
	 * @author while
	 * @data 下午9:08:34
	 * @param  @param userRole
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根据角色id修改角色信息")
	@PostMapping("updUserRoleById")
	public Response<UserRole> updUserRoleById(@RequestBody UserRole userRole){
		try {
			UserRole role= new UserRole();
			role.setRoleName(userRole.getRoleName());
			role.setRoleDescription(userRole.getRoleDescription());
			//根据用户id修改用户权限
			boolean flag = userRoleService.update(role,new UpdateWrapper<UserRole>(new UserRole().setId(userRole.getId())));
			if(flag){
				log.debug("修改角色成功");
				return new Response<>();
			}else {
				log.debug("修改角色失败");
				throw new BaseException(ExceptionEnum.ROLE_UPDATE_FILED);
			}
		} catch (Exception e) {
			log.debug("修改角色失败",e.getMessage());
			throw new BaseException(ExceptionEnum.ROLE_UPDATE_FILED);
		}
	}
	
	
	
	
	
	/**
	 * 根据角色id删除角色信息
	 * @author while
	 * @data 下午9:18:26
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根据角色id删除角色信息")
	@GetMapping("deleteUserRoleById")
	public Response<UserRole> deleteUserRoleById(@ApiParam("角色id") @RequestParam Integer id ){
		try {
			if(ObjectUtils.isEmpty(id)) {
				log.debug("删除角色失败");
				throw new  BaseException(ExceptionEnum.ROLE_DELETE_FILED,"角色id错误");
			}
			boolean flag = userRoleService.remove(new UpdateWrapper<UserRole>().eq("id", id));
			if(flag) {
				log.debug("删除角色成功");
				return new Response<>();
			}else {
				log.debug("删除角色失败");
				throw new  BaseException(ExceptionEnum.ROLE_DELETE_FILED);
			}
		} catch (Exception e) {
			log.debug("删除角色失败",e.getMessage());
			throw new  BaseException(ExceptionEnum.ROLE_DELETE_FILED);
		}
	}
	
	
	
}
