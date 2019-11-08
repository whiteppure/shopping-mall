package com.hbsi.shopping.properties.elecproperties.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.properties.elecproperties.entity.ElecProperties;
import com.hbsi.shopping.properties.elecproperties.service.IElecPropertiesService;
import com.hbsi.shopping.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/elecProperties/")
@Api(tags = "电子商品属性类信息模块")
public class ElecPropertiesController {

    @Autowired
    private IElecPropertiesService elecPropertiesService;


    @GetMapping("getAllElecpertiesByPage")
    @ApiOperation("分页查询所有电子商品类属性信息")
    public Response<IPage<ElecProperties>> getAllElecpertiesByPage(
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
            Page<ElecProperties> page = new Page<>();
            IPage<ElecProperties> ElecProperties = elecPropertiesService.page(page,new QueryWrapper<>());
            log.debug("查询成功");
            return new Response<>(ElecProperties);
        } catch (Exception e) {
            log.debug("查询失败",e.getMessage());
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"分页查询电子商品类属性失败");
        }
    }



    @ApiOperation("不分查询全部电子商品类属性信息")
    @GetMapping("getElecProperties")
    public Response<List<ElecProperties>> getElecProperties(){
        try {
            List<ElecProperties> ElecPropertiesList = elecPropertiesService.list();
            log.debug("查询成功");
            return new Response<>(ElecPropertiesList);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("查询失败",e.getMessage());
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"不分页查询电子商品类型失败");
        }
    }



    @ApiOperation("根据id修改电子商品类属性信息")
    @PostMapping("updElecProperties")
    public Response<ElecProperties> updElecProperties(@RequestBody ElecProperties elecProperties){
        ElecProperties elec = new ElecProperties();
        elec.setElecPropertiesName(elecProperties.getElecPropertiesName());
        elec.setOptionalValue(elecProperties.getOptionalValue());
        boolean flag = elecPropertiesService.update(elec,new UpdateWrapper<>(new ElecProperties().setId(elecProperties.getId())));
        if (!flag) {
            log.debug("修改失败");
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_UPDATE_FILED,"电子商品类属性信息修改失败");
        }
        log.debug("修改成功");
        return new Response<>();
    }





    @PostMapping("addElecProperties")
    @ApiOperation("添加电子商品类型属性信息")
    public Response<ElecProperties> addElecProperties(@RequestBody ElecProperties elecProperties){
        try {
            boolean flag = elecPropertiesService.save(elecProperties);
            if (!flag) {
                log.debug("添加失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加电子商品类商品属性失败");
            }
            log.debug("添加成功");
            return new Response<>();
        } catch (Exception e) {
            log.debug("添加失败",e.getMessage());
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加电子商品类商品属性失败");
        }
    }



    @GetMapping("deleteElecPropertiesById")
    @ApiOperation("根据电子商品属性类型id删除电子商品类型属性")
    public Response<ElecProperties> deleteElecPropertiesById(@ApiParam("电子商品类属性id") @RequestParam Integer id){
        try {
            if (ObjectUtils.isEmpty(id)){
                log.debug("删除错误");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"电子商品类属性id错误");
            }
            Boolean flag =  elecPropertiesService.remove(new UpdateWrapper<ElecProperties>().eq("id",id));
            if (!flag){
                log.debug("删除失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"电子商品类属性信息删除失败");
            }
            log.debug("删除成功");
            return new Response<>();
        } catch (BaseException e) {
            log.debug("删除失败",e.getMessage());
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"电子商品类属性信息删除失败");
        }
    }

    
}
