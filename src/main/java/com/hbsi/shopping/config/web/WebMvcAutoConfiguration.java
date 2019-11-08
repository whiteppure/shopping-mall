package com.hbsi.shopping.config.web;


import com.hbsi.shopping.intercepter.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;


import lombok.extern.slf4j.Slf4j;


/**
 * webMvc 配置
 *
 * @author while
 */
@Slf4j
@Configuration
@EnableWebMvc
public class WebMvcAutoConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("static/**")
                .addResourceLocations("classpath:static/");
        registry.addResourceHandler("templates/**")
                .addResourceLocations("classpath:templates/**");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");


        //放行图片
        registry.addResourceHandler("/static/image/productCode/**")
                .addResourceLocations("classpath:/static/image/productCode/",
                        "file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image\\productCode\\");
        registry.addResourceHandler("/static/image/productImage/**")
                .addResourceLocations("classpath:/static/image/productImage/",
                        "file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image\\productImage\\");


        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //error
        registry.addViewController("/error/404").setViewName("/error/404.html");
        registry.addViewController("/error/500").setViewName("/error/500.html");
        registry.addViewController("/error/error").setViewName("/error/error.html");

        //shopping 演示
        registry.addViewController("/demo/test").setViewName("/test.html");
        registry.addViewController("/demo/index").setViewName("/index.html");
        registry.addViewController("/demo").setViewName("/index.html");
        registry.addViewController("/login").setViewName("/login.html");
        registry.addViewController("/shopping-mall/login").setViewName("/login.html");
        registry.addViewController("/demo/pay").setViewName("/pay.html");
        registry.addViewController("/demo/order").setViewName("/order.html");
        registry.addViewController("/demo/order-submit").setViewName("/order-submit.html");
        registry.addViewController("/demo/cart").setViewName("/cart.html");
        registry.addViewController("/register").setViewName("/register.html");
        registry.addViewController("/shopping-mall/register").setViewName("/register.html");

        //shopping 首页
        registry.addViewController("/").setViewName("/shopping/home.html");
        registry.addViewController("/shopping-mall").setViewName("/shopping/home.html");
        registry.addViewController("/shopping-mall/home").setViewName("/shopping/home.html");
        registry.addViewController("/shopping-mall/product").setViewName("/shopping/product.html");
        registry.addViewController("/shopping-mall/cart-product").setViewName("/shopping/cart-product.html");
        registry.addViewController("/shopping-mall/pay-order").setViewName("/shopping/pay-order.html");
        registry.addViewController("/shopping-mall/pay-way").setViewName("/shopping/pay-money-way.html");
        registry.addViewController("/shopping-mall/personal").setViewName("/shopping/personal.html");
        registry.addViewController("/shopping-mall/search").setViewName("/shopping/search.html");

        //商品分类
        registry.addViewController("/shopping-mall/type/elect").setViewName("/type/elect-type.html");
        registry.addViewController("/shopping-mall/type/life").setViewName("/type/life-type.html");
        registry.addViewController("/shopping-mall/type/book").setViewName("/type/book-type.html");
        registry.addViewController("/shopping-mall/type/clothes").setViewName("/type/clothes-type.html");
        registry.addViewController("/shopping-mall/type/music").setViewName("/type/music-type.html");
        registry.addViewController("/shopping-mall/type/pet").setViewName("/type/pet-type.html");



        //shopping 个人中心
        registry.addViewController("/shop/my-home").setViewName("/shop/shop-home.html");
        registry.addViewController("/shop/my-goods").setViewName("/shop/my-goods.html");
        registry.addViewController("/shop/my-order").setViewName("/shop/my-order.html");
        registry.addViewController("/shop/my-address").setViewName("/shop/my-address.html");
        registry.addViewController("/shop/my-cart").setViewName("/shop/my-cart.html");
        registry.addViewController("/shop/add-goods").setViewName("/shop/add-goods.html");
        registry.addViewController("/shop/update-goods").setViewName("/shop/update-goods.html");
        registry.addViewController("/shop/shop-info").setViewName("/shop/shop-info.html");
        registry.addViewController("/shop/order-info").setViewName("/shop/order-info.html");
        registry.addViewController("/shop/my-info").setViewName("/shop/my-info.html");


        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }



    /*
    注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/shopping-mall/login","/","/login","/shopping-mall/home","/shopping-mall","/shopping-mall/product",
                        "/static/**","/shopping-mall/search","/images/**","/demo/**",
                        "/user/**","/register","/error/**","/shopping-mall/register",
                        "/productInfo/getAllProductByTypeId","/productInfo/getProducts","/productInfo/getTypeProductsByPage","/productInfo/getAllInfoById/**",
                        "/shopping-mall/type/**","/productInfo/getAllProductByTypeIdNotPage","/productInfo/randomNumByCount","/productInfo/productIsExistById"
                );
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
