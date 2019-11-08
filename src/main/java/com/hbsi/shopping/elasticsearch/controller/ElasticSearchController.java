package com.hbsi.shopping.elasticsearch.controller;

import com.hbsi.shopping.elasticsearch.entity.EsDemo;
import com.hbsi.shopping.elasticsearch.repository.TestRepository;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/elasticsearch/")
public class ElasticSearchController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("test")
    public Response<ProductInfo> test(){
        EsDemo test = new EsDemo(1, "张三", 20, "男");
        testRepository.index(test);
        return new Response<>();
    }


}
