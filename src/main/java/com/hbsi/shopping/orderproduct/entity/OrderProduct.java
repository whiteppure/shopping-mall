package com.hbsi.shopping.orderproduct.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author white
 * @since 2019-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "订单商品模块",description = "保存用户加入购物车的商品的订单信息")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属订单信息id
     */
    @TableField("orderInfoId")
    private Integer orderInfoId;

    /**
     * 商品数量
     */
    @TableField("productCount")
    private Integer productCount;

    /**
     * 商品价格
     */
    @TableField("productPrice")
    private BigDecimal productPrice;

    /**
     * 商品名称
     */
    @TableField("productName")
    private String productName;

    /**
     * 商品id
     */
    @TableField("productId")
    private Integer productId;

    /**
     * 店铺id
     */
    @TableField("shopId")
    private Integer shopId;


}
