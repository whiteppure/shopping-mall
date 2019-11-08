package com.hbsi.shopping.cartproduct.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hbsi.shopping.cart.service.ICartService;
import com.hbsi.shopping.cart.service.impl.CartServiceImpl;
import com.hbsi.shopping.cartproduct.service.impl.CartProductServiceImpl;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.service.IProductInfoService;
import com.hbsi.shopping.user.entity.User;
import com.hbsi.shopping.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.cart.entity.Cart;
import com.hbsi.shopping.cartproduct.entity.CartProduct;
import com.hbsi.shopping.cartproduct.service.ICartProductService;
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
 * @since 2019-06-28
 */
@Slf4j
@Controller
@RequestMapping("/cartProduct/")
@Api(tags = "购物车中的商品模块")
@CacheConfig(cacheNames = "cartProduct")
public class CartProductController {

    private final ICartProductService cartProductService;

    private final ICartService cartService;

    private final IProductInfoService productInfoService;

    private final CartProductServiceImpl cartProductServiceImpl;

    public CartProductController(ICartProductService cartProductService, ICartService cartService, IProductInfoService productInfoService, CartProductServiceImpl cartProductServiceImpl) {
        this.cartProductService = cartProductService;
        this.cartService = cartService;
        this.productInfoService = productInfoService;
        this.cartProductServiceImpl = cartProductServiceImpl;
    }

    @ResponseBody
    @Transactional
    @ApiOperation("向购物车中添加商品")
    @PostMapping("addProductToCart")
    public Response<CartProduct> addProductToCart(
            @RequestParam("userId") Integer userId,
            @RequestParam("productId") Integer productId,
            @RequestParam("productName") String productName,
            @RequestParam("productImg") String productImg,
            @RequestParam("productPrice") String productPrice,
            @RequestParam("productCount") Integer productCount
            ) {
        try {
            if (ObjectUtils.isEmpty(userId)){
                log.debug("userId参数错误~");
                return new Response<>(ExceptionEnum.CART_PRODUCT_ADD_FILED.getStatus(), "userId参数错误~");
            }
            if (ObjectUtils.isEmpty(productCount)){
                log.debug("productCount参数错误~");
                return new Response<>(ExceptionEnum.CART_PRODUCT_ADD_FILED.getStatus(), "productCount参数错误~");
            }
            if (ObjectUtils.isEmpty(productName)){
                log.debug("productName参数错误~");
                return new Response<>(ExceptionEnum.CART_PRODUCT_ADD_FILED.getStatus(), "productName参数错误~");
            }
            if (ObjectUtils.isEmpty(productId)){
                log.debug("productId参数错误~");
                return new Response<>(ExceptionEnum.CART_PRODUCT_ADD_FILED.getStatus(), "productId参数错误~");
            }
            if (ObjectUtils.isEmpty(productImg)){
                log.debug("productImg参数错误~");
                return new Response<>(ExceptionEnum.CART_PRODUCT_ADD_FILED.getStatus(), "productImg参数错误~");
            }
            if (ObjectUtils.isEmpty(productPrice)){
                log.debug("productPrice参数错误~");
                return new Response<>(ExceptionEnum.CART_PRODUCT_ADD_FILED.getStatus(), "productPrice参数错误~");
            }
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductId(productId);
            cartProduct.setProductName(productName);
            cartProduct.setProductImg(productImg);
            cartProduct.setProductCount(productCount);
            cartProduct.setProductPrice(new BigDecimal(productPrice.trim()));
            //如果购物车id为空
            if (ObjectUtils.isEmpty(cartProduct.getCartId())){
                //根据userId 查询购物车id
                Cart cart = cartService.getOne(new QueryWrapper<Cart>().eq("userId", userId));
                cartProduct.setCartId(cart.getId());
            }
            // 根据购物车中的商品id查询购物车中是否有该商品
            CartProduct product = cartProductService.getOne(new QueryWrapper<CartProduct>().eq("productId", productId));
            if (product != null) {// 存在该商品
                //根据商品id修改该商品的数量
                boolean flag = cartProductService.update(new CartProduct().setProductCount(product.getProductCount()+productCount),
                        new UpdateWrapper<>(new CartProduct().setProductId(productId)));
                if (!flag) {
                    log.debug("二次添加该商品失败");
                    throw new BaseException(ExceptionEnum.CART_PRODUCT_UPDATE_FILED, "修改该商品的数量修改失败");
                }
                log.debug("该商品已经添加到购物车数量加一");
                return new Response<>();
            }
            //根据商品id 查询店铺id
            ProductInfo productOne = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", cartProduct.getProductId()));
            cartProduct.setShopId(productOne.getShopId());
            //该购物车中没有该商品
            cartProduct.setCreateTime(DateUtils.formatDate(new Date()));
            if (cartProductService.save(cartProduct)) {
                log.debug("商品添加到购物车添加成功");
                return new Response<>();
            } else {
                log.debug("添加失败");
                return new Response<>(ExceptionEnum.CART_PRODUCT_ADD_FILED.getStatus(), "商品添加到购物车添加失败");
            }
        } catch (Exception e) {
            log.debug("添加失败", e.getMessage());
            throw new BaseException(ExceptionEnum.CART_PRODUCT_ADD_FILED);
        }
    }


