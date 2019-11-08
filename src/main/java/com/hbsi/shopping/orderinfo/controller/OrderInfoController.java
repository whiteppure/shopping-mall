package com.hbsi.shopping.orderinfo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.address.entity.UserAddress;
import com.hbsi.shopping.address.service.IUserAddressService;
import com.hbsi.shopping.cartproduct.service.impl.CartProductServiceImpl;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderinfo.entity.OrderInfo;
import com.hbsi.shopping.orderinfo.service.IOrderInfoService;
import com.hbsi.shopping.orderproduct.entity.OrderProduct;
import com.hbsi.shopping.orderproduct.service.IOrderProductService;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.service.IProductInfoService;
import com.hbsi.shopping.productsell.entity.ProductSell;
import com.hbsi.shopping.productsell.service.IProductSellService;
import com.hbsi.shopping.utils.DateUtils;
import com.hbsi.shopping.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/orderinfo/")
@Api(tags = "订单信息模块")
public class OrderInfoController {


    private final IOrderInfoService orderInfoService;

    private final IProductInfoService productInfoService;

    private final IProductSellService productSellService;

    private final CartProductServiceImpl cartProductServiceImpl;

    private final IOrderProductService orderProductService;

    private final IUserAddressService userAddressService;

    public OrderInfoController(IOrderInfoService orderInfoService, IProductInfoService productInfoService, IProductSellService productSellService, CartProductServiceImpl cartProductServiceImpl, IOrderProductService orderProductService, IUserAddressService userAddressService) {
        this.orderInfoService = orderInfoService;
        this.productInfoService = productInfoService;
        this.productSellService = productSellService;
        this.cartProductServiceImpl = cartProductServiceImpl;
        this.orderProductService = orderProductService;
        this.userAddressService = userAddressService;
    }


    //根据id修改订单状态  ---->订单已发货
    @ApiOperation("根据id修改订单为已发货")
    @GetMapping("updOrderSend")
    @ResponseBody
    public Response<OrderInfo> updOrderSend(@ApiParam("订单id") @RequestParam Integer id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                log.debug("id参数错误");
                return new Response<>(ExceptionEnum.ORDER_INFO_UPDATE_FILED.getStatus(), "订单id参数错误");
            }

