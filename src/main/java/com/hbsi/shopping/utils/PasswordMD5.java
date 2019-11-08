package com.hbsi.shopping.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordMD5 {
	/**
	 * MD5加密
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String passwordMD5(String password){
		MessageDigest md5 = null;//创建md5对象		
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}//获取MD5对象
		
		char[] charMD5 = password.toCharArray();//把传进来的字符串变成字符数组
		byte[] byteMD5 = new byte[charMD5.length];//定义字节数组
		
		for(int i=0; i<charMD5.length; i++) {//遍历字符数组
			byteMD5[i] = (byte) charMD5[i];//把字符转化为字节
		}
		byte[] md5Bytes = md5.digest(byteMD5);//digest进行摘要
		StringBuffer buffer = new StringBuffer();//StringBuffer??
		//遍历字节数组
		for(int i=0; i<md5Bytes.length; i++) {
			int value = (int)md5Bytes[i] & 0xff;//把每一个字节转化为16进制数
			if(value<16) {
				buffer.append("0");//再生成的字符串前边补0
			}
			String  str = Integer.toHexString(value);//	转化为16进制的数
			buffer.append(str);
		}
		return buffer.toString().toUpperCase();//小写字母转化大写
	}
}
