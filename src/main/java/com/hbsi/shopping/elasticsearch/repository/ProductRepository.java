package com.hbsi.shopping.elasticsearch.repository;

import com.hbsi.shopping.productinfo.entity.ProductInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductRepository extends ElasticsearchRepository<ProductInfo,Integer> {
}
