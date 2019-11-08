package com.hbsi.shopping.productinfo.controller;

import java.math.BigDecimal;
import java.util.*;

import com.alipay.api.domain.Image;
import com.alipay.api.domain.Product;
import com.hbsi.shopping.address.entity.UserAddress;
import com.hbsi.shopping.address.service.IUserAddressService;
import com.hbsi.shopping.comment.entity.UserComment;
import com.hbsi.shopping.comment.service.IUserCommentService;
import com.hbsi.shopping.elasticsearch.repository.ProductRepository;
import com.hbsi.shopping.productinfo.service.impl.ProductInfoServiceImpl;
import com.hbsi.shopping.producttype.entity.ProductType;
import com.hbsi.shopping.producttype.service.IProductTypeService;
import com.hbsi.shopping.shop.entity.Shop;
import com.hbsi.shopping.shop.service.IShopService;
import com.hbsi.shopping.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.service.IProductInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@Controller
@RequestMapping("/productInfo/")
@CacheConfig(cacheNames = "shopping-productInfo")
@Api(tags = "商品信息模块")
public class ProductInfoController {

    private final IProductInfoService productInfoService;

    private final ProductRepository productRepository;

    private final IUserAddressService userAddressService;

    private final IUserCommentService userCommentService;

    private final IProductTypeService productTypeService;

    private final IShopService shopService;

    public ProductInfoController(IProductInfoService productInfoService, ProductRepository productRepository, ProductInfoServiceImpl productInfoServiceImpl, IUserAddressService userAddressService, IUserCommentService userCommentService, IProductTypeService productTypeService, IShopService shopService) {
        this.productInfoService = productInfoService;
        this.productRepository = productRepository;
        this.userAddressService = userAddressService;
        this.userCommentService = userCommentService;
        this.productTypeService = productTypeService;
        this.shopService = shopService;
    }


