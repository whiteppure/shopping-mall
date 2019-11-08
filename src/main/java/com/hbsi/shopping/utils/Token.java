package com.hbsi.shopping.utils;



import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class Token {
	
	
	/**
	 * 创造token
	 * @author while
	 * @data 下午4:08:24
	 * @param  @return
	 * @description TODO
	 */
	public  static  String  createToken() {
		return  UUID.randomUUID().toString().replaceAll("-", "");
	}
	

	
	/**
	 * 根据map的键 取值
	 * @author while
	 * @data 下午4:27:43
	 * @param  @param map
	 * @param  @param key
	 * @param  @return
	 * @description TODO
	 */
	public static Object getTokenByKey(Map<String,String > map,String key) {
		if(map == null)
			return null;
		for(Entry<String, String> oldMap : map.entrySet()) {
			if(oldMap.getKey().equals(key)) {
				return oldMap.getValue();
			}
		}
		return null;
	}
	
	
}
