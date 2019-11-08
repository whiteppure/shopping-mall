package com.hbsi.shopping.vo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbsi.shopping.vo.entity.OrderAddressProductVo;


public interface IOrderAddressProductVoService   extends IService<OrderAddressProductVo> {

    /**
     * 根据店铺id 查询订单信息
     * @param page
     * @param shopId
     * @return
     */
    Page<OrderAddressProductVo> getAllOrderInfoByShopId(Page<OrderAddressProductVo> page, Integer shopId);

}
