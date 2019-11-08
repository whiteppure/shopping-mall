package com.hbsi.shopping.role.service.impl;

import com.hbsi.shopping.role.entity.UserRole;
import com.hbsi.shopping.role.mapper.UserRoleMapper;
import com.hbsi.shopping.role.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

	@Override
	public List<String> getAllRoleName() {
		return this.baseMapper.getAllRoleName();
	}

}
