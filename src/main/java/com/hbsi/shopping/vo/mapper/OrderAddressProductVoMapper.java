package com.hbsi.shopping.vo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.vo.entity.OrderAddressProductVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderAddressProductVoMapper extends BaseMapper<OrderAddressProductVo> {


    /**
     * 根据 店铺id查询 订单信息
     * @param page
     * @param shopId
     * @return
     */
    @Select("select order_info.id,order_info.`orderInfoStatus`,order_info.addressId,user_address.address,user_address.phone,user_address.userName,\n" +
            "order_product.productId,order_product.productName,order_product.shopId \n" +
            "FROM order_info,order_product,user_address\n" +
            "where order_info.id = order_product.orderInfoId AND user_address.id = order_info.addressId  AND order_product.shopId = #{shopId}")
    List<OrderAddressProductVo> getAllOrderInfoByShopId(Page<OrderAddressProductVo> page,@Param("shopId") Integer shopId);

}
