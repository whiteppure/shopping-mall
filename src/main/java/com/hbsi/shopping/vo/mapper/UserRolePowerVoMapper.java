package com.hbsi.shopping.vo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.vo.entity.UserPowerVo;

public interface UserRolePowerVoMapper extends BaseMapper<UserPowerVo> {

	/**
	 * 查询用户信息详情
	 * @author while
	 * @data 上午8:56:11
	 * @param  @return
	 * @description TODO
	 */
	@Select(" select `user`.userName,`user`.email,`user`.phone,`user`.roleName,`user`.shopName,`user`.`userStatus`,`user`.createTime\r\n" +
			",user_power.powerName,user_power.powerDescription \r\n" + 
			"from `user` , user_power WHERE `user`.id = user_power.userId ")
	List<UserPowerVo> getAllUserInfoByPage(Page<UserPowerVo> page);
	
	
	/**
	 * 根据用户id查询用户信息
	 * @author while
	 * @data 下午4:46:25
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@Select(" select `user`.userName,`user`.email,`user`.phone,`user`.roleName,`user`.shopName,`user`.`userStatus`,`user`.createTime\r\n" +
			",user_power.powerName,user_power.powerDescription \r\n" + 
			"from `user` , user_power WHERE `user`.id = user_power.userId AND userId=#{id} ")
	UserPowerVo getUserInfoByUserId(Integer id );
	
}
