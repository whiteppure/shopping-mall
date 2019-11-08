package com.hbsi.shopping.cartproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author white
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="购物车中的商品类",description="保存购物车中的商品信息")
public class CartProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="购物车中的商品id",name="id",required=true,dataType="int",example="1")
    private Integer id;

    /**
     * 商品名称
     */
    @TableField("productName")
    @ApiModelProperty(value="商品名称",name="productName",example="iPhone X",required=true)
    private String productName;
    
    /**
     * 商品图片
     */
    @TableField("productImg")
    @ApiModelProperty(value="商品图片",name="productImg",example="iphonex.png")
    private String productImg;

    
    /**
     * 商品价格
     */
    @TableField("productPrice")
    @ApiModelProperty(value="商品价格",name="productPrice",example="999.99",required=true)
    private BigDecimal productPrice;

    /**
     * 商品id
     */
    @TableField("productId")
    @ApiModelProperty(value="商品id",name="productId",dataType="int",required=true,example="2")
    private Integer productId;



    /**
     * 店铺id
     */
    @TableField("shopId")
    @ApiModelProperty(value="店铺id",name="shopId",dataType="int",hidden = true)
    private Integer shopId;



    /**
     * 购物车中的商品数量
     */
    @TableField("productCount")
    @ApiModelProperty(value="购物车中的商品数量",name="productCount",dataType="int",required=true,example="1")
    private Integer productCount;
    
    
    
    /**
     * 购物车id
     */
    @TableField("cartId")
    @ApiModelProperty(value="购物车id",name="cartId",dataType="int",required=true,example="1")
    private Integer cartId;

    /**
     * 商品加入购物车时间
     */
    @TableField("createTime")
    @ApiModelProperty(value="商品加入购物车时间",name="createTime",hidden=true)
    private String createTime;


}
