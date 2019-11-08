package com.hbsi.shopping.power.mapper;

import com.hbsi.shopping.power.entity.UserPower;

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
public interface UserPowerMapper extends BaseMapper<UserPower> {
	
	
	/**
	 * 查询所有权限名称
	 * @author while
	 * @data 下午6:01:43
	 * @param  @return
	 * @description TODO
	 */
	@Select("select distinct powerName from user_power")
	List<String> getAllPowerName();
}
