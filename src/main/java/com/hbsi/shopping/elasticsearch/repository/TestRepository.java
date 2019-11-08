package com.hbsi.shopping.elasticsearch.repository;

import com.hbsi.shopping.elasticsearch.entity.EsDemo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * ElasticsearchRepository<?,?>
 *
 * 第一个?: 写 Javabean
 * 第二个?: 写 该Javabean对应的主键类型
 */
@Component
public interface TestRepository extends ElasticsearchRepository<EsDemo,Integer> {


}
