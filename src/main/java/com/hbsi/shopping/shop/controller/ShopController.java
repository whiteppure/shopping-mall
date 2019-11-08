package com.hbsi.shopping.shop.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.shop.entity.Shop;
import com.hbsi.shopping.shop.service.IShopService;
import com.hbsi.shopping.user.service.IUserService;
import com.hbsi.shopping.utils.DateUtils;
import com.hbsi.shopping.utils.NumUtils;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@RestController
@RequestMapping("/shop/")
@Api(tags = "店铺模块")
public class ShopController {

    private final IShopService shopService;

    public ShopController(IShopService shopService) {
        this.shopService = shopService;
    }


    @ApiOperation("根据用户id查询店铺信息")
    @GetMapping("getShopInfoByUserId")
    public Response<Shop> getShopInfoByUserId(@ApiParam("用户id") @RequestParam("userId") Integer userId) {

        try {
            if (ObjectUtils.isEmpty(userId)) {
                log.debug("userId参数错误");
                return new Response<>(ExceptionEnum.SHOP_GET_FILED.getStatus(), "userId参数错误");
            }
            Shop shop = shopService.getOne(new QueryWrapper<Shop>().eq("userId", userId));
            log.debug("根据用户id查询店铺信息成功");
            return new Response<>(shop);
        } catch (Exception e) {
            log.debug(e.getMessage(), "查询失败~");
            return new Response<>(ExceptionEnum.SHOP_GET_FILED.getStatus(), "userId参数错误");
        }

    }




    //根据店铺id修改店铺信息
    @PostMapping("updShopById")
    @ApiOperation("根据店铺id修改店铺信息")
    public Response<Shop> updShopById(@RequestBody Shop shop) {
        try {
            Shop shop2 = new Shop();
            shop2.setCreateTime(DateUtils.formatDate(new Date()));
            shop2.setShopDesc(shop.getShopDesc());
            shop2.setShopName(shop.getShopName());
            shop2.setShopImg(shop.getShopImg());
            shop2.setUserName(shop.getUserName());
            boolean flag = shopService.update(shop2, new UpdateWrapper<Shop>(new Shop().setId(shop.getId())));
            if (flag) {
                log.debug("修改成功");
                return new Response<>();
            } else {
                log.debug("修改失败");
                throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED);
            }
        } catch (Exception e) {
            log.debug("修改失败", e.getMessage());
            throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED);
        }
    }


    @GetMapping("getAllShopByPage")
    @ApiOperation("分页查询所有店铺状态不限制")
    public Response<IPage<Shop>> getAllShopByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size
    ) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "size参数错误");
            }
            Page<Shop> page = new Page<Shop>(current, size);
            IPage<Shop> shopList = shopService.page(page, new QueryWrapper<Shop>());
            log.debug("查询成功");
            return new Response<>(shopList);
        } catch (Exception e) {
            log.debug("查询失败", e.getMessage());
            throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "分页查询所有的店铺(不限制状态)失败");
        }
    }


    @ApiOperation("分页查询所有禁用的店铺")
    @GetMapping("getAllDisableShopByPage")
    public Response<IPage<Shop>> getAllDisableShopByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "size参数错误");
            }
            Page<Shop> page = new Page<Shop>(current, size);
            IPage<Shop> shopList = shopService.page(page, new QueryWrapper<Shop>().eq("status", 0));
            log.debug("查询成功");
            return new Response<>(shopList);
        } catch (Exception e) {
            log.debug("查询失败", e.getMessage());
            throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "分页查询所有禁用的店铺失败");
        }

    }


    //分页查询所有正常的店铺
    @ApiOperation("分页查询所有正常的店铺")
    @GetMapping("getAllNormalShopByPage")
    public Response<IPage<Shop>> getAllNormalShopByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size) {
        try {
            if (ObjectUtils.isEmpty(current)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "current参数错误");
            }
            if (ObjectUtils.isEmpty(size)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "size参数错误");
            }
            Page<Shop> page = new Page<Shop>(current, size);
            IPage<Shop> shopList = shopService.page(page, new QueryWrapper<Shop>().eq("status", 1));
            log.debug("查询成功");
            return new Response<>(shopList);
        } catch (Exception e) {
            log.debug("查询失败", e.getMessage());
            throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "分页查询所有正常的店铺失败");
        }
    }


    //根据店铺id查询店铺详情
    @ApiOperation("根据店铺id查询店铺详情")
    @GetMapping("getShopById")
    public Response<Shop> getShopById(
            @ApiParam("店铺id") @RequestParam Integer id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "shopId参数错误");
            }
            Shop shop = shopService.getOne(new QueryWrapper<Shop>().eq("id", id));
            log.debug("查询成功");
            return new Response<>(shop);
        } catch (Exception e) {
            log.debug("查询失败", e.getMessage());
            throw new BaseException(ExceptionEnum.SHOP_GET_FILED, "根据店铺id查询店铺详情失败");
        }
    }


    //禁用店铺
    @PostMapping("diableShopById")
    @ApiOperation("根据店铺id禁用店铺")
    public Response<Shop> diableShopById(@ApiParam("店铺id") @RequestParam Integer shopId) {
        try {
            if (ObjectUtils.isEmpty(shopId)) {
                log.debug("禁用失败");
                throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED, "shopId参数错误");
            }
            boolean flag = shopService.update(new Shop().setShopStatus(0), new UpdateWrapper<Shop>(new Shop().setId(shopId)));
            if (flag) {
                log.debug("禁用成功");
                return new Response<>();
            } else {
                log.debug("禁用失败");
                throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED, "禁用店铺失败");
            }
        } catch (Exception e) {
            log.debug("禁用失败", e.getMessage());
            throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED, "禁用店铺失败");
        }
    }

    //启用店铺
    //根据店铺id修改店铺信息
    @PostMapping("startShopById")
    @ApiOperation("根据店铺id启用店铺")
    public Response<Shop> startShopById(@ApiParam("店铺id") @RequestParam Integer shopId) {
        try {
            if (ObjectUtils.isEmpty(shopId)) {
                log.debug("启用失败");
                throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED, "shopId参数错误");
            }
            boolean flag = shopService.update(new Shop().setShopStatus(1), new UpdateWrapper<Shop>(new Shop().setId(shopId)));
            if (flag) {
                log.debug("启用成功");
                return new Response<>();
            } else {
                log.debug("启用失败");
                throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED, "启用店铺失败");
            }
        } catch (Exception e) {
            log.debug("启用失败", e.getMessage());
            throw new BaseException(ExceptionEnum.SHOP_UPDATE_FILED, "启用店铺失败");
        }
    }


}
