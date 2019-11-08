package com.hbsi.shopping.properties.petproperties.entity;

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
 * @since 2019-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="宠物类属性模块",description="保存宠物类属性信息")
public class PetProperties implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="宠物类属性id",name="id",required=true,dataType="int",example="1")
    private Integer id;

    /**
     * 宠物类属性名
     */
    @TableField("petPropertiesName")
    @ApiModelProperty(value="宠物类属性名称",name="petPropertiesName",example="颜色")
    private String petPropertiesName;

    /**
     * 所属商品类型名称
     */
    @TableField("productTypeName")
    @ApiModelProperty(value="所属商品类型名称",name="productTypeName",example="宠物类")
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
