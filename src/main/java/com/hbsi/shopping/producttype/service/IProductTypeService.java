package com.hbsi.shopping.producttype.service;

import com.hbsi.shopping.producttype.entity.ProductType;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
public interface IProductTypeService extends IService<ProductType> {
	List<ProductType> getAllTypes();
}
