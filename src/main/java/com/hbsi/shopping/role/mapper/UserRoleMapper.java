package com.hbsi.shopping.role.mapper;

import com.hbsi.shopping.role.entity.UserRole;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
	
	
	/**
	 * 不分页查询所有角色名称
	 * @author while
	 * @data 下午8:55:24
	 * @param  @return
	 * @description TODO
	 */
	@Select("select distinct roleName from user_role")
	List<String> getAllRoleName();
}
