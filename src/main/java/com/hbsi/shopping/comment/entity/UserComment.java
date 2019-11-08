package com.hbsi.shopping.comment.entity;

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
 *
 * @author white
 * @since 2019-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户评论类",description="保存用户评论信息")
public class UserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(name="id",value="用户id",example="1",dataType="int",required=true)
    private Integer id;

    /**
     * 商品名称
     */
    @TableField("productName")
    @ApiModelProperty(name="productName",value="商品名称",example="手机",required=true)
    private String productName;

    /**
     * 商品id
     */
    @TableField("productId")
    @ApiModelProperty(name="productId",value="商品id",example="1",required=true,dataType="int")
    private Integer productId;

    /**
     * 用户名称
     */
    @TableField("userName")
    @ApiModelProperty(name="userName",value="用户名称",example="while",required=true)
    private String userName;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(name="userId",value="用户id",example="1",required=true,dataType="int")
    private Integer userId;

    /**
     * 评论内容
     */
    @TableField("commentContent")
    @ApiModelProperty(name="commentContent",value="评论内容",example="while",required=true)
    private String commentContent;

    /**
     * 评论图片
     */
    @TableField("commentImg")
    @ApiModelProperty(name="commentImg",value=" 评论图片",example="afafalgagma.png")
    private String commentImg;

    /**
     * 评论状态(1:正常 , -1删除,默认为1)
     */
    @TableField("commentStatus")
    @ApiModelProperty(value=" 评论状态",name="commentStatus",dataType="int",hidden= true)
    private Integer commentStatus;

    /**
     * 评论时间
     */
    @TableField("createTime")
    @ApiModelProperty(value="日期",name="createTime",hidden= true)
    private String createTime;


}
