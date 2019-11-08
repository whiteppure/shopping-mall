package com.hbsi.shopping.orderproduct.service.impl;

import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderproduct.entity.OrderProduct;
import com.hbsi.shopping.orderproduct.mapper.OrderProductMapper;
import com.hbsi.shopping.orderproduct.service.IOrderProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-07-05
 */
@Slf4j
@Service
public class OrderProductServiceImpl extends ServiceImpl<OrderProductMapper, OrderProduct> implements IOrderProductService {


    @Resource
    private OrderProductMapper orderproductMapper;


    /**
     * 多个商品
     * 添加(购物车中的)商品订单信息
     * @param orderProduct
     * @return
     */
    public Boolean addOrderProduct(OrderProduct orderProduct){

        try {
            int insert = orderproductMapper.insert(orderProduct);
            if (insert>0){
                log.debug("添加商品订单成功");
                return true;
            }
            return false;
        } catch (Exception e) {
            log.debug("添加商品订单失败");
            throw new BaseException(ExceptionEnum.ORDER_PRODUCT_ADD_FILED);
        }
    }


}
