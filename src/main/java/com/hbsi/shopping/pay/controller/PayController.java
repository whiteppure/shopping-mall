package com.hbsi.shopping.pay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.cart.entity.Cart;
import com.hbsi.shopping.cart.service.ICartService;
import com.hbsi.shopping.cartproduct.entity.CartProduct;
import com.hbsi.shopping.cartproduct.service.ICartProductService;
import com.hbsi.shopping.cartproduct.service.impl.CartProductServiceImpl;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderinfo.entity.OrderInfo;
import com.hbsi.shopping.orderinfo.service.IOrderInfoService;
import com.hbsi.shopping.orderinfo.service.impl.OrderInfoServiceImpl;
import com.hbsi.shopping.orderproduct.entity.OrderProduct;
import com.hbsi.shopping.orderproduct.service.impl.OrderProductServiceImpl;
import com.hbsi.shopping.pay.service.IPayService;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.service.IProductInfoService;
import com.hbsi.shopping.user.entity.User;
import com.hbsi.shopping.user.service.IUserService;
import com.hbsi.shopping.utils.NumUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@Controller
@RequestMapping("/pay/")
@Api(tags = "商品支付模块")
public class PayController {

    private final IOrderInfoService orderInfoService;

    private final IPayService payService;

    private final ICartProductService cartProductService;

    private final ICartService cartService;

    private final IUserService userService;

    private final IProductInfoService productInfoService;

    private final OrderInfoServiceImpl orderInfoServiceImpl;

    private final OrderProductServiceImpl orderProductServiceImpl;

    private final CartProductServiceImpl cartProductServiceImpl;

    public PayController(IOrderInfoService orderInfoService, IPayService payService, ICartProductService cartProductService, ICartService cartService, IUserService userService, IProductInfoService productInfoService, OrderInfoServiceImpl orderInfoServiceImpl, OrderProductServiceImpl orderProductServiceImpl, CartProductServiceImpl cartProductServiceImpl) {
        this.orderInfoService = orderInfoService;
        this.payService = payService;
        this.cartProductService = cartProductService;
        this.cartService = cartService;
        this.userService = userService;
        this.productInfoService = productInfoService;
        this.orderInfoServiceImpl = orderInfoServiceImpl;
        this.orderProductServiceImpl = orderProductServiceImpl;
        this.cartProductServiceImpl = cartProductServiceImpl;
    }


