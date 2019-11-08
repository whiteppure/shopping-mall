package com.hbsi.shopping.producttype.controller;


import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

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
import com.hbsi.shopping.producttype.entity.ProductType;
import com.hbsi.shopping.producttype.service.IProductTypeService;
import com.hbsi.shopping.utils.DateUtils;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

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
@RequestMapping("/productType/")
@Api(tags="商品类型模块")
public class ProductTypeController {


	private final IProductTypeService productTypeService;

	public ProductTypeController(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}


	@GetMapping("getAllTypesByPage")
	@ApiOperation("分页查询商品类型")
	public Response<IPage<ProductType>> getAllTypesByPage(
			@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size
			){
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"current参数错误,添加失败");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"size参数错误,添加失败");
			}
			Page<ProductType> page = new Page<ProductType>(current,size);
			IPage<ProductType> typeList = productTypeService.page(page,new QueryWrapper<ProductType>());
			log.debug("查询成功");
			return new Response<>(typeList);
		} catch (Exception e) {
			log.debug("查询失败",e.getMessage());
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"size参数错误,添加失败");
		}
	}
	
	
	
	@GetMapping("getAllTypesName")
	@ApiOperation("不分页查询所有商品类型名称")
	public Response<List<ProductType>> getAllTypesName(){
		try {
			List<ProductType> allTypes = productTypeService.getAllTypes();
			log.debug("查询成功");
			return new Response<>(allTypes);
		} catch (Exception e) {
			log.debug("查询失败",e.getMessage());
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"不分页查询所有商品类型名称失败");
		}
	}


	@GetMapping("getAllTypes")
	@ApiOperation("不分页查询所有商品类型信息")
	public Response<List<ProductType>> getAllTypes(){
		try {
			List<ProductType> allTypes = productTypeService.list();
			log.debug("查询成功");
			return new Response<>(allTypes);
		} catch (Exception e) {
			log.debug("查询失败",e.getMessage());
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"不分页查询所有商品类型信息失败");
		}
	}

	
	
	@ApiOperation("添加商品类型")
	@PostMapping("addType")
	public Response<ProductType> addType(@RequestBody ProductType productType) {
		try {
			boolean flag = productTypeService.save(productType.setCreateTime(DateUtils.formatDate(new Date())));
			if (!flag) {
				log.debug("添加失败");
				throw new BaseException(ExceptionEnum.PRODUCT_TYPE_ADD_FILED);
			}
			log.debug("添加成功");
			return new Response<>();
			
		} catch (Exception e) {
			log.debug("添加失败",e.getMessage());
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_ADD_FILED);
		}
	}
	
	
	@ApiOperation("修改商品类型")
	@PostMapping("updType")
	public Response<ProductType> updType(@RequestBody ProductType productType) {
		try {
			ProductType type = new ProductType();
			type.setTypeName(productType.getTypeName());
			type.setTypePropertiesCount(productType.getTypePropertiesCount());
			type.setCreateTime(DateUtils.formatDate(new Date()));
			boolean flag = productTypeService.update(type,new UpdateWrapper<>(new ProductType().setId(productType.getId())));
			if (!flag) {
				log.debug("修改失败");
				throw new BaseException(ExceptionEnum.PRODUCT_TYPE_UPDATE_FILED);
			}
			log.debug("修改成功");
			return new Response<>();
		} catch (Exception e) {
			log.debug("修改失败",e.getMessage());
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_UPDATE_FILED);
		}
	}
	
	
	
}
