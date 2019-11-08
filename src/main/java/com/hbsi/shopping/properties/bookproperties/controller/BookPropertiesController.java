package com.hbsi.shopping.properties.bookproperties.controller;


import java.awt.print.Book;
import java.util.List;

import javax.management.Query;

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
import com.hbsi.shopping.properties.bookproperties.entity.BookProperties;
import com.hbsi.shopping.properties.bookproperties.service.IBookPropertiesService;
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
 * @since 2019-06-30
 */
@Slf4j
@RestController
@RequestMapping("/bookProperties/")
@Api(tags="书籍属性类信息模块")
public class BookPropertiesController {
	
	@Autowired
	private IBookPropertiesService bookPropertiesService;
	
	
	@GetMapping("getAllBookpertiesByPage")
	@ApiOperation("分页查询所有书籍类属性信息")
	public Response<IPage<BookProperties>> getAllBookpertiesByPage(
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
			Page<BookProperties> page = new Page<>();
			IPage<BookProperties> bookProperties = bookPropertiesService.page(page,new QueryWrapper<BookProperties>());
			log.debug("查询成功");
			return new Response<>(bookProperties);
		} catch (Exception e) {
			log.debug("查询失败",e.getMessage());
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"分页查询书籍类属性失败");
		}
	}
	
	
	
	@ApiOperation("不分查询全部书籍类属性信息")
	@GetMapping("getBookProperties")
	public Response<List<BookProperties>> getBookProperties(){
		try {
			List<BookProperties> bookPropertiesList = bookPropertiesService.list();
			log.debug("查询成功");
			return new Response<>(bookPropertiesList);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("查询失败",e.getMessage());
			throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_GET_FILED,"不分页查询书籍类型失败");
		}
	}
	
	
	
	@ApiOperation("根据id修改书籍类属性信息")
	@PostMapping("updBookProperties")
	public Response<BookProperties> updBookProperties(@RequestBody BookProperties bookProperties){
		BookProperties book = new BookProperties();
		book.setBookPropertiesName(bookProperties.getBookPropertiesName());
		book.setOptionalValue(bookProperties.getOptionalValue());
		boolean flag = bookPropertiesService.update(book,new UpdateWrapper<BookProperties>(new BookProperties().setId(bookProperties.getId())));
		if (!flag) {
			log.debug("修改失败");
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_UPDATE_FILED,"书籍类属性信息修改失败");
		}
		log.debug("修改成功");
		return new Response<>();
	}
	
	
	
	
	
	@PostMapping("addBookProperties")
	@ApiOperation("添加书籍类型属性信息")
	public Response<BookProperties> addBookProperties(@RequestBody BookProperties bookProperties){
		try {
			boolean flag = bookPropertiesService.save(bookProperties);
			if (!flag) {
				log.debug("添加失败");
				throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加书籍类商品属性失败");
			}
			log.debug("添加成功");
			return new Response<>();
		} catch (Exception e) {
			log.debug("添加失败",e.getMessage());
			throw new BaseException(ExceptionEnum.PRODUCT_TYPE_PROPERTIES_ADD_FILED,"添加书籍类商品属性失败");
		}
	}



	@GetMapping("deleteBookPropertiesById")
	@ApiOperation("根据书籍属性类型id删除书籍类型属性")
	public Response<BookProperties> deleteBookPropertiesById(@ApiParam("书籍类型属性id") @RequestParam Integer id){
		try {
			if (ObjectUtils.isEmpty(id)){
				log.debug("删除错误");
				throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"书籍类属性id错误");
			}
			Boolean flag =  bookPropertiesService.remove(new UpdateWrapper<BookProperties>().eq("id",id));
			if (!flag){
				log.debug("删除失败");
				throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"书籍类属性信息删除失败");
			}
			log.debug("删除成功");
			return new Response<>();
		} catch (BaseException e) {
			log.debug("删除错误",e.getMessage());
			throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED,"书籍类属性信息删除失败");
		}

	}
	
	
	
	
}
