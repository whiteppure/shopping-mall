package com.hbsi.shopping.evaluate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

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
@ApiModel(value="用户评价类",description="保存用户评价信息")
public class UserEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(name="id",value="评价id",example="1",dataType="int",required=true)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("userName")
    @ApiModelProperty(name="userName",value="用户名",example="white",required=true)
    private String userName;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(name="userId",value="用户id",example="1",required=true,dataType="int")
    private Integer userId;

    /**
     * 评价内容
     */
    @TableField("evaluateContent")
    @ApiModelProperty(name="evaluateContent",value="评价内容",example="哈哈哈",required=true)
    private String evaluateContent;

    /**
     * 评论图片
     */
    @TableField("evaluateImg")
    @ApiModelProperty(name="evaluateImg",value="评论图片",example="waesdfghhgagsdfg.png")
    private String evaluateImg;

    /**
     * 店铺id
     */
    @TableField("shopId")
    @ApiModelProperty(name="shopId",value="店铺id",example="1",required=true,dataType="int")
    private Integer shopId;

    /**
     * 店铺名称
     */
    @TableField("shopName")
    @ApiModelProperty(name="shopName",value="店铺名称",example="张三的店铺")
    private String shopName;

    /**
     * 对店铺的评价(-1:极差,0:较差,1:一般,2:还不错3:极好)
     */
    @TableField("shopEvaluate")
    @ApiModelProperty(name="shopEvaluate",value="对店铺的评价",example="1",required=true,dataType="int")
    private Integer shopEvaluate;

    /**
     * 商品id
     */
    @TableField("productId")
    @ApiModelProperty(name="productId",value="商品id",example="1",dataType="int")
    private Integer productId;

    /**
     * 商品名称
     */
    @TableField("productName")
    @ApiModelProperty(name="productName",value="商品名称",example="iPhone X")
    private String productName;
    
    
    /**
     * 评价状态(1:正常 , -1删除,默认为1)
     */
    @TableField("evaluateStatus")
    @ApiModelProperty(value=" 评论状态",name="evaluateStatus",dataType="int",hidden= true)
    private Integer evaluateStatus;

    /**
     * 评价时间
     */
    @TableField("createTime")
    @ApiModelProperty(value="评价时间",name="createTime",hidden= true)
    private String createTime;


}