    /**
     * 添加商品信息  前后分离写法
     * <p>
     * 上传多文件
     *
     * @param productInfo 商品对象
     * @return
     */
    @ApiOperation("添加商品信息")
    @PostMapping("addProduct")
    @Transactional
    @ResponseBody
    @CachePut
    public Response<ProductInfo> addProduct(
            @ApiParam("商品图片") @RequestParam(value = "files") MultipartFile[] files,
            ProductInfo productInfo
    ) {
        if (files.length == 0) {
            log.debug("文件参数为空");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_ADD_FILED, "添加商品失败,商品图片不存在");
        }
        try {
            ProductType type = productTypeService.getOne(new QueryWrapper<ProductType>().eq("typeName", productInfo.getProductType()));

            productInfo.setProductTypeId(type.getId());
            productInfo.setCreateDate(DateUtils.formatDate(new Date()));
            final String productNum = NumUtils.createProductNum();
            productInfo.setProductNum(productNum);
            String[] strings = ImageUtils.upLoadFiles(files);

            if (!ObjectUtils.isEmpty(strings[0])) productInfo.setProductImg(strings[0]);
            if (!ObjectUtils.isEmpty(strings[1])) productInfo.setProductImgPic1(strings[1]);
            if (!ObjectUtils.isEmpty(strings[2])) productInfo.setProductImgPic2(strings[2]);
            if (!ObjectUtils.isEmpty(strings[3])) productInfo.setProductImgPic3(strings[3]);
            if (!ObjectUtils.isEmpty(strings[4])) productInfo.setProductImgPic4(strings[4]);

            //根据店铺id查询店铺名称
            Shop shop = shopService.getOne(new QueryWrapper<Shop>().eq("id", productInfo.getShopId()));
            productInfo.setShopName(shop.getShopName());
            if (productInfoService.save(productInfo)) {
                ProductInfo product = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("productNum", productNum));
                Integer productId = product.getId();
                String code = QRCode.createProductCode("https://www.baidu.com", productId + productNum + "");
                boolean updateFlag = productInfoService.update(new ProductInfo().setQRCode(code), new UpdateWrapper<>(new ProductInfo().setId(productId)));
                if (!updateFlag) {
                    log.debug("添加二维码失败");
                    throw new BaseException(ExceptionEnum.PRODUCT_INFO_CREATE_CODE_FILED);
                }
                //向elasticsearch 添加索引
                productRepository.index(product);
                log.debug("添加商品信息成功");
                return new Response<>();
            } else {
                log.debug("查询失败");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_ADD_FILED.getStatus(), "添加商品失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_ADD_FILED, "添加商品异常");
        }


    }


    @GetMapping("updProductLowerShelf")
    @ApiOperation("根据商品id下架商城中的商品")
    @Transactional
    @CachePut
    public Response updProductLowerShelfById(@ApiParam("商品id") @RequestParam Integer productId) {
        try {
            if (ObjectUtils.isEmpty(productId)) {
                log.debug("修改商品状态错误");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED, "商品id参数错误");
            }
            boolean flag = productInfoService.update(new ProductInfo().setProductInfoStatus(-1), new UpdateWrapper<>(new ProductInfo().setId(productId)));
            if (!flag) {
                log.debug("商品下架失败");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED, "商品下架失败");
            }
            log.debug("商品下架成功");
            return new Response(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED.getStatus(), "商品下架失败");
        } catch (BaseException e) {
            log.debug(e.getMessage(), "商品下架失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED, "商品下架失败");
        }
    }


    @GetMapping("updProductUpperShelfById")
    @ApiOperation("根据商品id上架商城中的商品")
    @Transactional
    @CachePut
    @ResponseBody
    public Response<Product> updProductUpperShelfById(@ApiParam @RequestParam Integer id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                log.debug("修改商品状态错误");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED, "商品id参数错误");
            }
            boolean flag = productInfoService.update(new ProductInfo().setProductInfoStatus(1), new UpdateWrapper<>(new ProductInfo().setId(id)));
            if (!flag) {
                log.debug("商品上架失败");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED, "商品上架失败");
            }
            log.debug("商品上架成功");
            return new Response<>();
        } catch (BaseException e) {
            log.debug(e.getMessage(), "商品上架失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED, "商品上架失败");
        }
    }


    /**
     * 点击购买商品 回显购物信息
     *
     * @param productId    商品id
     * @param userId       用户id
     * @param productCount 商品数量
     * @return 商品信息 + 收货地址信息(多个)
     */
    @ApiOperation("购买商品")
    @PostMapping("buyProduct")
    @Transactional
    @ResponseBody
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    public Response<Map<String, Object>> buyProduct(

            @ApiParam("商品id") @RequestParam Integer productId,
            @ApiParam("用户id") @RequestParam Integer userId,
            @ApiParam("购买商品数量") @RequestParam Integer productCount
    ) {
        try {
            if (ObjectUtils.isEmpty(productId)) {
                log.debug("商品id为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "productId参数错误");
            }
            if (ObjectUtils.isEmpty(userId)) {
                log.debug("用户id为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "userId参数错误,您可能未登录");
            }
            if (ObjectUtils.isEmpty(productCount)) {
                log.debug("购买商品为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "productCount参数错误");
            }
            //根据商品id查询商品信息
            ProductInfo productInfo = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", productId));

            HashMap<String, Object> map = new HashMap<>();
            //根据用户id查询用户地址信息
            List<UserAddress> userAddress = userAddressService.list(new QueryWrapper<UserAddress>().eq("userId", userId));
            if (ObjectUtils.isEmpty(userAddress)) {
                log.debug("收货地址未填写");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "收货地址未填写");
            }
            map.put("userAddress", userAddress);
            map.put("userProduct", productInfo);
            log.debug("购买商品成功");
            return new Response<>(map);
        } catch (Exception e) {
            log.debug(e.getMessage(), "购买商品失败");
            return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "购买商品异常");
        }

    }


    /**
     * 购买多个商品
     *
     * @param cartId
     * @return
     */
    @ApiOperation("提交购物车中的商品订单")
    @PostMapping("buyProductOnCart")
    @ResponseBody
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    public Response<Map<String, Object>> buyProductOnCart(
            @ApiParam("购物车id") @RequestParam Integer cartId,
            @ApiParam("收货地址id") @RequestParam Integer addressId
    ) {
        try {
            if (ObjectUtils.isEmpty(cartId)) {
                log.debug("购物车id为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "购物车id为空");
            }
            if (ObjectUtils.isEmpty(addressId)) {
                log.debug("收货地址id为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "addressId为空");
            }
            Map<String, Object> orderInfo = productInfoService.buyProductInCart(cartId, addressId);
            return new Response<>(orderInfo);
        } catch (Exception e) {
            log.debug("购买商品失败", e.getMessage());
            return new Response<>(ExceptionEnum.PRODUCT_INFO_BUY_PRODUCT_FILED.getStatus(), "清空购物车失败");
        }
    }


    /**
     * 根据商品id查询商品详情 +  用户评价
     *
     * @param id
     * @return
     */
    @GetMapping("getAllInfoById")
    @ApiOperation("根据商品id查询商品详情")
    @Cacheable
    @ResponseBody
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    public Response<Map<String, Object>> getAllInfoById(@ApiParam("商品id") @RequestParam Integer id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                log.debug("查询错误");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_FILED.getStatus(), "商品id为空");
            }
            //存放数据
            HashMap<String, Object> map = new HashMap<>();
            ProductInfo product = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", id));
            List<UserComment> userComments = userCommentService.list(new QueryWrapper<UserComment>().orderByDesc("createTime").eq("commentStatus", 1).eq("productId", id));
            if (product == null) {
                log.debug("暂无该商品信息");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_FILED.getStatus(), "未查到该商品,商品id有误");
            }
            log.debug("查询成功");
            map.put("product", product);
            map.put("comments", userComments);
            return new Response<>(map);
        } catch (Exception e) {
            log.debug("查询失败");
            return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_FILED.getStatus(), "根据id查询商品信息查询异常");
        }
    }


    @GetMapping("getGoodsInfoById")
    @ApiOperation("根据商品id查询商品详情修改回显")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @ResponseBody
    public Response<ProductInfo> getGoodsInfoById(
            @ApiParam("商品id") @RequestParam Integer productId) {
        try {
            if (ObjectUtils.isEmpty(productId)) {
                log.debug("商品id为空");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "商品id为空");
            }
            ProductInfo product = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", productId));
            log.debug("查询成功");
            return !ObjectUtils.isEmpty(product)? new Response<>(product): new Response<>(null);
        } catch (Exception e) {
            log.debug("查询失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "查询商品异常");
        }
    }


    /**
     * 根据商品类型id分页查询商品信息
     *
     * @param typeId
     * @param current
     * @param size
     * @return
     */
    @GetMapping("getAllProductByTypeId")
    @ApiOperation("根据商品类型id分页查询商品信息")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @ResponseBody
    public Response<IPage<ProductInfo>> getAllProductByTypeId(@ApiParam("商品类型id") @RequestParam Integer typeId,
                                                              @ApiParam("当前页") @RequestParam Integer current,
                                                              @ApiParam("每页数量") @RequestParam Integer size) {
        try {
            if (ObjectUtils.isEmpty(typeId)) {
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "商品类型id参数错误");
            }
            if (ObjectUtils.isEmpty(current)) {
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "size参数错误");
            }
            Page<ProductInfo> page = new Page<>(current, size);
            IPage<ProductInfo> productType = productInfoService.page(page,
                    new QueryWrapper<>(new ProductInfo().setProductTypeId(typeId)).eq("productInfoStatus", 1));
            log.debug("查询成功");
            return new Response<>(productType);
        } catch (Exception e) {
            log.debug(e.getMessage(), "查询失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据商品类型id查询商品失败");
        }
    }


    /**
     * 根据商品类型id不分页查询商品信息
     *
     * @param typeId
     * @return
     */
    @GetMapping("getAllProductByTypeIdNotPage")
    @ApiOperation("根据商品类型id不分页查询商品信息")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @ResponseBody
    public Response<Map<String, Object>> getAllProductByTypeIdNotPage(
            @ApiParam("商品类型id") @RequestParam("typeId") Integer typeId
    ) {
        try {
            if (ObjectUtils.isEmpty(typeId)) {
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "商品类型id参数错误");
            }
            Map<String, Object> map = new HashMap<>();
            int count = productInfoService.count(new QueryWrapper<ProductInfo>().eq("productTypeId", typeId).eq("productInfoStatus", 1));
            List<ProductInfo> list = productInfoService.list(new QueryWrapper<ProductInfo>().eq("productTypeId", typeId).eq("productInfoStatus", 1));
            if (ObjectUtils.isEmpty(list)) {
                log.debug("类型id不存在或该类型商品信息为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_IS_EMPTY.getStatus(), "查询商品类型信息为空或不存在");
            }
            log.debug("查询成功");
            map.put("goods", list);
            map.put("total", count);
            return new Response<>(map);
        } catch (Exception e) {
            log.debug(e.getMessage(), "查询失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据商品类型id不分页查询商品失败");
        }
    }


    @GetMapping("getProductInfoByName")
    @ApiOperation("根据商品名称查询商品信息")
    @ResponseBody
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    public Response<ProductInfo> getProductInfoByName(@RequestParam("productName") String productName) {
        ProductInfo productInfo = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("productName", productName));
        if (productInfo == null) {
            log.debug("暂无商品信息");
            return new Response<>();
        }
        return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_FILED.getStatus(), "该商品已经存在,请勿重复添加!");
    }


    @ApiOperation("修改商品信息")
    @PostMapping("updProduct")
    @ResponseBody
    @CachePut
    public Response updProduct(
            @RequestParam(value = "files[]") MultipartFile[] files,
            ProductInfo productInfo
    ) {
        return productInfoService.updateGoods(files,productInfo)? new Response("修改商品信息成功"):new Response(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED.getStatus(),"修改商品信息失败");
    }


    /**
     * 根据商品名称模糊查询
     */
    @ApiOperation("根据关键字模糊不分页查询商品")
    @GetMapping("getProductByName")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @ResponseBody
    public Response<List<ProductInfo>> getProductByName(@RequestParam("keyWord") String keyWord) {

        try {
            if (ObjectUtils.isEmpty(keyWord)) {
                log.debug("参数为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_PRODUCT_BY_KEY_WORD_FILED.getStatus(), "关键字不能为空");
            }

            List<ProductInfo> list = productInfoService.list(new QueryWrapper<ProductInfo>().like("productName", keyWord).eq("productInfoStatus", 1));

            if (list.isEmpty()) {
                log.debug("查询结果为空");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_PRODUCT_BY_KEY_WORD_FILED.getStatus(), "暂无您想要的结果");
            }
            log.debug("根据商品名称模糊查询成功");
            return new Response<>(list);
        } catch (Exception e) {
            log.debug(e.getMessage(), "根据商品名称模糊查询失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_PRODUCT_BY_KEY_WORD_FILED);
        }
    }


    //不分页查询所有商品
    @ApiOperation("不分页查询所有商品")
    @GetMapping("getProducts")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @ResponseBody
    public Response<List<ProductInfo>> getProducts() {
        try {
            List<ProductInfo> list = productInfoService.list(new QueryWrapper<ProductInfo>().eq("productInfoStatus", 1));
            return new Response<>(list);
        } catch (Exception e) {
            log.debug("不分页查询所有商品失败");
            return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_FILED.getStatus(), "查询商品失败");
        }
    }

    //分页查询所有商品
    //@ResponseBody


    //根据店铺id查询所有商品 不分页查询
    @PostMapping("getProductsByShopId")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @ApiOperation("根据店铺id查询所有商品 ")
    @ResponseBody
    public Response<List<ProductInfo>> getProductsByShopId(@RequestParam("shopId") Integer shopId) {
        try {
            if (ObjectUtils.isEmpty(shopId)) {
                log.debug("shopId参数错误~");
                return new Response<>(ExceptionEnum.PRODUCT_INFO_GET_FILED.getStatus(), "shopId参数错误");
            }
            List<ProductInfo> productInfos = productInfoService.list(new QueryWrapper<ProductInfo>().eq("shopId", shopId).eq("productInfoStatus", 1));
            log.debug("查询成功");
            return new Response<>(productInfos);
        } catch (Exception e) {
            log.debug(e.getMessage(), "根据店铺id查询商品信息失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据店铺id查询商品信息失败");
        }

    }

    //根据商品id 查询该商品类型的全部商品(分页)
    @ResponseBody
    @ApiOperation("根据商品id查询该商品类型的全部商品")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @GetMapping("getTypeProductsByPage")
    public Response<IPage<ProductInfo>> getTypeProductsByPage(@ApiParam("商品id") @RequestParam Integer productId,
                                                              @ApiParam("当前页") @RequestParam Integer current,
                                                              @ApiParam("每页数量") @RequestParam Integer size) {
        try {
            Page<ProductInfo> page = new Page<>(current, size);
            ProductInfo productInfo = productInfoService.getOne(new QueryWrapper<ProductInfo>().eq("id", productId));
            IPage<ProductInfo> iPage = productInfoService.page(page, new QueryWrapper<ProductInfo>().eq("productTypeId", productInfo.getProductTypeId()));
            log.debug("根据商品id查询该商品类型的全部商品成功");
            return new Response<>(iPage);
        } catch (Exception e) {
            log.debug(e.getMessage(), "查询失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据商品id查询该商品类型的全部商品失败");
        }
    }


    /**
     * 随机查询指定商品个数
     *
     * @param count
     * @return
     */
    @ResponseBody
    @GetMapping("randomNumByCount")
    @Caching(cacheable = {@Cacheable}, put = {@CachePut})
    @ApiOperation("随机查询指定商品个数")
    public Response<List<ProductInfo>> randomNumByCount(@RequestParam("count") Integer count) {
        if (ObjectUtils.isEmpty(count)) {
            log.debug("random参数为空");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "参数为空");
        }
        List<ProductInfo> list = productInfoService.randomDataByCount(count);
        log.debug("随机查询" + count + "个商品成功");
        return new Response<>(list);
    }


    @ResponseBody
    @ApiOperation("根据id判断商品是否存在")
    @Cacheable
    @GetMapping("productIsExistById")
    public Response<Integer> productIsExistById(@RequestParam("id") Integer id, String direction) {
        if (ObjectUtils.isEmpty(id)) {
            log.debug("id为空");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据id判断商品失败,id为空");
        }

        if (ObjectUtils.isEmpty(direction)) {
            log.debug("direction为空");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据id判断商品失败,direction为空");
        }
        Integer idTemp = productInfoService.productIsExistById(id, direction);
        return new Response<>(idTemp);
    }


}
