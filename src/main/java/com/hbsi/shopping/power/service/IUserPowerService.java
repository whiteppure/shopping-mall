package com.hbsi.shopping.power.service;

import com.hbsi.shopping.power.entity.UserPower;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
public interface IUserPowerService extends IService<UserPower> {
	/**
	 * 查询所有权限名称
	 * @author while
	 * @data 下午6:04:29
	 * @param  @return
	 * @description TODO
	 */
	List<String> getAllPowerName();
}
