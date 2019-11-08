package com.hbsi.shopping.ordersend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
@ApiModel(value = "订单发货模块",description = "保存发货订单信息")
public class OrderSend implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "发货订单id",dataType = "int",example = "1",name = "id")
    private Integer id;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id",dataType = "int",example = "1",name = "userId")
    private Integer userId;

    /**
     * 发货人
     */
    @TableField("userName")
    @ApiModelProperty(value = "发货人",example = "white",name = "userName")
    private String userName;

    /**
     * 发货备注
     */
    @TableField("common")
    @ApiModelProperty(value = "发货备注",example = "无",name = "common")
    private String comment;

    /**
     * 邮编
     */
    @TableField("postalCode")
    @ApiModelProperty(value = "邮编",dataType = "int",example = "063308",name = "postalCode")
    private String postalCode;

    /**
     * 商品信息id
     */
    @TableField("productId")
    @ApiModelProperty(value = "商品信息id",dataType = "int",example = "3",name = "productId")
    private Integer productId;

    /**
     * 发货商品名称
     */
    @TableField("productName")
    @ApiModelProperty(value = "发货商品名称",example = "小米X",name = "productName")
    private String productName;

    /**
     * 收货地址id
     */
    @TableField("addressId")
    @ApiModelProperty(value = "收货地址id",dataType = "int",example = "15",name = "addressId")
    private Integer addressId;

    /**
     * 订单信息id
     */
    @TableField("orderInfoId")
    @ApiModelProperty(value = "订单信息id",dataType = "int",example = "17",name = "orderInfoId")
    private Integer orderInfoId;

    /**
     * 发货状态:  0:发货中,1:已发货,2:已完成
     */
    @TableField("orderSendStatus")
    @ApiModelProperty(value = " 发货状态",dataType = "int",hidden = true,name = "orderSendStatus")
    private Integer orderSendStatus;

    /**
     * 发货时间
     */
    @TableField("createTime")
    @ApiModelProperty(value = " 发货日期",hidden = true,name = "createTime")
    private String createTime;


}
