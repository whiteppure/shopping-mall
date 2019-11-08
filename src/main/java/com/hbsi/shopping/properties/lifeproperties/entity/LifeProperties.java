package com.hbsi.shopping.properties.lifeproperties.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="生活用品类属性模块",description="保存生活类属性信息")
public class LifeProperties implements Serializable{
	
	 private static final long serialVersionUID = 1L;


	    @TableId(value = "id", type = IdType.AUTO)
	    @ApiModelProperty(value="生活用品类属性id",name="id",required=true,dataType="int",example="1")
	    private Integer id;

	    /**
	     * 生活用品类属性名
	     */
	    @TableField("lifePropertiesName")
	    @ApiModelProperty(value="生活用品类属性名称",name="lifePropertiesName",example="颜色")
	    private String lifePropertiesName;

	    /**
	     * 所属商品类型名称
	     */
	    @TableField("productTypeName")
	    @ApiModelProperty(value="所属商品类型名称",name="productTypeName",example="生活用品类")
	    private String productTypeName;

	    /**
	     * 属性可选值列表
	     */
	    @TableField("optionalValue")
	    @ApiModelProperty(value="商品属性可选值列表",name="optionalValue",example="红色,绿色,白色")
	    private String optionalValue;


	    /**
	     * 商品类型id
	     */
	    @TableField("typeId")
	    @ApiModelProperty(value="所属商品类型id",name="typeId",required=true,dataType="int",example="1")
	    private Integer typeId;
}
