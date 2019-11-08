package com.hbsi.shopping.pay.service.impl;

import com.hbsi.shopping.pay.entity.Pay;
import com.hbsi.shopping.pay.mapper.PayMapper;
import com.hbsi.shopping.pay.service.IPayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements IPayService {

}
