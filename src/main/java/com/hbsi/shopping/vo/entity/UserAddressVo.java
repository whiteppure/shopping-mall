package com.hbsi.shopping.vo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ApiModel(value="用户收货地址信息",description="保存用户收货地址信息")
public class UserAddressVo {
	
	
	@ApiModelProperty(value="用户id")
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	
	@ApiModelProperty(value="收货地址id")
	private Integer addressId;
	
	@ApiModelProperty(value="用户名")
	private  String userName;
	
	@ApiModelProperty(value="联系电话")
	private String phone;
	
	@ApiModelProperty(value="收货地址")
	private String address;
	
	@ApiModelProperty(value="收货地址备注")
	private String comment;
	
	@ApiModelProperty(value="用户email")
	private String email;
	
	@ApiModelProperty(value="用户角色名称")
	private String roleName;
	
}
