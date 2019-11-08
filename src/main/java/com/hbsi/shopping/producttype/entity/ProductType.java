package com.hbsi.shopping.producttype.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.Api;
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
@ApiModel(value="商品类型类",description="保存商品类型信息")
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="商品类型id",name="id",example="1",dataType="int",required=true)
    private Integer id;

    /**
     * 商品类型名称
     */
    @TableField("typeName")
    @ApiModelProperty(value="商品类型名称",name="typeName",example="电子数码类")
    private String typeName;


    /**
     * 商品类型属性数量
     */
    @TableField("typePropertiesCount")
    @ApiModelProperty(value="商品类型属性id",name="typePropertiesCount",example="4",dataType="int",required=true)
    private Integer typePropertiesCount;
    
    
    /**
     * 添加商品类型时间
     */
    @TableField("createTime")
    @ApiModelProperty(value="添加类型时间",hidden=true)
    private  String createTime;


}
