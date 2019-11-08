package com.hbsi.shopping.shop.entity;

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
@ApiModel(value="用户店铺类",description="保存店铺信息")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="店铺id",name="id",example="1",dataType="int",required=true)
    private Integer id;

    /**
     * 店铺名称
     */
    @TableField("shopName")
    @ApiModelProperty(value="店铺名称",name="shopName",example="张三的店铺")
    private String shopName;

    /**
     * 经营人id
     */
    @TableField("userId")
    @ApiModelProperty(value="店铺id",name="userId",example="1",dataType="int")
    private Integer userId;

    /**
     * 店铺经营人
     */
    @TableField("userName")
    @ApiModelProperty(value="店铺经营人",name="userName",example="white")
    private String userName;

    /**
     * 店铺编号
     */
    @TableField("shopNum")
    @ApiModelProperty(value="店铺编号",name="shopNum")
    private String shopNum;

    /**
     * 店铺封面
     */
    @TableField("shopImg")
    @ApiModelProperty(value="店铺封面",name="shopImg",example="shop12345.jpg")
    private String shopImg;
    
    /**
     * 店铺等级
     * 默认 一般
     */
    @TableField("shopLevel")
    @ApiModelProperty(value="店铺等级",name="shopLevel",hidden=true)
    private String shopLevel;

    /**
     * 店铺状态: 1:开通店铺,0:禁用, ,默认为1
     */
    @TableField("shopStatus")
    @ApiModelProperty(value="店铺状态",name="shopStatus",dataType="int",hidden=true)
    private Integer shopStatus;

    /**
     * 店铺描述
     */
    @TableField("shopDesc")
    @ApiModelProperty(value="店铺描述",name="shopDesc",example="hello 店铺")
    private String shopDesc;

    /**
     * 店铺创建时间
     */
    @TableField("createTime")
    @ApiModelProperty(value="店铺创建时间",name="createTime",hidden=true)
    private String createTime;


}
