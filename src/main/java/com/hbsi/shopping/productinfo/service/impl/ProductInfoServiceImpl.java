package com.hbsi.shopping.productinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbsi.shopping.address.entity.UserAddress;
import com.hbsi.shopping.address.mapper.UserAddressMapper;
import com.hbsi.shopping.cart.entity.Cart;
import com.hbsi.shopping.cart.mapper.CartMapper;
import com.hbsi.shopping.cartproduct.entity.CartProduct;
import com.hbsi.shopping.cartproduct.mapper.CartProductMapper;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.orderinfo.entity.OrderInfo;
import com.hbsi.shopping.orderinfo.service.impl.OrderInfoServiceImpl;
import com.hbsi.shopping.orderproduct.entity.OrderProduct;
import com.hbsi.shopping.orderproduct.service.impl.OrderProductServiceImpl;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.mapper.ProductInfoMapper;
import com.hbsi.shopping.productinfo.service.IProductInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.shopping.utils.ImageUtils;
import com.hbsi.shopping.utils.NumUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {


    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Resource
    private CartProductMapper cartProductMapper;

    @Resource
    private UserAddressMapper userAddressMapper;

    @Resource
    private OrderInfoServiceImpl orderInfoService;

    @Resource
    private OrderProductServiceImpl orderProductService;


    /**
     * 购买商品
     *
     * @param cartId
     * @param addressId
     * @return
     */
    @Transactional
    public Map<String, Object> buyProductInCart(Integer cartId, Integer addressId) {

        HashMap<String, Object> map = new HashMap<>();

        List<CartProduct> products = cartProductMapper.selectList(new QueryWrapper<CartProduct>().eq("cartId", cartId));

        //根据购物车id获取 用户id 用户名
        Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq("id", cartId));

        UserAddress userAddress = userAddressMapper.selectOne(new QueryWrapper<UserAddress>().eq("id", addressId));
        if (ObjectUtils.isEmpty(userAddress.getAddress())) {
            log.debug("收货地址为空");
            map.put("收货地址未填写", userAddress);
            return map;
        }
        //创建订单编号
        final String orderNum = NumUtils.createOrderNum() + "" + cart.getId();

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderInfoStatus(1);
        orderInfo.setOrderNum(orderNum);
        orderInfo.setUserId(cart.getUserId());
        orderInfo.setUserName(cart.getUserName());
        orderInfo.setAddressId(addressId);
        boolean flag = orderInfoService.createOrderNotAddCart(orderInfo);
        if (!flag) {
            log.debug("创建订单失败");
            throw new BaseException(ExceptionEnum.ADDRESS_IS_EMPTY, "创建用户订单失败");
        }
        //根据用户订单编号插询商品的订单
        OrderInfo order = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("orderNum", orderNum));
        //遍历商品 分别创建订单
        products.forEach(product -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderInfoId(order.getId());
            orderProduct.setShopId(product.getShopId());
            orderProduct.setProductPrice(product.getProductPrice());
            orderProduct.setProductName(product.getProductName());
            orderProduct.setProductCount(product.getProductCount());
            orderProduct.setProductId(product.getProductId());
            if (!orderProductService.addOrderProduct(orderProduct)) {
                log.debug("创建" + product.getId() + "号订单失败");
                return;
            }
            map.put("userAddress", userAddress);
            map.put("userProduct" + product.getId(), product);
        });
        log.debug("结算购物车成功");
        return map;
    }


    /**
     * 修改商品信息
     *
     * @param files
     * @param productInfo
     * @return
     */
    @Override
    @Transactional
    public boolean updateGoods(MultipartFile[] files, ProductInfo productInfo) {

        final  String  NOPIC = "/static/image/product/noPic.jpg";

        try {
            // 有新的图片上传
            // 有图片是"暂无照片"时
            if (!ObjectUtils.isEmpty(files)){
                String[] pathos = ImageUtils.upLoadFiles(files);
                System.out.println("文件数量======>"+pathos.length);
                for (int i = 0; i < pathos.length; i++) {
                    if (productInfo.getProductImg().equals("undefined") || ObjectUtils.isEmpty(productInfo.getProductImg())){
                        productInfo.setProductImg(pathos[i]);
                        continue;
                    }else if (productInfo.getProductImgPic1().equals("undefined") || ObjectUtils.isEmpty(productInfo.getProductImgPic1()) ){
                        productInfo.setProductImgPic1(pathos[i]);
                        continue;
                    }else if ( productInfo.getProductImgPic2().equals("undefined") || ObjectUtils.isEmpty(productInfo.getProductImgPic2())){
                        productInfo.setProductImgPic2(pathos[i]);
                        continue;
                    }else if (productInfo.getProductImgPic3().equals("undefined") || ObjectUtils.isEmpty(productInfo.getProductImgPic3())){
                        productInfo.setProductImgPic3(pathos[i]);
                        continue;
                    }else if (productInfo.getProductImgPic4().equals("undefined") || ObjectUtils.isEmpty(productInfo.getProductImgPic4())){
                        productInfo.setProductImgPic4(pathos[i]);
                    }
                }
            }else{
                //=========== 测试 ok ===========
                // 没有新图片上传 判断 传过来的路径 和 数据库 里边的图片路径比对
                ProductInfo selectById = productInfoMapper.selectById(productInfo.getId());
                //如果不相同 将数据库里边的 该字段图片 设置为 "暂无图片"样式
                if (!productInfo.getProductImg().equals(selectById.getProductImg())){
                    productInfo.setProductImg(NOPIC);
                }
                if (!productInfo.getProductImgPic1().equals(selectById.getProductImgPic1())){
                    productInfo.setProductImgPic1(NOPIC);
                }
                if (!productInfo.getProductImgPic2().equals(selectById.getProductImgPic2())){
                    productInfo.setProductImgPic2(NOPIC);
                }
                if (!productInfo.getProductImgPic3().equals(selectById.getProductImgPic3())){
                    productInfo.setProductImgPic3(NOPIC);
                }
                if (!productInfo.getProductImgPic4().equals(selectById.getProductImgPic4())){
                    productInfo.setProductImgPic4(NOPIC);
                }
            }
            int i = productInfoMapper.updateById(productInfo);
            if (i > 0) {
                log.debug("修改商品成功");
                return true;
            }else {
                log.error("修改商品失败");
                return false;
            }
        } catch (NumberFormatException e) {
            log.debug(e.getMessage(),"修改商品信息异常");
            throw  new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED,"修改商品信息异常");
        }

    }

    /**
     * 从数据库里边随机查询指定数据
     */
    @Override
    public List<ProductInfo> randomDataByCount(Integer count) {
        List<ProductInfo> productInfos = null;
        try {
            productInfos = productInfoMapper.randomDataByCount(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productInfos;
    }


    /**
     * 根据商品id,下一个商品(或上一个商品)判断 并返回 所需id
     *
     * @param id
     * @param direction
     * @return
     */
    @Override
    public Integer productIsExistById(Integer id, String direction) {
        try {
            //查询第一条记录 的 id  select id from product_info order by id asc  LIMIT 1;
            ProductInfo firstProduct = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().select("id").orderByAsc("id").last("limit 1"));
            //查询商品信息表的最后一条记录 的 id   select id from product_info where productInfoStatus=1 order by id DESC limit 1;
            ProductInfo lastProduct = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().select("id").eq("productInfoStatus", 1).orderByDesc("id").last("limit 1"));

            ProductInfo productInfo;
            //向后翻
            if (direction.equals("next")) {
                //到最后了
                if (lastProduct.getId() <= id)
                    return firstProduct.getId();
                //根据id查询 商品信息 如果为空继续向 id + 1 后查询
                while (true) {
                    productInfo = productInfoMapper.selectById(id);
                    //根据 id 查询 商品 若为空
                    if (ObjectUtils.isEmpty(productInfo)) {
                        id += 1;
                        //到最后了
                        if (lastProduct.getId() <= id) {
                            id = firstProduct.getId();
                            break;
                        } else
                            continue;
                    } else
                        break;
                }
            }
            //向前 翻
            if (direction.equals("pre")) {
                if (firstProduct.getId() >= id)
                    return lastProduct.getId();
                while (true) {
                    productInfo = productInfoMapper.selectById(id);
                    //根据 id 查询 商品 若为空
                    if (ObjectUtils.isEmpty(productInfo)) {
                        id -= 1;
                        //防止 退回到边界
                        if (firstProduct.getId().equals(id)) {
                            id = lastProduct.getId();
                            break;
                        } else
                            continue;
                    } else
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

}
