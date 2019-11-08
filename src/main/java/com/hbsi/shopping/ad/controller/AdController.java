package com.hbsi.shopping.ad.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.ad.entity.Ad;
import com.hbsi.shopping.ad.service.IAdService;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@RestController
@RequestMapping("/ad/")
@Api(tags = "广告模块")
public class AdController {

    private final IAdService adService;

    public AdController(IAdService adService) {
        this.adService = adService;
    }


    @GetMapping("getAllAdByPage")
    @ApiOperation("分页查询广告")
    public Response<IPage<Ad>> getAllAdByPage(
            @ApiParam("当前页") @RequestParam Integer current,
            @ApiParam("每页数量") @RequestParam Integer size
    ){
        try {
            if (ObjectUtils.isEmpty(current)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.AD_GET_FILED,"current参数错误");
            }
            if (ObjectUtils.isEmpty(size)){
                log.debug("查询错误");
                throw new BaseException(ExceptionEnum.AD_GET_FILED,"size参数失败");
            }
            Page<Ad> page = new Page<>(current,size);
            IPage<Ad> addAd = adService.page(page, new QueryWrapper<Ad>());
            log.debug("分页查询成功");
            return  new Response<>(addAd);
        } catch (BaseException e) {
            log.debug(e.getMessage(),"广告查询失败");
            throw new BaseException(ExceptionEnum.AD_GET_FILED);
        }
    }



    @GetMapping("showAd")
    @ApiOperation("根据广告id展示广告")
    public Response<Ad>  showAd(@ApiParam @RequestParam Integer id){

        if (ObjectUtils.isEmpty(id)){
            log.debug("修改失败");
            throw new BaseException(ExceptionEnum.AD_UPDATE_FILED,"id参数错误");
        }
        boolean flag = adService.update(new Ad().setAdStatus(0), new UpdateWrapper<>(new Ad().setId(id)));
        if (!flag){
            log.debug("修改失败");
            throw new BaseException(ExceptionEnum.AD_UPDATE_FILED);
        }
        log.debug("修改成功");
        return new Response<>();
    }



    @GetMapping("notShowAd")
    @ApiOperation("根据广告id不展示广告")
    public Response<Ad>  notShowAd(@ApiParam @RequestParam Integer id){

        if (ObjectUtils.isEmpty(id)){
            log.debug("修改失败");
            throw new BaseException(ExceptionEnum.AD_UPDATE_FILED,"id参数错误");
        }
        boolean flag = adService.update(new Ad().setAdStatus(-1), new UpdateWrapper<>(new Ad().setId(id)));
        if (!flag){
            log.debug("修改失败");
            throw new BaseException(ExceptionEnum.AD_UPDATE_FILED);
        }
        log.debug("修改成功");
        return new Response<>();
    }



    @GetMapping("addAd")
    @ApiOperation("添加广告")
    public Response<Ad> addAd(@RequestBody Ad ad){
        try {
            if(!adService.save(ad)){
                log.debug("修改失败");
                throw new BaseException(ExceptionEnum.AD_ADD_FILED);
            }
            log.debug("添加广告成功");
            return  new Response<>();
        } catch (BaseException e) {
            log.debug("修改失败",e.getMessage());
            throw new BaseException(ExceptionEnum.AD_ADD_FILED);
        }
    }





    @GetMapping("updById")
    @ApiOperation("根据id修改广告")
    public Response<Ad> updById(@RequestBody Ad ad){
        try {
            Ad  a = new Ad();
            a.setAdLocation(ad.getAdLocation());
            a.setAdName(ad.getAdName());
            boolean flag = adService.update(a, new UpdateWrapper<>(new Ad().setId(ad.getId())));
            if(!flag){
                log.debug("修改失败");
                throw new BaseException(ExceptionEnum.AD_UPDATE_FILED);
            }
            log.debug("修改广告成功");
            return  new Response<>();
        } catch (BaseException e) {
            log.debug("修改失败",e.getMessage());
            throw new BaseException(ExceptionEnum.AD_UPDATE_FILED);
        }
    }




    @ApiOperation("根据id删除id")
    @GetMapping("deleteAdById")
    public Response<Ad> deleteAdById(@ApiParam("广告id") @RequestParam Integer id){
        try {
            if (ObjectUtils.isEmpty(id)){
                log.debug("删除错误");
                throw new BaseException(ExceptionEnum.AD_DELETE_FILED,"广告id为空");
            }
            boolean flag = adService.remove(new UpdateWrapper<Ad>().eq("id", id));
            if(!flag){
                log.debug("删除失败");
                throw new BaseException(ExceptionEnum.AD_DELETE_FILED);
            }
            log.debug("删除广告成功");
            return  new Response<>();
        } catch (BaseException e) {
            log.debug("删除失败",e.getMessage());
            throw new BaseException(ExceptionEnum.AD_DELETE_FILED);
        }
    }




}
