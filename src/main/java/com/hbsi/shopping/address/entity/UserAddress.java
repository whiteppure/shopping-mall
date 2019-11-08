package com.hbsi.shopping.address.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.statement.truncate.Truncate;

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
@ApiModel(value="用户地址模块",description="保存用户的收货地址信息")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="地址id",name="id",example="1",dataType="int",required=true)
    private Integer id;

    /**
     * 收货人
     */
    @TableField("userName")
    @ApiModelProperty(value="收货人",name="userName",example="123")
    private String userName;

    /**
     * 联系电话
     */
    @TableField("phone")
    @ApiModelProperty(value="收货人联系电话",name="phone",example="12313212123")
    private String phone;

    /**
     * 收货地址
     */
    @TableField("address")
    @ApiModelProperty(value="收货地址",name="address",example="河北省保定市莲池区长城北大街27号")
    private String address;

    /**
     * 收货备注
     */
    @TableField("comment")
    @ApiModelProperty(value="收货备注",name="comment",example="233333")
    private String comment;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value="用户id",name="userId",example="1",dataType="int")
    private Integer userId;
    
    
    /**
     * 注册日期
     */
    @TableField("createTime")
    @ApiModelProperty(value="日期",name="createTime",hidden= true)
    private String createTime;

    /**
     * 地址标签
     */
    @TableField("addressTag")
    @ApiModelProperty(value="地址标签",name="addressTag",example = "家")
    private String addressTag;


}
