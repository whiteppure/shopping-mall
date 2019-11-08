package com.hbsi.shopping.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.Api;
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
 * @author white
 * @since 2019-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户支付订单模块",description = "保存用户支付模块信息")
public class Pay implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付id",dataType = "int" ,example = "1",name = "id",required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 支付方式
     */
    @TableField("payWay")
    @ApiModelProperty(value = "支付方式",example = "支付宝",name = "id",required = true)
    private String payWay;

    /**
     * 支付状态(1:待支付 ,2已支付,-1:未支付)
     */
    @TableField("payStatus")
    @ApiModelProperty(value = "支付状态",dataType = "int" ,name = "payStatus",hidden = true)
    private Integer payStatus;

    /**
     * 支付时间
     */
    @TableField("payTime")
    @ApiModelProperty(value = "支付时间",example = "支付宝",name = "payTime",hidden = true)
    private String payTime;

    /**
     * 订单信息id
     */
    @TableField("orderInfoId")
    @ApiModelProperty(value = "订单信息id",dataType = "int" ,example = "1",name = "orderInfoId")
    private Integer orderInfoId;

    /**
     * 支付人
     */
    @TableField("userName")
    @ApiModelProperty(value = "支付人",example = "支付宝",name = "userName")
    private String userName;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id",dataType = "int" ,example = "1",name = "userId")
    private Integer userId;


    /**
     * 支付金额
     */
    @TableField("payPrice")
    @ApiModelProperty(value = "支付金额",dataType = "int" ,example = "1",name = "payPrice")
    private BigDecimal payPrice;

}
