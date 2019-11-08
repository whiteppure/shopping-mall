package com.hbsi.shopping.ordersend.controller;

import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.ordersend.entity.OrderSend;
import com.hbsi.shopping.ordersend.service.IOrderSendService;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
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
@RequestMapping("/orderSend/")
@Api(tags = "发货模块")
public class OrderSendController {

	@Autowired
	private IOrderSendService orderSendService;

	@ApiOperation("分页查询发货订单信息")
	@GetMapping("getAllSendOrder")
	public Response<IPage<OrderSend>> getAllSendOrder(
    		@ApiParam("当前页") @RequestParam Integer current,
    		@ApiParam("每页数量") @RequestParam Integer size
    		){
		try {
			Page<OrderSend> page = new  Page<OrderSend>(current,size);
			IPage<OrderSend> ipage = orderSendService.page(page,new QueryWrapper<>());
			log.debug("查询成功");
			return  new Response<>(ipage);
		} catch (Exception e) {
			log.debug("查询失败");
			return new Response<>(ExceptionEnum.SEND_ORDER_GET_FILED.getStatus(),"分页查询发货订单失败");
		}
	}


	@PostMapping("addOrderSend")
	@ApiOperation("创建发货订单")
	public Response<OrderSend> addOrderSend(@RequestBody  OrderSend orderSend){
		try {
			boolean flag = orderSendService.save(orderSend);
			if (!flag){
				log.debug("添加发货订单失败");
				throw new BaseException(ExceptionEnum.SEND_ORDER_ADD_FILED);
			}
			log.debug("添加成功");
			return new Response<>();
		} catch (Exception e) {
			log.debug("添加失败",e.getMessage());
			throw new BaseException(ExceptionEnum.SEND_ORDER_ADD_FILED);
		}
	}






}
