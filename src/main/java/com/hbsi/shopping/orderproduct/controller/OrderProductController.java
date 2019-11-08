package com.hbsi.shopping.orderproduct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderproduct.entity.OrderProduct;
import com.hbsi.shopping.orderproduct.service.IOrderProductService;
import com.hbsi.shopping.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-07-05
 */
@Slf4j
@Controller
@RequestMapping("/orderProduct/")
@Api(tags = "商品订单模块")
public class OrderProductController {

    private final IOrderProductService orderProductService;

    public OrderProductController(IOrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    //根据订单信息id不分页查询
    @GetMapping("getAllOrderProductByOrderId")
    @ApiOperation("根据订单信息id不分页查询")
    public Response<List<OrderProduct>> getAllOrderProductByOrderId(
            @ApiParam("订单信息id") @RequestParam Integer orderInfoId

    ) {
        try {
            if (ObjectUtils.isEmpty(orderInfoId)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.ORDER_PRODUCT_GET_FILED, "orderInfoId参数错误");
            }
            List<OrderProduct> orderProductList = orderProductService.list(new QueryWrapper<OrderProduct>().eq("orderInfoId", orderInfoId));
            log.debug("查询成功");
            return new Response<>(orderProductList);
        } catch (BaseException e) {
            log.debug("查询失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_PRODUCT_GET_FILED);
        }
    }




}
