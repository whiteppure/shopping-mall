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
@ApiModel(value="用户店铺类",description="保存用户信息和店铺信息")
public class UserShopVo {
	
	@TableId(value = "id",type=IdType.AUTO)
	@ApiModelProperty(value="主键 用户的id")
	private Integer userId;
	
	@ApiModelProperty(value="店铺id")
	private  Integer shopId;
	
	@ApiModelProperty(value="用户名")
	private  String userName;
	
	@ApiModelProperty(value="用户的电话")
	private String phone;
	
	@ApiModelProperty(value="用户的email")
	private String email;
	
	@ApiModelProperty(value="用户的角色名称")
	private String roleName;
	
	//用户状态 禁用用户的时候禁用用户的店铺
	@ApiModelProperty(value="用户的状态")
	private Integer userStatus;
	
	@ApiModelProperty(value="用户的店铺名称")
	private String shopName;
	
	@ApiModelProperty(value="店铺描述")
	private String shopDesc;
	
	//店铺创建时间
	@ApiModelProperty(value="店铺创建时间")
	private String createTime;
	
	@ApiModelProperty(value="店铺编号")
	private String shopNum;
	
	@ApiModelProperty(value="店铺等级")
	private String shopLevel;
	
}