    /**
     * 删除用户购物车中的商品
     *
     * @param @param  id
     * @param @return
     * @author while
     * @data 上午10:16:12
     * @description TODO
     */
    @Transactional
    @GetMapping("deleteCartProductById")
    @ApiOperation("删除用户购物车中的商品")
    @CachePut
    public String deleteCartProductByProductId
    (
            @ApiParam("用户id") @RequestParam("userId") Integer userId,
            @ApiParam("商品id") @RequestParam("productId") Integer productId
    ) {
        try {
            if (ObjectUtils.isEmpty(userId)) {
                log.debug("删除错误");
                throw new BaseException(ExceptionEnum.CART_PRODUCT_DELETE_FILED, "userId参数错误");
            }
            if (ObjectUtils.isEmpty(productId)) {
                log.debug("删除错误");
                throw new BaseException(ExceptionEnum.CART_PRODUCT_DELETE_FILED, "productId参数错误");
            }

            //根据userId 查询购物车id
            Cart cart = cartService.getOne(new QueryWrapper<Cart>().eq("userId", userId));

            //根据商品id删除 购物车中的商品
            boolean flag = cartProductService.remove(new UpdateWrapper<CartProduct>().eq("cartId", cart.getId()).eq("productId", productId));
            if (flag) {
                log.debug("删除成功");
                return "redirect:/shopping-mall/cart-product";
            } else {
                log.debug("删除失败");
                return "redirect:/shopping-mall/cart-product";
            }
        } catch (Exception e) {
            log.debug("删除失败", e.getMessage());
            throw new BaseException(ExceptionEnum.CART_PRODUCT_DELETE_FILED);
        }
    }


    //清空用户购物车里所有商品

    @CachePut
    @PostMapping("deleteCartProductByUserId")
    @ApiOperation("根据用户id清空用户购物车里所有商品")
    @ResponseBody
    public Response<?> deleteCartProductByUserId(@ApiParam("用户id") @RequestParam Integer userId){
        try {
            if (ObjectUtils.isEmpty(userId)){
                log.debug("用户id为空");
                throw new BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED,"用户id为空");
            }
            Boolean flag = cartProductServiceImpl.deleteCartProductByUserId(userId);
            if (!flag){
                log.debug("根据用户id清空用户购物车里所有商品失败");
                return new Response<>(ExceptionEnum.CART_PRODUCT_GET_FILED.getStatus(),"根据用户id清空用户购物车里所有商品失败");
            }else{
                log.debug("根据用户id清空用户购物车里所有商品成功");
                return new Response<>();
            }
        } catch (BaseException e) {
            log.debug("根据用户id清空用户购物车里所有商品失败");
            throw new BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED,"根据用户id清空用户购物车里所有商品失败");
        }
    }

    //获取购物车商品总价
    @ApiOperation("获取购物车商品总价")
    @PostMapping("getPriceInCart")
    @ResponseBody
    public Response<BigDecimal> getPriceInCart(
            @ApiParam("userId") Integer userId
            ){
        try {
            if (ObjectUtils.isEmpty(userId)){
                log.debug("未获取到userId");
                return new Response(ExceptionEnum.CART_PRODUCT_GET_FILED.getStatus(),"未获取到userId");
            }
            //根据用户id获取购物车
            Cart cart = cartService.getOne(new QueryWrapper<Cart>().eq("userId", userId));
            //根据购物车id查询购物车里的商品
            List<CartProduct> products = cartProductService.list(new QueryWrapper<CartProduct>().eq("cartId", cart.getId()));
            if (products == null){
                log.debug("您的购物车没有商品");
                return new Response(ExceptionEnum.CART_PRODUCT_GET_FILED.getStatus(),"您的购物车没有商品");
            }
            BigDecimal productPrice = new BigDecimal(0);
            for (CartProduct product : products) {
                BigDecimal multiply = product.getProductPrice().multiply(new BigDecimal(product.getProductCount()));
                productPrice = multiply.add(productPrice);
            }
            log.debug("获取购物车总价成功"+productPrice);
            return new Response(productPrice);
        } catch (Exception e) {
            log.debug("获取购物车总价失败");
            throw new  BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED);
        }
    }

}
