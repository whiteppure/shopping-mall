package com.hbsi.shopping.role.service;

import com.hbsi.shopping.role.entity.UserRole;

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
public interface IUserRoleService extends IService<UserRole> {

	List<String> getAllRoleName();
}
