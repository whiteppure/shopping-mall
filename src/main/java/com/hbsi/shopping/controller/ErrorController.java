package com.hbsi.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/errorPage/")
@Api(tags="异常处理模块")
@Slf4j
public class ErrorController {
	
	@RequestMapping("404")
	public String pageNotFound() {
		log.debug("404错误");
		return "redirect:/error/404";
	}
	
	@RequestMapping("500")
	public String serverError() {
		log.debug("500错误");
		return "redirect:/error/500";
	}
}
