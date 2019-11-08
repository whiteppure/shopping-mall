package com.hbsi.shopping.commentreply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
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
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("用户回复模块")
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "replyId",type = IdType.AUTO)
    @ApiModelProperty(name = "replyId" , value = "回复id",dataType = "int")
    private Integer replyId;

    @TableField("replyContent")
    @ApiModelProperty(name = "replyContent",value = "回复内容")
    private String replyContent;

    @TableField("replyTime")
    @ApiModelProperty(name = "replyTime",value = "回复时间",hidden = true)
    private String replyTime;

    @TableField("commentId")
    @ApiModelProperty(name = "commentId",value = "评论id",dataType = "int")
    private Integer commentId;

    @TableField("userId")
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "int")
    private Integer userId;

    @TableField("userName")
    @ApiModelProperty(name = "userName",value = "用户名")
    private String userName;


}
