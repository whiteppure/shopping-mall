package com.hbsi.shopping.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.hbsi.shopping.exception.BaseException;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * controller 增强器
 * 
 * @author while
 * @data 下午8:58:55
 * @description TODO
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionControllerAdvice {

	/**
	 * 全局异常捕捉处理
	 * 
	 * @author while
	 * @data 下午8:59:16
	 * @param @param e
	 * @param @return
	 * @description TODO
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView myErrorHandler(Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		if (e instanceof BaseException) {// 如果该异常属于自定义异常
			log.error(e.getMessage());
			BaseException be = (BaseException) e;
			modelAndView.setViewName("redirect:/errorPage/500");
			modelAndView.addObject("code", be.getCode());
			modelAndView.addObject("msg", be.getMsg());
			return modelAndView;
		} else {
			log.error("[系统异常] {}", e);
			modelAndView.setViewName("redirect:/errorPage/error");
			return modelAndView;
		}

	}
}
