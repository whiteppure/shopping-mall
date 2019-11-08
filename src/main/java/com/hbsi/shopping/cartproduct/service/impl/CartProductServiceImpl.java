package com.hbsi.shopping.cartproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.cart.entity.Cart;
import com.hbsi.shopping.cart.mapper.CartMapper;
import com.hbsi.shopping.cartproduct.entity.CartProduct;
import com.hbsi.shopping.cartproduct.mapper.CartProductMapper;
import com.hbsi.shopping.cartproduct.service.ICartProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderproduct.entity.OrderProduct;
import com.hbsi.shopping.orderproduct.mapper.OrderProductMapper;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.mapper.ProductInfoMapper;
import com.hbsi.shopping.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-28
 */
@Slf4j
@Service
public class CartProductServiceImpl extends ServiceImpl<CartProductMapper, CartProduct> implements ICartProductService {

    @Resource
    private CartProductMapper cartProductMapper;

    @Resource
    private OrderProductMapper orderProductMapper;

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Resource
    private CartMapper cartMapper;


    /**
     * 购买商品 加入购物车
     * @param cartId 购物车id
     * @return
     */
    public BigDecimal getOrderPriceInCart(Integer cartId){
        try {
            //购物车里的订单总价
            BigDecimal sumPrice = BigDecimal.ZERO;
            //根据购物车id查询 加入购物车的 商品
            List<CartProduct> products = cartProductMapper.selectList(new QueryWrapper<CartProduct>().eq("cartId", cartId));
            sumPrice = getBigDecimal(sumPrice, products);
            return sumPrice;
        } catch (Exception e) {
            log.debug("获取购物车里的订单总价失败",e.getMessage());
           throw  new BaseException(ExceptionEnum.ORDER_INFO_GET_PRICE_FILED, "获取购物车里的订单总价失败");

        }
    }

    public static BigDecimal getBigDecimal(BigDecimal sumPrice, List<CartProduct> products) {
        for (CartProduct product: products) {
            Integer productCount = product.getProductCount();
            BigDecimal productPrice = product.getProductPrice();
            BigDecimal orderPrice = BigDecimal.valueOf(productCount).multiply(productPrice);
            sumPrice = sumPrice.add(new BigDecimal(orderPrice+""));
        }
        log.debug("获取购物车里的订单总价成功");
        return sumPrice;
    }


    /**
     * 购买商品 未加入购物车
     * @param productId 购买商品id
     * @param count 购买商品数量
     * @return
     */
    public BigDecimal getProductPrice(Integer productId ,Integer count){
        try {
            ProductInfo product = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().eq("id", productId));
            BigDecimal price = product.getProductPrice().multiply(BigDecimal.valueOf(count));
            log.debug("获取商品订单价格成功");
            return price;
        } catch (Exception e) {
            log.debug("获取商品价格失败", e.getMessage());
            throw  new BaseException(ExceptionEnum.ORDER_INFO_GET_PRICE_FILED, "获取商品订单价格失败");
        }
    }


    /**
     * 根据订单信息id查询
     * @param orderInfoId
     * @return
     */
    public List<OrderProduct> getCartProductByOrderInfoId(Integer orderInfoId){

        try {
            List<OrderProduct> orders = orderProductMapper.selectList(new QueryWrapper<OrderProduct>().eq("orderInfoId", orderInfoId));

            if (orders.isEmpty()){
                log.debug("根据订单信息id查询为空");
                throw new BaseException(ExceptionEnum.ORDER_PRODUCT_GET_EMPTY);
            }
            log.debug("查询成功");
            return orders;
        } catch (BaseException e) {
            log.debug("根据订单信息id查询失败",e.getMessage());
            throw new BaseException(ExceptionEnum.ORDER_PRODUCT_GET_FILED);
        }
    }


    @Transactional
    public Boolean deleteCartProductByUserId(Integer userId){
        try {
            Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq("userId", userId));
            int flag = cartProductMapper.delete(new QueryWrapper<CartProduct>().eq("cartId", cart.getId()));
            if (!(flag > 0)){
                log.debug("根据用户id清空用户购物车里所有商品失败");
                return false;
            }else{
                log.debug("根据用户id清空用户购物车里所有商品成功");
                return true;
            }
        } catch (BaseException e) {
            log.debug("根据用户id清空用户购物车里所有商品失败");
            throw new BaseException(ExceptionEnum.CART_PRODUCT_GET_FILED,"根据用户id清空用户购物车里所有商品失败");
        }

    }



}
