package com.hbsi.shopping.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
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
@ApiModel(value="用户角色类",description="保存用户角色信息")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="角色id",name="id",example="1",dataType="int",required=true)
    private Integer id;

    /**
     * 角色名称
     */
    @TableField("roleName")
    @ApiModelProperty(value="角色名称",name="roleName",example="vip")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("roleDescription")
    @ApiModelProperty(value="角色描述",name="roleDescription",example="充钱使我变得更强")
    private String roleDescription;

    /**
     * 角色参考id
     */
    @TableField("referRoleId")
    @ApiModelProperty(value="角色参考id",name="referRoleId",example="1",dataType="int")
    private Integer referRoleId;
    
    
    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value="用户id",name="userId",example="1",dataType="int")
    private Integer userId;

    /**
     * 创建日期
     */
    @TableField("createTime")
    @ApiModelProperty(value="日期",name="createTime",hidden= true)
    private String  createTime;


}
