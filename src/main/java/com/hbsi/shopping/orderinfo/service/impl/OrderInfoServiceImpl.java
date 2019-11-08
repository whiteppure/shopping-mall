package com.hbsi.shopping.orderinfo.service.impl;

import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderinfo.entity.OrderInfo;
import com.hbsi.shopping.orderinfo.mapper.OrderInfoMapper;
import com.hbsi.shopping.orderinfo.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.shopping.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {


    @Resource
    private  OrderInfoMapper orderInfoMapper;

    /**
     *
     * 创建商品订单
     * @param orderInfo
     * @return
     */
    @Transactional
    public boolean createOrderNotAddCart(OrderInfo orderInfo) {
        try {
            orderInfo.setCreateTime(DateUtils.formatDate(new Date()));
            int flag = orderInfoMapper.insert(orderInfo);
            if (flag<=0) {
                log.debug("创建商品订单失败");
                return  false;
            }
            log.debug("创建订单成功");
            return true;
        } catch (Exception e) {
            log.debug("创建商品订单失败", e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_INFO_ADD_FILED);
        }
    }




}
