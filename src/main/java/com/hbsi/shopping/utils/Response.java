package com.hbsi.shopping.utils;


import com.hbsi.shopping.exception.ExceptionEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Response<T> {
	 @ApiModelProperty(value="返回状态")
	private String status;
	 @ApiModelProperty(value="返回信息")
	private String message;
	 @ApiModelProperty(value="返回数据")
	private T data;

	public Response() {
		this.status = ExceptionEnum.SUCCESS.getStatus();
		this.message =ExceptionEnum.SUCCESS.getMessage();
	}

	public Response( T data) {
		this.status = ExceptionEnum.SUCCESS.getStatus();
		this.message =ExceptionEnum.SUCCESS.getMessage();
		this.data = data;
	}

	public Response(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
		if(!"200".equals(status))
		log.error(message);
	}
	public Response(String status, String message) {
		this.status = status;
		this.message = message;
		if(!"200".equals(status))
		log.error(message);
	}
	
}
