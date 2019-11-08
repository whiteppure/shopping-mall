package com.hbsi.shopping.vo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.shopping.vo.entity.OrderAddressProductVo;
import com.hbsi.shopping.vo.mapper.OrderAddressProductVoMapper;
import com.hbsi.shopping.vo.service.IOrderAddressProductVoService;
import org.springframework.stereotype.Service;


@Service
public class OrderAddressProductVoServiceImpl extends ServiceImpl<OrderAddressProductVoMapper, OrderAddressProductVo> implements IOrderAddressProductVoService {


    @Override
    public Page<OrderAddressProductVo> getAllOrderInfoByShopId(Page<OrderAddressProductVo> page, Integer shopId) {
        return page.setRecords(this.baseMapper.getAllOrderInfoByShopId(page,shopId));
    }

}
