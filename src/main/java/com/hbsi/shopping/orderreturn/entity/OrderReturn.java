package com.hbsi.shopping.orderreturn.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
@ApiModel(value = "退货订单模块",description = "保存退货订单信息")
public class OrderReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "退货订单id",name = "id",example = "1",dataType = "int")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 退货订单编号
     */
    @TableField("returnNum")
    @ApiModelProperty(value = "退货订单编号",name = "returnNum",hidden = true)
    private String returnNum;

    /**
     * 退货人
     */
    @TableField("useName")
    @ApiModelProperty(value = "退货人",name = "useName",example = "abcdefghij")
    private String useName;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id",name = "userId",dataType = "int",example = "1")
    private Integer userId;

    /**
     * 退货金额
     */
    @TableField("returnPrice")
    @ApiModelProperty(value = "退货金额",name = "returnPrice",example = "999")
    private BigDecimal returnPrice;

    /**
     * 退货状态:-1:已拒绝,0:退货中,1:待处理,2:已完成
     */
    @TableField("orderReturnStatus")
    @ApiModelProperty(value = "退货状态",name = "orderReturnStatus",dataType = "int",hidden = true)
    private Integer orderReturnStatus;

    /**
     * 申请时间
     */
    @TableField("createTime")
    @ApiModelProperty(value = "申请时间",name = "createTime",hidden = true)
    private String createTime;

    /**
     * 商品信息id
     */
    @TableField("productInfoId")
    @ApiModelProperty(value = "商品信息id",name = "productInfoId",dataType = "int",example = "3")
    private Integer productInfoId;

    /**
     * 商品名称
     */
    @TableField("productName")
    @ApiModelProperty(value = "商品名称",name = "productName",example = "goodExample")
    private String productName;

    /**
     * 退货原因
     */
    @TableField("returnReason")
    @ApiModelProperty(value = "退货原因",name = "returnReason",example = "不想要了23333")
    private String returnReason;

    /**
     * 退货描述
     */
    @TableField("description")
    @ApiModelProperty(value = "退货描述",name = "description",example = "23333")
    private String description;

    /**
     * 退货凭证图片
     */
    @TableField("returnImg")
    @ApiModelProperty(value = "退货凭证图片",name = "returnImg",example = "abc.png")
    private String returnImg;

    /**
     * 处理人
     */
    @TableField("delPerson")
    @ApiModelProperty(value = "处理人",name = "delPerson",example = "white")
    private String delPerson;

    /**
     * 处理退货备注
     */
    @TableField("comments")
    @ApiModelProperty(value = "处理退货备注",name = "comments",example = "无")
    private String comments;


    /**
     * 订单信息id
     */
    @TableField("orderInfoId")
    @ApiModelProperty(value = "订单信息id",dataType = "int",example = "17",name = "orderInfoId")
    private Integer orderInfoId;


}
