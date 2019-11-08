package com.hbsi.shopping.productinfo.service;

import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
public interface IProductInfoService extends IService<ProductInfo> {


    /**
     * 随机查询指定商品个数
     * @param count
     * @return
     */
    List<ProductInfo> randomDataByCount(Integer count);


    /**
     * 根据商品id,下一个商品(或上一个商品)判断 并返回 所需id
     * @param id 商品id
     * @param direction  方向
     * @return 需要的id
     */
    Integer productIsExistById(Integer id, String direction);


    /**
     * 购买商品
     * @param cartId
     * @param addressId
     * @return
     */
    Map<String, Object> buyProductInCart(Integer cartId, Integer addressId);

    /**
     * 修改 商品
     * @param files
     * @param productInfo
     * @return
     */
    boolean updateGoods(MultipartFile[] files, ProductInfo productInfo);
}
