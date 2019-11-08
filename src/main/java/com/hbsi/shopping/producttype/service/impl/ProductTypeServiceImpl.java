package com.hbsi.shopping.producttype.service.impl;

import com.hbsi.shopping.producttype.entity.ProductType;
import com.hbsi.shopping.producttype.mapper.ProductTypeMapper;
import com.hbsi.shopping.producttype.service.IProductTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

	@Override
	public List<ProductType> getAllTypes() {
		return this.baseMapper.getAllTypes();
	}

}
