package com.hbsi.shopping.comment.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.comment.entity.UserComment;
import com.hbsi.shopping.comment.service.IUserCommentService;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.DateUtils;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@RestController
@RequestMapping("/comment/")
@Api(tags="用户评论模块")
@CacheConfig(cacheNames = "comment")
public class UserCommentController {

	private final IUserCommentService userCommentService;

	public UserCommentController(IUserCommentService userCommentService) {
		this.userCommentService = userCommentService;
	}

	/**
	 * 分页查询全部的评论
	 * 
	 * @author while
	 * @data 下午8:46:11
	 * @param @param current
	 * @param @param size
	 * @param @return
	 * @description TODO
	 */
	@Caching(cacheable = {@Cacheable},put = {@CachePut})
	@ApiOperation("分页查询全部的评论")
	@GetMapping("getAllCommentByPage")
	public Response<IPage<UserComment>> getAllCommentByPage(
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size) {
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "current 参数错误");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "size参数错误");
			}
			Page<UserComment> page = new Page<UserComment>(current, size);
			IPage<UserComment> commentList = userCommentService.page(page, new QueryWrapper<UserComment>().orderByDesc("userId").eq("status", 1));
			log.debug("查询成功");
			return new Response<IPage<UserComment>>(commentList);
		} catch (Exception e) {
			log.debug("查询错误", e.getMessage());
			throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "分页查询所有的评论失败");
		}
	}
	
	
	/**
	 * 根据评论id查询评论
	 * @author while
	 * @data 下午9:05:45
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@GetMapping("getCommentById")
	@ApiOperation("根据评论id查询评论")
	public Response<UserComment> getCommentById(@ApiParam("评论id") @RequestParam Long id) {
		if (ObjectUtils.isEmpty(id)) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "评论id错误");
		}
		UserComment userComment = null;
		try {
			userComment = userCommentService.getOne(new QueryWrapper<UserComment>().eq("id", id).eq("status", 1));
			log.debug("查询成功");
		} catch (Exception e) {
			log.debug("根据评论id查询评论失败", e.getMessage());
			throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "根据评论id查询评论失败");
		}
		return new Response<>(userComment);
	}
	
	/**
	 * 用户添加评论
	 * @author while
	 * @data 下午9:15:22
	 * @param  @param userComment
	 * @param  @return
	 * @description TODO
	 */
	@Transactional
	@ApiOperation("用户添加评论")
	@PostMapping("addComment")
	@CachePut
	public Response<UserComment> addComment(@RequestBody UserComment userComment){
		try {
			userComment.setCreateTime(DateUtils.formatDate(new Date()));
			if(userCommentService.save(userComment)) {
				log.debug("添加评论成功");
				return new Response<>();
			}
			else {
				log.debug("添加评论失败");
				throw new BaseException(ExceptionEnum.COMMENT_ADD_FILED,"添加评论失败");
			}
		} catch (Exception e) {
			log.debug("添加评论失败",e.getMessage());
			throw new BaseException(ExceptionEnum.COMMENT_ADD_FILED,"添加评论失败");
		}
	}
	
	

	/**
	 * 分页根据用户id查询全部评论
	 * 
	 * @author while
	 * @data 下午8:52:52
	 * @param @param current
	 * @param @param size
	 * @param @param userId
	 * @param @return
	 * @description TODO
	 */
	@Caching(cacheable = {@Cacheable},put = {@CachePut})
	@ApiOperation("分页根据用户id查询全部评论")
	@GetMapping("getAllCommentByUserId")
	public Response<IPage<UserComment>> getAllCommentByUserId(
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size,
			@ApiParam("用户id") @RequestParam Integer userId) {
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "current 参数错误");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "size参数错误");
			}
			if (ObjectUtils.isEmpty(userId)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "userId参数错误");
			}
			Page<UserComment> page = new Page<UserComment>(current, size);
			IPage<UserComment> commentList = userCommentService.page(page,
					new QueryWrapper<UserComment>().eq("userId", userId).eq("status", 1));
			log.debug("查询成功");
			return new Response<IPage<UserComment>>(commentList);
		} catch (Exception e) {
			log.debug("查询错误", e.getMessage());
			throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "根据用户id查询全部评论");
		}
	}
	
	
	
	
	
	
	/**
	 * 根商品id分页查询全部评论
	 * @author while
	 * @data 上午8:02:29
	 * @param  @param current
	 * @param  @param size
	 * @param  @param productId
	 * @param  @return
	 * @description TODO
	 */
	@ApiOperation("根商品id分页查询全部评论")
	@GetMapping("getAllCommentByProductId")
	public Response<IPage<UserComment>> getAllCommentByProductId(
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size,
			@ApiParam("商品id") @RequestParam Integer productId) {
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "current 参数错误");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "size参数错误");
			}
			if (ObjectUtils.isEmpty(productId)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "productId参数错误");
			}
			Page<UserComment> page = new Page<UserComment>(current, size);
			IPage<UserComment> commentList = userCommentService.page(page,
					new QueryWrapper<UserComment>().eq("productId", productId).eq("status", 1));
			log.debug("查询成功");
			return new Response<IPage<UserComment>>(commentList);
		} catch (Exception e) {
			log.debug("查询错误", e.getMessage());
			throw new BaseException(ExceptionEnum.COMMENT_GET_FILED, "根商品id分页查询全部评论失败");
		}
	}
	
	
	
	/**
	 * 根据用户id删除用户评论
	 * @author while
	 * @data 下午8:56:54
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@Transactional
	@GetMapping("deleteCommentByUserId")
	@ApiOperation("根据用户id删除评论")
	public Response<Object> deleteCommentByUserId(@ApiParam("用户id") @RequestParam Integer userId) {
		if (ObjectUtils.isEmpty(userId)) {
			log.debug("删除用户评论失败");
			throw new BaseException(ExceptionEnum.COMMENT_DELETE_FILED, "用户id错误");
		}
		try {
			boolean flag = userCommentService.update(new UserComment().setCommentStatus(-1), new UpdateWrapper<UserComment>(new UserComment().setUserId(userId)));
			if (flag) {
				log.debug("删除用户评论成功");
				return new Response<Object>();
			} else {
				log.debug("根据用户id删除评论失败");
				throw new BaseException(ExceptionEnum.COMMENT_DELETE_FILED, "根据用户id删除评论失败");
			}
		} catch (Exception e) {
			log.debug("根据用户id删除评论失败", e.getMessage());
			throw new BaseException(ExceptionEnum.COMMENT_DELETE_FILED, "根据用户id删除评论失败");
		}
	}
	
	
	
	
	/**
	 * 根据评论id删除用户评论
	 * @author while
	 * @data 下午8:56:54
	 * @param  @param id
	 * @param  @return
	 * @description TODO
	 */
	@Transactional
	@GetMapping("deleteCommentById")
	@ApiOperation("根据评论id删除用户评论")
	public Response<Object> deleteCommentById(@ApiParam("评论id") @RequestParam Integer id) {
		if (ObjectUtils.isEmpty(id)) {
			log.debug("删除用户评论失败");//COMMENT_GET_FILED
			throw new BaseException(ExceptionEnum.COMMENT_DELETE_FILED, "评论id错误");
		}
		try {
			boolean flag = userCommentService.update(new UserComment().setCommentStatus(-1), new UpdateWrapper<UserComment>(new UserComment().setId(id)));
			if (flag) {
				log.debug("删除用户评论成功");
				return new Response<Object>();
			} else {
				log.debug("根据评论id删除用户评论失败");
				throw new BaseException(ExceptionEnum.COMMENT_DELETE_FILED, "根据评论id删除用户评论失败");
			}
		} catch (Exception e) {
			log.debug("根据评论id删除用户评论失败", e.getMessage());
			throw new BaseException(ExceptionEnum.COMMENT_DELETE_FILED, "根据评论id删除用户评论失败");
		}
	}
	
	

}
