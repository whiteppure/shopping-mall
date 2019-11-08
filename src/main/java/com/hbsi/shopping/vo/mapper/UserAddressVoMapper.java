package com.hbsi.shopping.vo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbsi.shopping.vo.entity.UserAddressVo;

public interface UserAddressVoMapper extends BaseMapper<UserAddressVo> {

	
	/**
	 * 根据用户id查询收货地址
	 * @author while
	 * @data 下午7:08:16
	 * @param  @param userId
	 * @param  @return
	 * @description TODO
	 */
	@Select("select `user`.userName,`user`.email,`user`.phone,`user`.roleName,user_address.address,user_address.`common`\r\n"
			+ "from `user`,user_address where `user`.id = user_address.userId and `user`.id = #{userId} ")
	List<UserAddressVo> getAllUserAddresByUserId(Integer userId);
}
