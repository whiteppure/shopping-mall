package com.hbsi.shopping.cart.entity;

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
@ApiModel(value="用户购物车",description="保存用户购买的商品信息")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="购物车id",name="id",example="1",dataType="int",required=true)
    private Integer id;


    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value="用户id",example="1",name="userId")
    private Integer userId;

    /**
     * 用户名称
     */
    @TableField("userName")
    @ApiModelProperty(value="用户名称",example="white",name="userName")
    private String userName;

    /**
     * 创建日期
     */
    @TableField("createTime")
    @ApiModelProperty(value="创建日期",name="createTime",hidden=true)
    private String createTime;


}