            boolean flag = orderInfoService.update(new OrderInfo().setOrderInfoStatus(0), new QueryWrapper<>(new OrderInfo().setId(id)));
            if (!flag) {
                log.debug("修改订单状态为已发货修改失败");
                return new Response<>(ExceptionEnum.ORDER_INFO_UPDATE_FILED.getStatus(), "修改订单状态为已发货失败");
            }
            log.debug("修改成功");
            return new Response<>();
        } catch (Exception e) {
            log.debug("修改订单状态为已发货修改失败", e.getMessage());
            return new Response<>(ExceptionEnum.ORDER_INFO_UPDATE_FILED.getStatus(), "修改订单状态为已发货修改失败");
        }
    }




    //根据id修改订单状态  ---->订单已完成
    //向用户已经购买的商品表里添加一条数据
    @ApiOperation("根据id修改订单为已完成")
    @GetMapping("updOrderComplete")
    @ResponseBody
    public Response<OrderInfo> updOrderComplete(@ApiParam("订单id") @RequestParam Integer id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                log.debug("id参数错误");
                return new Response<>(ExceptionEnum.ORDER_INFO_UPDATE_FILED.getStatus(), "订单id参数错误");
            }

            boolean flag = orderInfoService.update(new OrderInfo().setOrderInfoStatus(2), new QueryWrapper<>(new OrderInfo().setId(id)));
            if (!flag) {
                log.debug("修改订单状态为已完成修改失败");
                return new Response<>(ExceptionEnum.ORDER_INFO_UPDATE_FILED.getStatus(), "修改订单状态为已完成修改失败");
            }

            OrderInfo orderInfo = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("id", id));

            //根据订单id查询 商品订单信息
            List<OrderProduct> orderProductList = orderProductService.list(new QueryWrapper<OrderProduct>().eq("orderInfoId",id));
            orderProductList.forEach( orderProduct -> {
                //根据商品id查询 商品信息
                ProductInfo productInfo = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", orderProduct.getProductId()));
                //创建productSell对象
                ProductSell sell = new ProductSell();
                sell.setCreateTime(DateUtils.formatDate(new Date()));
                sell.setProducntId(productInfo.getId());
                sell.setProductImg(productInfo.getProductImg());
                sell.setProductName(productInfo.getProductName());
                sell.setShopId(productInfo.getShopId());
                sell.setUserId(orderInfo.getUserId());
                sell.setUserName(orderInfo.getUserName());
                if (!productSellService.save(sell)) {
                    log.debug("修改失败,添加已售商品失败");
                    return;
                }
            });
            log.debug("修改成功");
            return new Response<>();
        } catch (Exception e) {
            log.debug("修改订单状态为已发货失败", e.getMessage());
            return new Response<>(ExceptionEnum.ORDER_INFO_UPDATE_FILED.getStatus(), "修改订单状态为已完成修改失败");
        }
    }


    //获取订单价格    购买商品id  购买商品数量
    @ApiOperation("获取订单价格")
    @GetMapping("getOrderPrice")
    @ResponseBody
    public Response<BigDecimal> getOrderPrice(
            @ApiParam("购买商品的id") @RequestParam Integer productId,
            @ApiParam("购买商品的数量") @RequestParam Integer count
    ) {
        try {
            if (ObjectUtils.isEmpty(productId)) {
                log.debug("productId参数错误");
                return new Response<>(ExceptionEnum.ORDER_INFO_GET_PRICE_FILED.getStatus(), "productId参数错误");
            }
            if (ObjectUtils.isEmpty(count)) {
                log.debug("Count参数错误");
                return new Response<>(ExceptionEnum.ORDER_INFO_GET_PRICE_FILED.getStatus(), "count参数错误");
            }
            BigDecimal price = cartProductServiceImpl.getProductPrice(productId,count);
            log.debug("商品价格获取成功");
            return new Response<>(price);
        } catch (Exception e) {
            log.debug("获取商品价格失败", e.getMessage());
            return new Response<>(ExceptionEnum.ORDER_INFO_GET_PRICE_FILED.getStatus(), "获取商品价格失败");
        }

    }




    @ApiOperation("获取购物车里的订单总价")
    @GetMapping("getOrderPriceInCart")
    @ResponseBody
    public Response<BigDecimal> getOrderPriceInCart(
            @ApiParam("购物车id") @RequestParam Integer cartId) {
        try {
            if (ObjectUtils.isEmpty(cartId)) {
                log.debug("获取订单价格失败");
                return new Response<>(ExceptionEnum.ORDER_INFO_GET_PRICE_FILED.getStatus(), "获取购物车里的订单价格失败");
            }
            BigDecimal price = cartProductServiceImpl.getOrderPriceInCart(cartId);
            //购物车里的订单总价
            return new Response<>(price);
        } catch (Exception e) {
            log.debug("获取购物车里的订单总价失败");
            return new Response<>(ExceptionEnum.ORDER_INFO_GET_PRICE_FILED.getStatus(), "获取购物车里的订单总价失败");
        }

    }


    //分页查询全部订单
    @ApiOperation("分页查询全部订单")
    @GetMapping("getAllOrderByPage")
    @ResponseBody
    public Response<IPage<OrderInfo>> getAllOrderByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size
    ) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("分页查询全部订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("分页查询全部订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "size参数错误");
            }
            Page<OrderInfo> page = new Page<>(current, size);

            IPage<OrderInfo> iPage = orderInfoService.page(page, new QueryWrapper<OrderInfo>().orderByDesc("createTime"));
            log.debug("分页查询订单成功");
            return new Response<>(iPage);
        } catch (BaseException e) {
            log.debug("分页查询全部订单失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "分页查询订单失败");
        }

    }


    //根据id查询订单详情
    @ApiOperation("根据订单id查询订单详情")
    @GetMapping("getOrderInfoById")
    @ResponseBody
    public Response<OrderInfo> getOrderInfoById(@ApiParam @RequestParam Integer id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                log.debug("查询订单详情失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "订单id参数错误");
            }
            OrderInfo orderInfo = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("id", id));
            log.debug("查询订单详情成功");
            return new Response<>(orderInfo);
        } catch (BaseException e) {
            log.debug("查询订单详情失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "查询订单详情失败");
        }
    }


    //分页查询已完成的订单
    @ApiOperation("分页查询已完成订单")
    @GetMapping("getOrderCompleteByPage")
    @ResponseBody
    public Response<IPage<OrderInfo>> getOrderCompleteByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size
    ) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("分页查询已完成订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("分页查询已完成订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "size参数错误");
            }
            Page<OrderInfo> page = new Page<>(current, size);

            IPage<OrderInfo> iPage = orderInfoService.page(page, new QueryWrapper<OrderInfo>().orderByDesc("createTime").eq("status", 2));
            log.debug("分页查询已完成成功");
            return new Response<>(iPage);
        } catch (BaseException e) {
            log.debug("分页查询已完成订单失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "分页查询已完成失败");
        }

    }


    //分页查询待发货的订单
    @ApiOperation("分页查询待发货订单")
    @GetMapping("getAllOrderUnshippedByPage")
    @ResponseBody
    public Response<IPage<OrderInfo>> getAllOrderUnshippedByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size
    ) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("分页查询待发货订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("分页查询待发货订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "size参数错误");
            }
            Page<OrderInfo> page = new Page<>(current, size);

            IPage<OrderInfo> iPage = orderInfoService.page(page, new QueryWrapper<OrderInfo>().orderByDesc("createTime").eq("status", 1));
            log.debug("分页查询待发货订单成功");
            return new Response<>(iPage);
        } catch (BaseException e) {
            log.debug("分页查询待发货订单订单失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "分页查询待发货订单失败");
        }

    }




    //根据用户id分页查询全部订单
    @ApiOperation("根据用户id分页查询订单信息")
    @GetMapping("getAllOrderByUserIdPage")
    @ResponseBody
    public Response<IPage<OrderInfo>> getAllOrderByUserIdPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size,
            @ApiParam("用户id") @RequestParam Integer userId
    ) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("根据用户id分页查询订单信息失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("根据用户id分页查询订单信息失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "size参数错误");
            }
            if (ObjectUtils.isEmpty(userId)) {
                log.debug("根据用户id分页查询订单信息失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "userId参数错误");
            }
            Page<OrderInfo> page = new Page<>(current, size);
            IPage<OrderInfo> userOrder = orderInfoService.page(page, new QueryWrapper<OrderInfo>().eq("userId", userId));
            log.debug("根据用户id分页查询订单信息成功");
            return new Response<>(userOrder);
        } catch (BaseException e) {
            log.debug("根据用户id分页查询订单信息失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "根据用户id分页查询订单信息失败");
        }
    }





    @ApiOperation("根据用户id不分页查询订单信息")
    @GetMapping("getAllOrderByUserId")
    @ResponseBody
    public Response<List<OrderInfo>> getAllOrderByUserId(
            @ApiParam("用户id") @RequestParam Integer userId
    ) {
        try {
            if (ObjectUtils.isEmpty(userId)) {
                log.debug("根据用户id不分页查询订单信息失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "userId参数错误");
            }
            List<OrderInfo> userOrder = orderInfoService.list(new QueryWrapper<OrderInfo>().eq("userId", userId));
            log.debug("根据用户id分页查询订单信息成功");
            return new Response<>(userOrder);
        } catch (BaseException e) {
            log.debug("根据用户id不分页查询订单信息失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "根据用户id不分页查询订单信息失败");
        }
    }












    //根据用户id订单状态分页查询订单信息
    @ApiOperation("根据用户id订单状态分页查询订单信息")
    @GetMapping("getAllOrderByUserIdAndStatus")
    @ResponseBody
    public Response<IPage<OrderInfo>> getAllOrderByUserIdAndStatus(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size,
            @ApiParam("用户id") @RequestParam Integer userId,
            @ApiParam("订单状态: {1:待发货,2:已完成,0:已发货,-1:已删除}") @RequestParam Integer status
    ) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("根据用户id订单状态分页查询订单信息失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("根据用户id订单状态分页查询订单信息失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "size参数错误");
            }
            if (ObjectUtils.isEmpty(status)) {
                log.debug("根据用户id订单状态分页查询订单信息失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "status参数错误");
            }
            Page<OrderInfo> page = new Page<>(current, size);
            IPage<OrderInfo> userOrder = orderInfoService.page(page, new QueryWrapper<OrderInfo>().eq("userId", userId).eq("status", status));
            log.debug("根据用户id订单状态分页查询订单信息成功");
            return new Response<>(userOrder);
        } catch (BaseException e) {
            log.debug("根据用户id订单状态分页查询订单信息失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED, "根据用户id订单状态分页查询订单信息失败");
        }
    }


    //删除订单信息
    @ApiOperation("根据订单id删除订单")
    @PostMapping("deleteOrderById")
    @ResponseBody
    public Response<OrderInfo> deleteOrderById(@ApiParam("订单id") @RequestParam Integer id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                log.debug("删除订单错误");
                throw new BaseException(ExceptionEnum.ORDER_INFO_DELETE_FILED, "订单id参数错误");
            }
            boolean flag = orderInfoService.update(new OrderInfo().setOrderInfoStatus(-1), new UpdateWrapper<>(new OrderInfo().setId(id)));
            if (!flag) {
                log.debug("删除订单失败");
                throw new BaseException(ExceptionEnum.ORDER_INFO_DELETE_FILED, "根据订单id删除订单");
            }
            log.debug("删除订单成功");
            return new Response<>();
        } catch (BaseException e) {
            log.debug("删除订单失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_DELETE_FILED, "根据订单id删除订单");
        }
    }




    @GetMapping("getOrderByOrderId")
    @ApiOperation("根据订单id查询订单信息")
    public  String getOrderByOrderId(Integer orderId, Model model){
        try {
            if (ObjectUtils.isEmpty(orderId)){
                log.debug("orderId为空");
                throw new  BaseException(ExceptionEnum.ORDER_INFO_GET_FILED);
            }
            List<OrderProduct> products = orderProductService.list(new QueryWrapper<OrderProduct>().eq("orderInfoId", orderId));
            //根据订单id查询用户地址
            OrderInfo orderInfo = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("id", orderId));
            UserAddress userAddress = userAddressService.getOne(new QueryWrapper<UserAddress>().eq("id", orderInfo.getAddressId()));
            model.addAttribute("products",products);
            model.addAttribute("address",userAddress);
            log.debug("查询用户订单信息成功");
            return "/shop/my-order";
        } catch (Exception e) {
            log.debug(e.getMessage(),"查询用户订单信息失败");
            throw new  BaseException(ExceptionEnum.ORDER_INFO_GET_FILED);

        }
    }


    @GetMapping("getOrderInfoByNum")
    @ResponseBody
    @ApiOperation("根据订单编号查询订单信息")
    public Response<OrderInfo> getOrderInfoByNum(@RequestParam("orderNum") String orderNum){
        try {
            if (ObjectUtils.isEmpty(orderNum)){
                log.debug("orderNum为空");
                return new Response<>(ExceptionEnum.ORDER_INFO_GET_FILED.getStatus(),"参数错误");
            }
            OrderInfo orderInfo = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("orderNum", orderNum));
            log.debug("查询订单信息成功");
            return new Response<>(orderInfo);
        } catch (Exception e) {
            log.debug("查询订单信息失败~",e.getMessage());
            return new Response<>(ExceptionEnum.ORDER_INFO_GET_FILED.getStatus(),"查询订单信息失败");
        }
    }



}
