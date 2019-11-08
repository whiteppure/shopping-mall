package com.hbsi.shopping.vo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.Response;
import com.hbsi.shopping.vo.entity.OrderAddressProductVo;
import com.hbsi.shopping.vo.service.IOrderAddressProductVoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orderAddressProductVo/")
@Api(tags = "用户订单信息模块")
public class OrderAddressProductVoController {

    @Autowired
    private IOrderAddressProductVoService orderAddressProductVoService;


    @GetMapping("getAllOrderInfoByShopId")
    @ApiOperation("根据店铺id分页查询用户的订单信息")
    public Response<IPage<OrderAddressProductVo>> getAllOrderInfoByShopId(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size,
            @ApiParam("店铺id") @RequestParam Integer shopId
    ){
        try {
            if (ObjectUtils.isEmpty(current)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.ORDER_ADDRESS_PRODUCT_GET_FILED,"current参数错误");
            }
            if (ObjectUtils.isEmpty(size)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.ORDER_ADDRESS_PRODUCT_GET_FILED,"size参数错误");
            }
            if (ObjectUtils.isEmpty(shopId)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.ORDER_ADDRESS_PRODUCT_GET_FILED,"shopId参数错误");
            }

            Page<OrderAddressProductVo> page = new Page<>(current,size);
            Page<OrderAddressProductVo> orderInfo = orderAddressProductVoService.getAllOrderInfoByShopId(page, shopId);
            log.debug("查询成功");
            return  new Response<>(orderInfo);
        } catch (BaseException e) {
            log.debug("查询失败",e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_ADDRESS_PRODUCT_GET_FILED);
        }
    }

}
