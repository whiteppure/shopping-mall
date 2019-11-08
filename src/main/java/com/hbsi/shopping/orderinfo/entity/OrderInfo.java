package com.hbsi.shopping.orderinfo.entity;

import java.math.BigDecimal;
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
@ApiModel(value = "订单信息类",description = "保存用户的订单信息")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "订单信息id",name="id",required = true,dataType = "int",example = "1")
    private Integer id;

    /**
     * 订单编号
     */
    @TableField("orderNum")
    @ApiModelProperty(value = "订单编号",name = "orderNum",example = "SN123456",required = true)
    private String orderNum;

    /**
     * 收货地址id
     */
    @TableField("addressId")
    @ApiModelProperty(value = "收货地址id",name="addressId",required = true,dataType = "int",example = "1")
    private Integer addressId;


    /**
     * 订单价格
     */
    @TableField("orderPrice")
    @ApiModelProperty(value = "订单价格",name="orderPrice",required = true,example = "9.9")
    private BigDecimal orderPrice;



    /**
     * 订单状态(-1;已删除,0已发货,1:待发货,2已完成)
     */
    @TableField("orderInfoStatus")
    @ApiModelProperty(value = "订单状态",name="orderInfoStatus",required = true,hidden = true)
    private Integer orderInfoStatus;



    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id",name="userId",dataType = "int",example = "1")
    private Integer userId;



    /**
     * 用户名
     */
    @TableField("userName")
    @ApiModelProperty(value = "用户名",name="userName",example = "white")
    private String userName;


    /**
     * 订单创建日期
     */
    @TableField("createTime")
    @ApiModelProperty(value = "订单创建日期",name="createTime",required = true,hidden = true)
    private String createTime;


}
