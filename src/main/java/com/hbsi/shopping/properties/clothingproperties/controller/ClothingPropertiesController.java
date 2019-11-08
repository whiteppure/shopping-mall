package com.hbsi.shopping.properties.clothingproperties.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.properties.clothingproperties.entity.ClothingProperties;
import com.hbsi.shopping.properties.clothingproperties.service.IClothingPropertiesService;
import com.hbsi.shopping.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-30
 */
@Slf4j
@RestController
@RequestMapping("/clothingProperties/")
@Api(tags = "服装类属性信息模块")
public class ClothingPropertiesController {

    @Autowired
    private IClothingPropertiesService clothingPropertiesService;


    @GetMapping("getAllClothingpertiesByPage")
    @ApiOperation("分页查询所有服装类属性信息")
    public Response<IPage<ClothingProperties>> getAllClothingpertiesByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size
    ){
        try {
            if(ObjectUtils.isEmpty(current)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"current参数错误");
            }
            if(ObjectUtils.isEmpty(size)) {
                log.debug("查询失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"size参数错误");
            }
            Page<ClothingProperties> page = new Page<>();
            IPage<ClothingProperties> clothingProperties = clothingPropertiesService.page(page,new QueryWrapper<>());
            log.debug("查询成功");
            return new Response<>(clothingProperties);
        } catch (Exception e) {
            log.debug("查询失败",e.getMessage());
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"分页查询服装类属性失败");
        }
    }



    @ApiOperation("不分查询全部服装类属性信息")
    @GetMapping("getClothingProperties")
    public Response<List<ClothingProperties>> getClothingProperties(){
        try {
            List<ClothingProperties> clothingPropertiesList = clothingPropertiesService.list();
            log.debug("查询成功");
            return new Response<>(clothingPropertiesList);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("查询失败",e.getMessage());
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"不分页查询服装类型失败");
        }
    }



    @ApiOperation("根据id修改服装类属性信息")
    @PostMapping("updClothingProperties")
    public Response<ClothingProperties> updClothingProperties(@RequestBody ClothingProperties clothingProperties){
        ClothingProperties clothing = new ClothingProperties();
        clothing.setClothingPropertiesName(clothingProperties.getClothingPropertiesName());
        clothing.setOptionalValue(clothingProperties.getOptionalValue());
        boolean flag = clothingPropertiesService.update(clothing,new UpdateWrapper<>(new ClothingProperties().setId(clothingProperties.getId())));
        if (!flag) {
            log.debug("修改失败");
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_UPDATE_FILED,"服装类属性信息修改失败");
        }
        log.debug("修改成功");
        return new Response<>();
    }





    @PostMapping("addClothingProperties")
    @ApiOperation("添加服装类型属性信息")
    public Response<ClothingProperties> addClothingProperties(@RequestBody ClothingProperties clothingProperties){
        try {
            boolean flag = clothingPropertiesService.save(clothingProperties);
            if (!flag) {
                log.debug("添加失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加服装类商品属性失败");
            }
            log.debug("添加成功");
            return new Response<>();
        } catch (Exception e) {
            log.debug("添加失败",e.getMessage());
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加服装类商品属性失败");
        }
    }



    @GetMapping("deleteClothingPropertiesById")
    @ApiOperation("根据服装属性类型id删除服装类型属性")
    public Response<ClothingProperties> deleteClothingPropertiesById(@ApiParam("服装类属性id") @RequestParam Integer id){
        try {
            if (ObjectUtils.isEmpty(id)){
                log.debug("删除错误");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"服装类属性id错误");
            }
            Boolean flag =  clothingPropertiesService.remove(new UpdateWrapper<ClothingProperties>().eq("id",id));
            if (!flag){
                log.debug("删除失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"服装类属性信息删除失败");
            }
            log.debug("删除成功");
            return new Response<>();
        } catch (BaseException e) {
            log.debug("删除失败",e.getMessage());
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"服装类属性信息删除失败");
        }
    }



}
