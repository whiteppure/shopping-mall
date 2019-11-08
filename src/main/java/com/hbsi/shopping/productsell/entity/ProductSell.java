package com.hbsi.shopping.productsell.entity;

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
@ApiModel(value="已售商品类",description="保存已售商品信息")
public class ProductSell implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="已售商品id",name="id",required=true,example="int",dataType="int")
    private Integer id;

    /**
     * 已售商品名称
     */
    @TableField("productName")
    @ApiModelProperty(value="已售商品名称",name="productName",example="iPhone")
    private String productName;
    
    /**
     * 已售商品图片
     */
    @TableField("productImg")
    @ApiModelProperty(value="已售商品图片",name="productImg",example="iPhone.png")
    private String productImg;

    /**
     * 已售商品id
     */
    @TableField("producntId")
    @ApiModelProperty(value="已售商品id",name="producntId",example="1",dataType="1")
    private Integer producntId;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value="购买商品的用户id",name="producntId",example="1",dataType="1")
    private Integer userId;

    /**
     * 用户名
     */
    @TableField("userName")
    @ApiModelProperty(value="用户名",name="userName",example="white")
    private String userName;

    /**
     * 已售商品所属店铺id
     */
    @TableField("shopId")
    @ApiModelProperty(value="该商品属于哪个店铺",name="shopId",example="1",dataType="1")
    private Integer shopId;

    /**
     * 交易时间
     */
    @TableField("createTime")
    @ApiModelProperty(value="交易时间",name="createTime",hidden=true)
    private String createTime;


}
