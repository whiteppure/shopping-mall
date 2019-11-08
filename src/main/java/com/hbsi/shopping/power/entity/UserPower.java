package com.hbsi.shopping.power.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户权限类",description="保存用户权限信息")
public class UserPower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @ApiModelProperty(value="权限id",name="id",required=true,example="1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    @TableField("powerName")
    @ApiModelProperty(value="权限名称",name="powerName",example="管理普通用户")
    private String powerName;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value="用户id",name="userId",example="1",dataType="int")
    private Integer userId;
    
    
    /**
     * 用户名称
     */
    @TableField("userName")
    @ApiModelProperty(value="用户名称",name="userName",example="white")
    private String userName;
    
    

    /**
     * 权限描述
     */
    @TableField("powerDescription")
    @ApiModelProperty(value="权限描述",name="description",example="管理员的权限")
    private String powerDescription;


}
