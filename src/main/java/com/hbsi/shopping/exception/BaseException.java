package com.hbsi.shopping.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException{
	
	private ExceptionEnum e;
	
	private String msg;
	
	private String code;
	
	public BaseException(ExceptionEnum e) {
		super(e.getMessage());
		this.msg = e.getStatus();
	}
	
	public BaseException(ExceptionEnum e,String msg) {
		super(e.getMessage());
		this.msg = e.getStatus();
	}

}
