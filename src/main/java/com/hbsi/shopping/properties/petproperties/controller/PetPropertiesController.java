package com.hbsi.shopping.properties.petproperties.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.properties.petproperties.entity.PetProperties;
import com.hbsi.shopping.properties.petproperties.service.IPetPropertiesService;
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
@RequestMapping("/petProperties/")
@Api(tags = "宠物属性信息模块")
public class PetPropertiesController {

    @Autowired
    private IPetPropertiesService petPropertiesService;


    @GetMapping("getAllPetpertiesByPage")
    @ApiOperation("分页查询所有宠物商品类属性信息")
    public Response<IPage<PetProperties>> getAllPetpertiesByPage(
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
            Page<PetProperties> page = new Page<>();
            IPage<PetProperties> PetProperties = petPropertiesService.page(page,new QueryWrapper<>());
            log.debug("查询成功");
            return new Response<>(PetProperties);
        } catch (Exception e) {
            log.debug("查询失败",e.getMessage());
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"分页查询宠物商品类属性失败");
        }
    }



    @ApiOperation("不分查询全部宠物商品类属性信息")
    @GetMapping("getPetProperties")
    public Response<List<PetProperties>> getPetProperties(){
        try {
            List<PetProperties> PetPropertiesList = petPropertiesService.list();
            log.debug("查询成功");
            return new Response<>(PetPropertiesList);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("查询失败",e.getMessage());
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"不分页查询宠物商品类型失败");
        }
    }



    @ApiOperation("根据id修改宠物商品类属性信息")
    @PostMapping("updPetProperties")
    public Response<PetProperties> updPetProperties(@RequestBody PetProperties petProperties){
        PetProperties Pet = new PetProperties();
        Pet.setPetPropertiesName(petProperties.getPetPropertiesName());
        Pet.setOptionalValue(petProperties.getOptionalValue());
        boolean flag = petPropertiesService.update(Pet,new UpdateWrapper<>(new PetProperties().setId(petProperties.getId())));
        if (!flag) {
            log.debug("修改失败");
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_UPDATE_FILED,"宠物商品类属性信息修改失败");
        }
        log.debug("修改成功");
        return new Response<>();
    }





    @PostMapping("addPetProperties")
    @ApiOperation("添加宠物商品类型属性信息")
    public Response<PetProperties> addPetProperties(@RequestBody PetProperties petProperties){
        try {
            boolean flag = petPropertiesService.save(petProperties);
            if (!flag) {
                log.debug("添加失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加宠物商品类商品属性失败");
            }
            log.debug("添加成功");
            return new Response<>();
        } catch (Exception e) {
            log.debug("添加失败",e.getMessage());
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加宠物商品类商品属性失败");
        }
    }



    @GetMapping("deletePetPropertiesById")
    @ApiOperation("根据宠物商品属性类型id删除宠物商品类型属性")
    public Response<PetProperties> deletePetPropertiesById(@ApiParam("宠物商品类属性id") @RequestParam Integer id){
        try {
            if (ObjectUtils.isEmpty(id)){
                log.debug("删除错误");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"宠物商品类属性id错误");
            }
            Boolean flag =  petPropertiesService.remove(new UpdateWrapper<PetProperties>().eq("id",id));
            if (!flag){
                log.debug("删除失败");
                throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"宠物商品类属性信息删除失败");
            }
            log.debug("删除成功");
            return new Response<>();
        } catch (BaseException e) {
            log.debug("删除失败",e.getMessage());
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"宠物商品类属性信息删除失败");
        }
    }
}
