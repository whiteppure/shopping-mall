package com.hbsi.shopping.orderreturn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderproduct.entity.OrderProduct;
import com.hbsi.shopping.orderreturn.entity.OrderReturn;
import com.hbsi.shopping.orderreturn.service.IOrderReturnService;
import com.hbsi.shopping.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@RestController
@RequestMapping("/orderReturn/")
@Api(tags = "订单退货模块")
public class OrderReturnController {

    private final IOrderReturnService orderReturnService;

    public OrderReturnController(IOrderReturnService orderReturnService) {
        this.orderReturnService = orderReturnService;
    }


    @ApiOperation("用户退货")
    @PostMapping("returnProducts")
    public Response<OrderReturn> returnProducts(@RequestBody OrderReturn orderReturn){
        try {
            boolean flag = orderReturnService.save(orderReturn);
            if (!flag){
                log.debug("退货失败");
                return new Response<>(ExceptionEnum.RETURN_ORDER_ADD_FILED.getStatus(),"退货失败");
            }
            log.debug("退货成功");
            return  new Response<>();
        } catch (Exception e) {
            log.debug("退货失败",e.getMessage());
            return new Response<>(ExceptionEnum.RETURN_ORDER_ADD_FILED.getStatus(),"退货失败");
        }
    }


    @ApiOperation("分页查询全部退货订单")
    @PostMapping("getAllReturnProductByPage")
    public Response<IPage<OrderReturn>> getAllReturnProductByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size
    ){

        try {
            if (ObjectUtils.isEmpty(current)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.RETURN_ORDER_GET_FILED,"current参数错误");
            }
            if (ObjectUtils.isEmpty(size)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.RETURN_ORDER_GET_FILED,"size参数错误");
            }
            Page<OrderReturn> page = new Page<>(current,size);
            IPage<OrderReturn> returnIPage = orderReturnService.page(page, new QueryWrapper<>());
            if (returnIPage == null){
                log.debug("暂无退货订单");
                return  new Response<>(ExceptionEnum.RETURN_ORDER_GET_EMPTY.getStatus(),"暂无退货订单");
            }
            log.debug("查询成功");
            return  new Response<>(returnIPage);
        } catch (Exception e) {
            log.debug("查询失败",e.getMessage());
            throw new BaseException(ExceptionEnum.RETURN_ORDER_GET_FILED,"分页查询退货订单失败");
        }
    }




    @ApiOperation("根据退货订单id查询退货详情")
    @GetMapping("getOrderReturnById")
    public Response<OrderReturn> getOrderReturnById(@ApiParam("退货订单id") @RequestParam Integer id){
        try {
            if (ObjectUtils.isEmpty(id)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.RETURN_ORDER_GET_FILED,"id为空");
            }
            OrderReturn orderReturn = orderReturnService.getOne(new QueryWrapper<OrderReturn>().eq("id", id));
            log.debug("查询成功");
            return  new Response<>(orderReturn);
        } catch (Exception e) {
            log.debug("查询错误",e.getMessage());
            throw new BaseException(ExceptionEnum.RETURN_ORDER_GET_FILED,"根据退货订单id查询失败");
        }
    }




    @ApiOperation("根据退货订单id删除退货订单")
    @GetMapping("deleteOrderReturnById")
    public  Response<OrderProduct> deleteOrderReturnById(@ApiParam("退货订单id") @RequestParam Integer id ){
        try {
            if (ObjectUtils.isEmpty(id)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.RETURN_ORDER_GET_FILED,"id为空");
            }
            boolean flag = orderReturnService.remove(new UpdateWrapper<OrderReturn>().eq("id", id));
            if (!flag){
                log.debug("删除失败");
                return new Response<>(ExceptionEnum.RETURN_ORDER_DELETE_FILED.getStatus(),"根据退货订单id删除失败");
            }
            return  new Response<>();
        } catch (Exception e) {
            log.debug("查询错误",e.getMessage());
            throw new BaseException(ExceptionEnum.RETURN_ORDER_GET_FILED,"删除失败");
        }
    }





}
