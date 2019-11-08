package com.hbsi.shopping.producttype.mapper;

import com.hbsi.shopping.producttype.entity.ProductType;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {
	

	@Select("select typeName from product_type")
	List<ProductType> getAllTypes();
	
}
