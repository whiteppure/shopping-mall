package com.hbsi.shopping.ad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告id",name="id",dataType = "int" ,example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 广告名称
     */
    @TableField("adName")
    @ApiModelProperty(value = "广告名称", name = "adName",example = "123")
    private String adName;

    /**
     * 广告位置
     */
    @TableField("adLocation")
    @ApiModelProperty(value = "广告位置",name = "adLocation",example ="首页大图")
    private String adLocation;

    /**
     * 图片
     */
    @TableField("adImg")
    @ApiModelProperty(value = "广告图片路径",name = "adImg" ,example ="132.png")
    private String adImg;


    /**
     * 广告状态: 1:申请中,0展示,-1:不展示,默认为1
     */
    @TableField("adStatus")
    @ApiModelProperty(value = "广告状态",hidden = true,name = "adStatus")
    private Integer adStatus;


}
