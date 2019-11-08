package com.hbsi.shopping.cart.controller;


import java.util.List;


import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.cart.entity.Cart;
import com.hbsi.shopping.cart.service.ICartService;
import com.hbsi.shopping.cartproduct.entity.CartProduct;
import com.hbsi.shopping.cartproduct.service.ICartProductService;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@Controller
@RequestMapping("/cart/")
@Api(tags="用户购物车")
@CacheConfig(cacheNames = "cart")
public class CartController {
	
	private final ICartService cartService;
	
	private final ICartProductService cartProductService;

	public CartController(ICartService cartService, ICartProductService cartProductService) {
		this.cartService = cartService;
		this.cartProductService = cartProductService;
	}

	/**
	 * 根据用户id 查询购物车
	 * 根据购物车id 查询加入购物车的商品
	 */
	@Caching(cacheable = {@Cacheable},put = {@CachePut})
	@ResponseBody
	@ApiOperation("根据用户id分页查询购物车中的商品")
	@PostMapping("getCartByCartIdByPage")
	public  Response<IPage<CartProduct>> getCartByCartIdByPage(
			@ApiParam("用户id") @RequestParam Integer userId,
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size
			){
		try {
			if(ObjectUtils.isEmpty(userId)) {
				log.debug("查询失败");
				throw new  BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED,"userId参数错误");
			}
			
			if(ObjectUtils.isEmpty(current)) {
				log.debug("查询失败");
				throw new  BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED,"current参数错误");
			}
			
			if(ObjectUtils.isEmpty(size)) {
				log.debug("查询失败");
				throw new  BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED,"size参数错误");
			}
			Page<CartProduct> page = new Page<CartProduct>(current,size);
			//根据用户id查询购物车
			Cart cart = cartService.getOne(new QueryWrapper<Cart>().eq("userId", userId));
			
			//根据购物车id查询加入购物车的所有商品
			IPage<CartProduct> cartProduct = cartProductService.page(page,new QueryWrapper<CartProduct>().eq("cartId", cart.getId()));
			log.debug("查询成功");
			return new Response<>(cartProduct);
		} catch (Exception e) {
			log.debug("查询失败",e.getMessage());
			throw new  BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED,"根据用户id查询购物车中的商品失败");
		}
	}



	/**
	 * 根据购用户id不分页查询购物车中的商品
	 */
	@Caching(cacheable = {@Cacheable},put = {@CachePut})
	@ApiOperation("根据用户id不分页查询购物车中的商品")
	@PostMapping("getCartByCartId")
	@ResponseBody
	public  Response<CartProduct> getCartByCartId(
			@ApiParam("用户id") @RequestParam Integer userId
	){
		try {
			if(ObjectUtils.isEmpty(userId)) {
				log.debug("查询失败,userId参数错误");
				return new Response(ExceptionEnum.CART_PRODUCT_GET_FILED.getStatus(),"查询失败参数错误");
			}
			//根据用户id查询购物车
			Cart cart = cartService.getOne(new QueryWrapper<Cart>().eq("userId", userId));

			//根据购物车id查询加入购物车的所有商品
			List<CartProduct> cartProducts = cartProductService.list(new QueryWrapper<CartProduct>().eq("cartId", cart.getId()));
			log.debug("查询成功");
			return new Response(cartProducts);

		} catch (Exception e) {
			log.debug("查询失败",e.getMessage());
			return new Response(ExceptionEnum.CART_PRODUCT_GET_FILED.getStatus(),"系统错误哦");

		}
	}

	
	
}
