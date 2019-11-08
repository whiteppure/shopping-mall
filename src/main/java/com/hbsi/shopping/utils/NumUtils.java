package com.hbsi.shopping.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.*;


public class NumUtils {
	
	//商品编号
	public  static String createProductNum() {
		return "PNo"+Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))*1000;
	}
	
	
	//店铺编号
	public  static String createShopNum() {
		return "SNo"+(System.currentTimeMillis()+"").substring(1)+  (System.nanoTime()+"").substring(7,10);  
	}
	
	
	//订单编号
	public static String createOrderNum() {
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return "RNo"+time + String.format("%011d", hashCodeV);
    }

    //随机生成  任意位数短信验证码
    public static Integer createPhoneCode(Integer length){
		Random random = new Random();
		return parseInt(IntStream.range(0, length).mapToObj(i -> String.valueOf(random.nextInt(10))).collect(Collectors.joining()));
	}
	
	
	public static void main(String[] args) {
//		System.out.println(createProductNum());
		System.out.println(createPhoneCode(4));
//		System.out.println(createShopNum());
	}
	
}
