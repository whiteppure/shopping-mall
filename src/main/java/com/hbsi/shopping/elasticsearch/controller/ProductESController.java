package com.hbsi.shopping.elasticsearch.controller;

import com.hbsi.shopping.elasticsearch.repository.ProductRepository;
import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.hbsi.shopping.productinfo.service.IProductInfoService;
import com.hbsi.shopping.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/search-product/")
public class ProductESController {

    private final ProductRepository productRepository;

    private final IProductInfoService productInfoService;

    public ProductESController(ProductRepository productRepository, IProductInfoService productInfoService) {
        this.productRepository = productRepository;
        this.productInfoService = productInfoService;
    }


    @GetMapping("addIndexForProduct")
    public Response<?> addIndexForProduct(){
        try {
            List<ProductInfo> list = productInfoService.list();
            list.forEach(productRepository::index);
            System.out.println(list);
            return new Response<>();
        } catch (Exception e) {
            throw new RuntimeException("为商品添加索引失败");
        }
    }






}
