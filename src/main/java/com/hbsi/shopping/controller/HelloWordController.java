package com.hbsi.shopping.controller;

import com.hbsi.shopping.utils.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Api(tags = "测试用的controller")
public class HelloWordController {
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@GetMapping("/helloWord")
	@ResponseBody
	public String demo() {
		return "test";
	}


	@PostMapping("uploadFileTest")
	@ApiOperation("上传文件测试")
	@ResponseBody
	public String uploadFileTest(@ApiParam("上传文件") MultipartFile file){

		String s = ImageUtils.upLoadFile(file, "123test");
		return "上传成功";
	}


	
}
