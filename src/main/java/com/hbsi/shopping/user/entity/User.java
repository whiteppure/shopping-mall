package com.hbsi.shopping.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljz
 * @since 2019-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户类",description="保存用户信息")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId(value="id",type=IdType.AUTO)
    @ApiModelProperty(value="用户id",name="id",example="1",dataType="int",required=true)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("userName")
    @ApiModelProperty(value="用户名",name="userName",example="white",required=true)
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    @ApiModelProperty(value="密码",name="password",example="12345678",required=true)
    private String password;

    /**
     * 联系电话
     */
    @TableField("phone")
    @ApiModelProperty(value="联系电话",name="phone",example="13122256789",required=true)
    private String phone;

    /**
     * 邮箱/账号
     */
    @TableField("email")
    @ApiModelProperty(value="邮箱/账号",name="email",example="13122256789@163.com",required=true)
    private String email;



    /**
     * 角色id
     */
    @TableField("roleId")
    @ApiModelProperty(value="角色id",name="roleId",example="1",dataType="int",hidden=true)
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField("roleName")
    @ApiModelProperty(value=" 角色名称",name="roleName",example="管理员")
    private String roleName;
    
    
    /**
     * 店铺名称
     */
    @TableField("shopName")
    @ApiModelProperty(value="店铺名称",name="shopName",example="张三的店铺")
    private String shopName;
    
    /**
     * 店铺id
     */
    @TableField("shopId")
    @ApiModelProperty(value="店铺id",name="shopId",example="1",dataType="int",hidden=true)
    private Integer shopId;

    /**
     * 0为禁用状态,1为正常状态
     */
    @TableField("userStatus")
    @ApiModelProperty(value="用户状态",name="userStatus",dataType="int",hidden= true)
    private Integer userStatus;

    /**
     * 注册日期
     */
    @TableField("createTime")
    @ApiModelProperty(value="日期",name="createTime",hidden= true)
    private String createTime;


}
