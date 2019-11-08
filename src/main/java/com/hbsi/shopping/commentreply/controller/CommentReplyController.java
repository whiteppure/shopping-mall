package com.hbsi.shopping.commentreply.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.commentreply.entity.CommentReply;
import com.hbsi.shopping.commentreply.service.ICommentReplyService;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-07-26
 */
@Slf4j
@RestController
@RequestMapping("/commentreply/")
@Api(tags = "用户回复评论模块")
public class CommentReplyController {


    private final ICommentReplyService commentReplyService;

    public CommentReplyController(ICommentReplyService commentReplyService) {
        this.commentReplyService = commentReplyService;
    }


    @GetMapping("getAllReplyByCommentId")
    @ApiOperation("根据评论id查询评论回复")
    public Response<List<CommentReply>> getAllReplyByCommentId(@RequestParam Integer commentId) {
        try {
            if (ObjectUtils.isEmpty(commentId)){
                log.debug("commentId参数错误");
                throw new BaseException(ExceptionEnum.COMMENT_REPLY_GET_FILED,"commentId参数错误");
            }
            List<CommentReply> replays = commentReplyService.list(new QueryWrapper<CommentReply>().eq("commentId", commentId));
            log.debug("根据评论id查询回复成功");
            return new Response<>(replays);
        } catch (Exception e) {
            log.debug("根据评论id查询评论回复失败",e.getMessage());
            throw new BaseException(ExceptionEnum.COMMENT_REPLY_GET_FILED, "根据评论id查询评论回复信息失败");
        }
    }





    @GetMapping("getAllReplyByUserId")
    @ApiOperation("根据用户id查询评论回复")
    public Response<List<CommentReply>> getAllReplyByUserId(@RequestParam Integer userId){
        try {
            if (ObjectUtils.isEmpty(userId)){
                log.debug("userId参数错误");
                throw new BaseException(ExceptionEnum.COMMENT_REPLY_GET_FILED,"userId参数错误");
            }
            List<CommentReply> replies = commentReplyService.list(new QueryWrapper<CommentReply>().eq("userId", userId));
            log.debug("根据用户id查询评论回复成功");
            return  new Response<>(replies);
        } catch (Exception e) {
            log.debug("根据用户id查询评论回复失败");
            throw new BaseException(ExceptionEnum.COMMENT_REPLY_GET_FILED,"根据用户id查询评论回复失败");
        }
    }





    @PostMapping("addCommentReply")
    @ApiOperation("添加用户回复")
    public Response<CommentReply> addCommentReply(@RequestBody CommentReply  commentReply){
        try {
            boolean flag = commentReplyService.save(commentReply);
            if (!flag){
                log.debug("添加用户回复失败");
                throw new BaseException(ExceptionEnum.COMMENT_REPLY_ADD_FILED);
            }
            log.debug("添加用户回复成功");
            return  new Response<>();
        } catch (BaseException e) {
            log.debug("添加回复失败");
            throw new BaseException(ExceptionEnum.COMMENT_REPLY_ADD_FILED);
        }
    }





    @GetMapping("deleteCommentReply")
    @ApiOperation("根据回复id删除回复")
    public Response<CommentReply>  deleteCommentReply(@RequestParam Integer replyId ){
        try {
            if (ObjectUtils.isEmpty(replyId)){
                log.debug("删除回复失败,replyId参数错误");
                throw new BaseException(ExceptionEnum.COMMENT_REPLY_DELETE_FILED,"replyId错误");
            }
            boolean flag = commentReplyService.remove(new UpdateWrapper<CommentReply>().eq("replyId", replyId));
            if (!flag){
                log.debug("删除回复失败");
                throw new BaseException(ExceptionEnum.COMMENT_REPLY_DELETE_FILED);
            }
            log.debug("删除回复成功");
            return  new Response<>();
        } catch (BaseException e) {
            log.debug("删除回复失败");
            throw new BaseException(ExceptionEnum.COMMENT_REPLY_DELETE_FILED);
        }
    }



}