    /**
     * 购买商品(单个商品) 提交订单 创建订单信息
     * @param productId
     * @param userId
     * @param productCount
     * @param addressId
     * @return
     */
    @GetMapping("payOrder")
    @ApiOperation("提交订单信息")
    @Transactional
    public String payOrder(
            @ApiParam("商品id") @RequestParam Integer productId,
            @ApiParam("用户id") @RequestParam Integer userId,
            @ApiParam("购买商品数量") @RequestParam Integer productCount,
            @ApiParam("收货地址id") @RequestParam Integer addressId,
            Model model) {
        try {
            if (ObjectUtils.isEmpty(userId)){
                log.debug("用户id为空");
                throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"用户id为空");
            }
            if (ObjectUtils.isEmpty(addressId)){
                log.debug("收货地址id为空");
                throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"收货地址id为空");
            }
            if (ObjectUtils.isEmpty(productId)){
                log.debug("商品id为空");
                throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"商品id为空");
            }
            if (ObjectUtils.isEmpty(productCount)){
                log.debug("数量为空");
                throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"数量为空");
            }

            //根据商品id查询商品信息
            ProductInfo productInfo = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", productId));
            //查询用户
            User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
            //创建订单信息
            final String orderNum =  NumUtils.createOrderNum()+""+productInfo.getId();
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserId(user.getId());
            orderInfo.setUserName(user.getUserName());
            orderInfo.setAddressId(addressId);
            //订单价格
            BigDecimal decimal = new BigDecimal(productCount);
            orderInfo.setOrderPrice( productInfo.getProductPrice().multiply(decimal));
            //订单编号
            orderInfo.setOrderNum(orderNum);

            //创建订单信息表
            boolean flag = orderInfoServiceImpl.createOrderNotAddCart(orderInfo);
            if (!flag){
                log.debug("创建订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_ADD_FILED,"创建订单失败");
            }
            //根据订单编号查询该订单
            OrderInfo order = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("orderNum", orderNum));

            //设置商品订单
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderInfoId(order.getId());
            orderProduct.setProductPrice(productInfo.getProductPrice());
            orderProduct.setProductName(productInfo.getProductName());
            orderProduct.setProductCount(productCount);
            orderProduct.setProductId(productInfo.getId());
            orderProduct.setShopId(productInfo.getShopId());
            //保存商品订单
            Boolean flagOrder = orderProductServiceImpl.addOrderProduct(orderProduct);
            if (!flagOrder){
                log.debug("商品订单创建失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_ADD_FILED,"商品订单创建失败");
            }
            log.debug("提交订单信息成功");
            model.addAttribute("orderInfo",order);
            return "/shopping/pay-money-way.html";

        } catch (Exception e) {
            log.debug("提交订单信息失败", e.getMessage());
            throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"提交订单信息失败");
        }

    }


    //购物车 提交商品订单
    @ApiOperation("购物车 提交商品订单")
    @GetMapping("settleCart")
    @Transactional
    public strictfp String settleCart(
            @ApiParam("用户id") @RequestParam Integer userId,
            @ApiParam("收货地址id") @RequestParam Integer addressId,
            @ApiParam("订单价格") @RequestParam  BigDecimal orderPrice,
            Model model
    ){
        try {
            if (ObjectUtils.isEmpty(userId)){
                log.debug("userId为空");
                throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"userId为空");
            }
            if (ObjectUtils.isEmpty(addressId)){
                log.debug("收货地址id为空");
                throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"收货地址id为空");
            }
            if (ObjectUtils.isEmpty(orderPrice)){
                log.debug("订单价格为空");
                throw new BaseException(ExceptionEnum.PAY_ORDER_FILED,"订单价格为空");
            }
            //根据userId 查询 购物车id  --->cartProduct  拿到购物车中的商品
            User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
            Cart cart = cartService.getOne(new QueryWrapper<Cart>().eq("userId", userId));
            String orderNum =  NumUtils.createOrderNum()+""+addressId;
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserId(user.getId());
            orderInfo.setUserName(user.getUserName());
            orderInfo.setAddressId(addressId);
            //订单价格
//            BigDecimal price = new BigDecimal(orderPrice);
            orderInfo.setOrderPrice(orderPrice);
            //订单编号
            orderInfo.setOrderNum(orderNum);
            //创建订单信息表
            if (!orderInfoServiceImpl.createOrderNotAddCart(orderInfo)){
                log.debug("创建订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_ADD_FILED,"创建订单失败");
            }
            //获取购物车里的商品信息
            List<CartProduct> products = cartProductService.list(new QueryWrapper<CartProduct>().eq("cartId", cart.getId()));
            //根据订单编号查询该订单
            OrderInfo order = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("orderNum", orderNum));
            products.forEach(product -> {
                //根据商品id查询商品信息
                ProductInfo productInfo = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", product.getProductId()));
                //创建订单
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrderInfoId(order.getId());
                orderProduct.setProductPrice(productInfo.getProductPrice());
                orderProduct.setProductName(productInfo.getProductName());
                orderProduct.setProductCount(product.getProductCount());
                orderProduct.setProductId(productInfo.getId());
                orderProduct.setShopId(productInfo.getShopId());
                //保存商品订单
                Boolean flagOrder = orderProductServiceImpl.addOrderProduct(orderProduct);
                if (!flagOrder) {
                    log.debug("商品订单创建失败");
                    throw new BaseException(ExceptionEnum.ORDER_INFO_ADD_FILED, "商品订单创建失败");
                }
            });
            //成功保存订单后 删除购物车中的商品
            Boolean flag = cartProductServiceImpl.deleteCartProductByUserId(userId);
            if (!flag){
                log.debug("删除购物车中的商品失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_ADD_FILED, "删除购物车中的商品失败");
            }
            log.debug("提交订单信息成功");
            model.addAttribute("orderInfo",order);
            return "/shopping/pay-money-way.html";
        } catch (BaseException e) {
            log.debug("提交订单失败",e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_ADD_FILED, "商品订单创建失败");

        }
    }


}
