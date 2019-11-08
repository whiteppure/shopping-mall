package com.hbsi.shopping.vo.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbsi.shopping.vo.entity.UserShopVo;

public interface UserShopVoMapper extends BaseMapper<UserShopVo>{
	
	/**
	 * 根据用户id查询用户和店铺的信息
	 * @author while
	 * @data 下午4:13:13
	 * @param  @param userId
	 * @param  @return
	 * @description TODO
	 */
	@Select("select `user`.userName,`user`.phone,`user`.email,`user`.roleName,`user`.`userStatus`,\r\n" +
			"shop.shopDesc,shop.shopNum,shop.shopName,shop.createTime,shop.shopLevel\r\n" + 
			"from `user` , shop where `user`.shopId = shop.id and `user`.id=#{userId}")
	UserShopVo getUserShopByUserId(Integer userId);
	
}
