package com.hbsi.shopping.exception;

public enum ExceptionEnum {

	SUCCESS("200", "成功"),

	/**
	 * 用户信息模块
	 */
	USERNAME_IS_EMPTY("000300", "用户名为空"),
	PASSWORD_IS_EMPTY("000301", "密码为空"), 
	USERNAME_OR_PASSWORD_FILED("000303", "用户名或密码错误"),
	CHECK_CODE_GET__FILED("000304", "重新获取验证码失败"),
	USER_ALREADY_LOGIN("000305","该用户已经登录"),
	VERIFY_CODE_ERROR("000306","该用户的验证码输入错误"),
	GET_USER_FILED("000307","查询用户失败"),
	UPDATE_USER_FILED("000308","修改用户失败"),
	USER_REGISTER_FILED("000309", "用户注册失败"),
	USERNAME_IS_BE_REGISTER("000310", "用户名已被注册"),
	USER_UPDATE_INFO_FILED("000311", "用户修改信息失败"),
	USER_ROLE_POWER_GET_FILDE("000312","查询用户信息失败"),
	USER_ADDRESS_GET_FILDE("000313","查询用户收货地址信息失败"),
	USER_SHOP_GET_FILED("000314","查询用户店铺失败"),
	USER_IS_LOGIN_OUT("000315","用户未登录"),
	USER_PARAM_IS_EMPTY("000316","参数为空"),
	/**
	 * 评论模块
	 */
	COMMENT_GET_FILED("000400","查询用户评论失败"),
	COMMENT_DELETE_FILED("000401","删除用户评论失败"),
	COMMENT_ADD_FILED("000402","添加用户评论失败"),
	COMMENT_REPLY_GET_FILED("000403","用户回复评论查询失败"),
	COMMENT_REPLY_ADD_FILED("000404","用户回复评论添加失败"),
	COMMENT_REPLY_DELETE_FILED("000405","用户回复评论删除失败"),
	/**
	 * 用户评价模块
	 */
	EVALUATE_GET_FILED("000500","查询用户评价失败"),
	EVALUATE_DELETE_FILED("000501","删除用户评价失败"),
	EVALUATE_ADD_FILED("000502","添加用户评价失败"),
	/**
	 * 用户收货地址模块
	 */
	ADDRESS_GET_FILED("000600","查询用户的收货地址失败"),
	ADDRESS_DELETE_FILED("000601","删除用户的收货地址失败"),
	ADDRESS_ADD_FILED("000602","添加用户的收货地址失败"),
	ADDRESS_UPDATE_FILED("000603","修改用户的收货地址失败"),
	ADDRESS_IS_EMPTY("000604","用户收货地址为空"),
	/**
	 * 用户权限模块
	 */
	POWER_GET_FILED("000700","查询用户权限失败"),
	POWER_DELETE_FILED("000701","删除用户权限失败"),
	POWER_ADD_FILED("000702","添加用户权限失败"),
	POWER_UPDATE_FILED("000703","修改用户权限失败"),
	/**
	 * 用户角色模块
	 */
	ROLE_GET_FILED("000800","查询用户角色失败"),
	ROLE_DELETE_FILED("000801","删除用户角色失败"),
	ROLE_ADD_FILED("000802","添加用户角色失败"),
	ROLE_UPDATE_FILED("000803","修改角色失败"),
	/**
	 * 商品购物车模块
	 */
	CART_PRODUCT_GET_FILED("000900","查询用户添加到购物车中的商品失败"),
	CART_PRODUCT_ADD_FILED("000901","用户添加到购物车中的商品添加失败"),
	CART_PRODUCT_DELETE_FILED("000902","用户删除购物车中的商品删除失败"),
	CART_PRODUCT_UPDATE_FILED("000903","用户修改购物车中的商品修改失败"),
	/**
	 * 商品信息模块
	 */
	PRODUCT_INFO_GET_FILED("001000","查询商品信息失败"),
	PRODUCT_INFO_ADD_FILED("001001","添加商品信息失败"),
	PRODUCT_INFO_UPDATE_FILED("001002","修改商品信息失败"),
	PRODUCT_INFO_DELETE_FILED("001003","删除商品信息失败"),
	PRODUCT_INFO_CREATE_CODE_FILED("001004","创建商品二维码失败"),
	PRODUCT_INFO_UPLOAD_IMG_FILED("001005","保存用户的图片失败"),
	PRODUCT_INFO_BUY_PRODUCT_FILED("001006","购买商品失败"),
	PRODUCT_INFO_GET_PRODUCT_BY_KEY_WORD_FILED("001007","根据关键字查询商品失败"),
	PRODUCT_INFO_IS_EMPTY("001008","查询商品类型信息为空或不存在"),
	/**
	 * 店铺模块
	 */
	SHOP_GET_FILED("001100","查询店铺信息失败"),
	SHOP_ADD_FILED("001101","添加店铺失败"),
	SHOP_DELETE_FILED("001102","删除店铺失败"),
	SHOP_UPDATE_FILED("001103","修改店铺失败"),
	/**
	 * 商品类型模块
	 */
	PRODUCT_TYPE_GET_FILED("001200","查询商品类型失败"),
	PRODUCT_TYPE_UPDATE_FILED("001201","修改商品类型失败"),
	PRODUCT_TYPE_ADD_FILED("001202","添加商品类型失败"),
	PRODUCT_TYPE_DELETE_FILED("001203","删除商品类型失败"),
	/**
	 * 已售商品模块
	 */
	PRODUCT_SELL_GET_FILED("001300","查询已售商品失败"),
	PRODUCT_SELL_ADD_FILED("001301","添加已售商品失败"),
	/**
	 * 商品类型属性模块
	 */
	PRODUCT_TYPE_PROPERTIES_GET_FILED("001400","查询商品属性失败"),
	PRODUCT_TYPE_PROPERTIES_UPDATE_FILED("001401","修改商品属性失败"),
	PRODUCT_TYPE_PROPERTIES_ADD_FILED("001402","添加商品属性失败"),
	PRODUCT_TYPE_PROPERTIES_DELETE_FILED("001403","删除商品属性失败"),
	/**
	 * 商品订单信息模块
	 */
	ORDER_INFO_GET_FILED("001500","查询商品订单信息失败"),
	ORDER_INFO_UPDATE_FILED("001501","修改商品订单信息失败"),
	ORDER_INFO_DELETE_FILED("001502","删除商品订单信息失败"),
	ORDER_INFO_ADD_FILED("001503","添加商品订单信息失败"),
	ORDER_INFO_GET_PRICE_FILED("001504","获取商品价格失败"),
	ORDER_PRODUCT_ADD_FILED("001505","创建购物车中的商品订单失败"),
	ORDER_PRODUCT_GET_EMPTY("001506","获取订单信息为空"),
	ORDER_PRODUCT_GET_FILED("001507","获取商品订单信息失败"),
	ORDER_ADDRESS_PRODUCT_GET_FILED("001508","查询用户订单信息失败"),
	/**
	 * 支付订单模块
	 */
	PAY_ORDER_FILED("001600","支付订单失败"),
	PAY_ORDER_REPEAT("001601","订单重复支付"),
	/**
	 * 退货订单模块
	 */
	RETURN_ORDER_GET_FILED("001700","查询退货订单失败"),
	RETURN_ORDER_UPDATE_FILED("001701","修改退货订单失败"),
	RETURN_ORDER_ADD_FILED("001702","退货失败"),
	RETURN_ORDER_DELETE_FILED("001703","删除退货订单失败"),
	RETURN_ORDER_GET_EMPTY("001703","退货订单为空"),
	/**
	 * 发货订单模块
	 */
	SEND_ORDER_GET_FILED("001800","查询发货订单失败"),
	SEND_ORDER_UPDATE_FILED("001801","修改发货订单失败"),
	SEND_ORDER_ADD_FILED("001802","添加发货订单失败"),
	/**
	 * 广告模块
	 */
	AD_GET_FILED("001900","广告查询失败"),
	AD_DELETE_FILED("001901","广告删除失败"),
	AD_UPDATE_FILED("001902","广告修改失败"),
	AD_ADD_FILED("001903","添加广告失败"),

	

	;
	private String status;
	private String message;

	ExceptionEnum(String status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
