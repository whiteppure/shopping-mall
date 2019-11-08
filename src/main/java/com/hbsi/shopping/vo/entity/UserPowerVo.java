package com.hbsi.shopping.vo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户详情信息", description = "保存用户详情")
public class UserPowerVo {

	@ApiModelProperty(value = "用户id")
	@TableId(value = "id",type=IdType.AUTO)
	private Integer id;
	
	
	@ApiModelProperty(value = "外键权限id")
	private Integer powerId;
	
	@ApiModelProperty(value = "用户名")
	private String userName;
	
	@ApiModelProperty(value = "email")
	private String email;
	
	@ApiModelProperty(value = "店铺名称")
	private String shopName;
	
	@ApiModelProperty(value = "联系电话")
	private String phone;
	
	@ApiModelProperty(value = "注册时间")
	private String createTime;
	
	@ApiModelProperty(value = "用户状态")
	private Integer userStatus;
	
	@ApiModelProperty(value = "权限名称")
	private String powerName;
	
	@ApiModelProperty(value = "权限描述")
	private String powerDescription;
	
	@ApiModelProperty(value = "角色名称")
	private String roleName;

}
