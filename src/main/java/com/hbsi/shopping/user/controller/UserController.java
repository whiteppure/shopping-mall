package com.hbsi.shopping.user.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.address.entity.UserAddress;
import com.hbsi.shopping.address.service.IUserAddressService;
import com.hbsi.shopping.cart.entity.Cart;
import com.hbsi.shopping.cart.service.ICartService;
import com.hbsi.shopping.controller.ImageCodeController;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.power.entity.UserPower;
import com.hbsi.shopping.power.service.IUserPowerService;
import com.hbsi.shopping.role.entity.UserRole;
import com.hbsi.shopping.role.service.IUserRoleService;
import com.hbsi.shopping.role.service.impl.UserRoleServiceImpl;
import com.hbsi.shopping.shop.entity.Shop;
import com.hbsi.shopping.shop.service.IShopService;
import com.hbsi.shopping.user.entity.User;
import com.hbsi.shopping.user.service.IUserService;
import com.hbsi.shopping.utils.DateUtils;
import com.hbsi.shopping.utils.NumUtils;
import com.hbsi.shopping.utils.PasswordMD5;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.update.Update;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-12
 */
@Slf4j
@Controller
@RequestMapping("/user/")
@Api(tags = "用户模块")
public class UserController {

	private final IUserService userService;

	private final IUserPowerService userPowerService;

	private final IUserRoleService userRoleService;

	private final IUserAddressService userAddressService;

	private final ICartService cartUser;

	private final IShopService shopService;

	private final ImageCodeController codeController;

	public UserController(IUserService userService, IUserPowerService userPowerService, IUserRoleService userRoleService, IUserAddressService userAddressService, ICartService cartUser, IShopService shopService, ImageCodeController codeController) {
		this.userService = userService;
		this.userPowerService = userPowerService;
		this.userRoleService = userRoleService;
		this.userAddressService = userAddressService;
		this.cartUser = cartUser;
		this.shopService = shopService;
		this.codeController = codeController;
	}

	@PostMapping("login")
	@ApiOperation("用户登录")
	@ResponseBody
	public Response<Object> login(
			@ApiParam("邮箱或手机号") @RequestParam String account,
			@ApiParam("密码") @RequestParam String password,
			@ApiParam("验证码") @RequestParam String code,
			HttpServletResponse response,
			HttpServletRequest request
	) {
		HttpSession session = request.getSession();
		if (ObjectUtils.isEmpty(account)) {
			log.error("邮箱或手机号为空");
			return new Response<>(ExceptionEnum.USERNAME_IS_EMPTY.getStatus(), "登录账号为空");
		}
		if (ObjectUtils.isEmpty(password)) {
			log.error("密码为空");
			return new Response<>(ExceptionEnum.PASSWORD_IS_EMPTY.getStatus(), "密码为空");
		}
		if (ObjectUtils.isEmpty(code)) {
			log.error("验证码为空");
			return new Response<>(ExceptionEnum.PASSWORD_IS_EMPTY.getStatus(), "验证码为空");
		}
		//如果session 里边的验证码为空
		if (session.getAttribute("imageCode") == null) {
			// 验证码为null 重新获取验证码
			try {
				codeController.getCode(response, request);
			} catch (Exception e) {
				log.debug("重新获取验证码失败", e.getMessage());
				throw new BaseException(ExceptionEnum.CHECK_CODE_GET__FILED);
			}
		} else {
			if (session.getAttribute("imageCode").toString().equalsIgnoreCase(code)) {
					//根据手机号查询用户
					User userByPhone = userService.getOne(new QueryWrapper<User>().eq("phone", account).eq("password", password).eq("userStatus", 1));
					//根据邮箱查询用户
					User userByEmail = userService.getOne(new QueryWrapper<User>().eq("email", account).eq("password", password).eq("userStatus", 1));
					//存放user对象信息
					User user = new User();

					if (userByPhone != null){
						user = userByPhone;
					}

					if (userByEmail != null){
						user = userByEmail;
					}
					if (userByPhone != null || userByEmail != null){
						log.debug(user.getUserName() + "登录成功");
	//					String token = PasswordMD5.passwordMD5(com.hbsi.shopping.utils.Token.createToken());
						// 将用户写进session
						session.setAttribute("user", user);
						log.debug("登录成功");
						return new Response<>(user);
				}else{
					log.debug("账号或密码错误");
					return new Response<>(ExceptionEnum.USERNAME_OR_PASSWORD_FILED.getStatus(), "用户名或密码错误");
				}
			} else {
				log.debug("验证码输入错误");
				return new Response<>(ExceptionEnum.VERIFY_CODE_ERROR.getStatus(), "验证码输入错误");
			}
		}
		log.debug("系统异常");
		throw new RuntimeException("系统异常");
	}



	/**
	 * 无状态查询所有用户
	 *
	 * @author while
	 * @data 下午5:53:52
	 * @param @param current
	 * @param @param size
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@GetMapping("getAllUserByPage")
	@ApiOperation("无状态查询所有用户")
	public Response<IPage<User>> getAllUserByPage(@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size) {
		// 分页对象
		IPage<User> userList = null;
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.GET_USER_FILED, "current 参数错误");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.GET_USER_FILED, "size参数错误");
			}
			Page<User> page = new Page<User>(current, size);
			userList = userService.page(page, new QueryWrapper<User>(new User()));
		} catch (Exception e) {
			log.debug("查询用户错误", e.getMessage());
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "无状态查询所有用户失败");
		}
		log.debug("分页查询成功");
		return new Response<IPage<User>>(userList);
	}





	/**
	 * 查询所有被禁用的用户
	 *
	 * @author while
	 * @data 下午7:35:26
	 * @param @param current
	 * @param @param size
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@GetMapping("getAllDisableUserByPage")
	@ApiOperation("查询所有被禁用的用户")
	public Response<IPage<User>> getAllDisableUserByPage(@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size) {
		// 分页对象
		IPage<User> userList = null;
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.GET_USER_FILED, "current 参数错误");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.GET_USER_FILED, "size参数错误");
			}
			Page<User> page = new Page<User>(current, size);
			userList = userService.page(page, new QueryWrapper<User>(new User()).eq("userStatus", 0));
		} catch (Exception e) {
			log.debug("查询用户错误", e.getMessage());
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "查询所有被禁用的用户");
		}
		log.debug("分页查询成功");
		return new Response<IPage<User>>(userList);
	}

	/**
	 * 查询所有正常的用户
	 *
	 * @author while
	 * @data 下午7:35:26
	 * @param @param current
	 * @param @param size
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@GetMapping("getAllNormalUserByPage")
	@ApiOperation("分页查询所有正常的用户")
	public Response<IPage<User>> getAllNormalUserByPage(@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size) {
		// 分页对象
		IPage<User> userList = null;
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.GET_USER_FILED, "current 参数错误");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("分页查询失败");
				throw new BaseException(ExceptionEnum.GET_USER_FILED, "size参数错误");
			}
			Page<User> page = new Page<User>(current, size);
			userList = userService.page(page, new QueryWrapper<User>(new User()).eq("userStatus", 1));
		} catch (Exception e) {
			log.debug("查询用户错误", e.getMessage());
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "查询所有正常的用户");
		}
		log.debug("分页查询成功");
		return new Response<IPage<User>>(userList);
	}

	/**
	 * 不分页查询所有用户
	 *
	 * @author while
	 * @data 下午6:47:26
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@GetMapping("getAllUser")
	@ApiOperation("不分页查询所有用户")
	public Response<List<User>> getAllUser() {
		try {
			List<User> userList = userService.list();
			log.debug("查询成功");
			return new Response<>(userList);
		} catch (Exception e) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "无状态查询所有用户失败");
		}
	}

	/**
	 * 根据用户id查询用户
	 *
	 * @author while
	 * @data 下午6:16:35
	 * @param @param id
	 * @param @return
	 * @description TODO
	 */
	@GetMapping("getUserById")
	@ApiOperation("根据用户id查询用户")
	@ResponseBody
	public Response<User> getUserById(@ApiParam("用户id") @RequestParam("id") Integer id) {
		if (ObjectUtils.isEmpty(id)) {
			log.debug("查询失败");
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "用户id错误");
		}
		try {
			User user = userService.getOne(new QueryWrapper<User>().eq("id", id));
			log.debug("查询成功");
			return new Response<>(user);
		} catch (Exception e) {
			log.debug("查询失败", e.getMessage());
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "根据id查询用户失败");
		}
	}

	/**
	 * 禁用用户
	 *
	 * @author while
	 * @data 下午6:36:13
	 * @param @param id
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@GetMapping("disableUserById")
	@ApiOperation("根据用户id禁用用户(一并禁用该用户的店铺)")
	public Response<Object> disableUserById(@ApiParam("用户id") @RequestParam Integer id) {
		if (ObjectUtils.isEmpty(id)) {
			log.debug("禁用用户失败");
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "用户id错误");
		}
		try {
			boolean userFlag = userService.update(new User().setUserStatus(0), new UpdateWrapper<User>(new User().setId(id)));
			if (!userFlag) {
				log.debug("禁用用户失败");
				throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "禁用用户失败");
			}
			boolean shopFlag = shopService.update(new Shop().setShopStatus(0),new UpdateWrapper<Shop>(new Shop().setUserId(id)));
			if (!shopFlag) {
				log.debug("禁用用户失败,店铺禁用失败");
				throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "禁用用户失败,店铺禁用失败");
			}
			log.debug("禁用成功");
			return new Response<Object>();
		} catch (Exception e) {
			log.debug("禁用用户失败", e.getMessage());
			throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "禁用用户失败");
		}
	}

	/**
	 * 启用用户
	 *
	 * @author while
	 * @data 下午6:36:13
	 * @param @param id
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@GetMapping("startUserById")
	@ApiOperation("根据用户id启用用户(一并启用该用户的店铺)")
	public Response<User> startUserById(@ApiParam("用户id") @RequestParam Integer id) {
		if (ObjectUtils.isEmpty(id)) {
			log.debug(" 启用用户失败");
			throw new BaseException(ExceptionEnum.GET_USER_FILED, "用户id错误");
		}
		try {
			boolean userFlag = userService.update(new User().setUserStatus(1), new UpdateWrapper<User>(new User().setId(id)));
			if (!userFlag) {
				log.debug(" 启用用户失败");
				throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, " 启用用户失败");
			}
			boolean shopFlag = shopService.update(new Shop().setShopStatus(1),new UpdateWrapper<Shop>(new Shop().setUserId(id)));
			if (!shopFlag) {
				log.debug(" 启用用户失败,店铺启用失败");
				throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, " 启用用户失败,店铺启用失败");
			}
			log.debug(" 启用用户成功");
			return new Response<User>();
		} catch (Exception e) {
			log.debug(" 启用用户失败", e.getMessage());
			throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "启用用户失败");
		}
	}

	/**
	 * 修改用户信息
	 *
	 * @author while
	 * @data 下午7:11:13
	 * @param @param user
	 * @param @return
	 * @description TODO
	 */
	@PostMapping("updUserById")
	@ApiOperation("用户修改个人信息")
	public Response<User> updUserById(@RequestBody User user) {
		try {
			User userData = new User();
			userData.setPhone(user.getPhone());
			userData.setUserName(user.getUserName());
			userData.setEmail(user.getEmail());
			userService.update(userData, new UpdateWrapper<User>(new User().setId(user.getId())));
			log.debug("修改成功");
			return new Response<>();
		} catch (Exception e) {
			log.debug("修改个人用户信息失败", e.getMessage());
			throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "修改个人用户信息失败");
		}
	}

	/**
	 * 用户修改密码
	 *
	 * @author while
	 * @data 下午7:30:09
	 * @param @param id
	 * @param @param oldPwd
	 * @param @param newPwd
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@PostMapping("updPwd")
	@ApiOperation("用户修改密码")
	public Response<User> updPwd(@ApiParam("用户id") @RequestParam Integer id,
			@ApiParam("旧密码") @RequestParam String oldPwd, @ApiParam("新密码") @RequestParam String newPwd) {
		try {
			if (ObjectUtils.isEmpty(id)) {
				log.debug("修改密码失败");
				throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "id参数错误");
			}
			if (ObjectUtils.isEmpty(oldPwd)) {
				log.debug("修改密码失败");
				throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "oldPwd参数错误");
			}
			if (ObjectUtils.isEmpty(newPwd)) {
				log.debug("修改密码失败");
				throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "newPwd参数错误");
			}

			User user = userService.getOne(new QueryWrapper<User>().eq("id", id));
			if (user == null) {
				throw new BaseException(ExceptionEnum.GET_USER_FILED, "查询该用户失败");
			}
			// 判断输入的 旧密码是否相同
			if (user.getPassword().equals(PasswordMD5.passwordMD5(oldPwd))) {
				// 根据id 修改密码 字段
				boolean flag = userService.update(new User().setPassword(PasswordMD5.passwordMD5(newPwd)),
						new UpdateWrapper<User>().eq("id", id));
				if (flag) {
					log.debug("修改密码成功");
					return new Response<>();
				} else {
					log.debug("修改密码失败");
					throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "修改密码失败");
				}
			} else {
				log.debug("修改密码失败");
				return new Response<>(ExceptionEnum.UPDATE_USER_FILED.getStatus(), "输入的旧密码错误");
			}
		} catch (Exception e) {
			log.debug("修改密码失败", e.getMessage());
			throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "修改密码失败");
		}
	}

	/* ========================== 用户验证=========================== */

	@PostMapping("checkUserName")
	@ApiOperation("用户名验证重复")
	@ResponseBody
	public Response<User> checkUserName(String userName){
		if (ObjectUtils.isEmpty(userName)){
			log.debug("参数为空");
			return new Response<>(ExceptionEnum.USER_PARAM_IS_EMPTY.getStatus(), "用户名为空");
		}
		User user = userService.getOne(new QueryWrapper<User>().eq("userName", userName));
		if (user == null){
			log.error("该用户"+userName+"可以注册");
			return  new Response<>();
		}else{
			log.error("该用户已被注册");
			return new Response<>(ExceptionEnum.USERNAME_IS_BE_REGISTER.getStatus(), "该用户已被注册");
		}
	}


	@PostMapping("checkPhone")
	@ApiOperation("验证电话号码是否被注册")
	@ResponseBody
	public Response<User> checkPhone(String phone){
		if (ObjectUtils.isEmpty(phone)){
			log.debug("参数为空");
			return new Response<>(ExceptionEnum.USER_PARAM_IS_EMPTY.getStatus(), "手机号码为空");
		}
		User user = userService.getOne(new QueryWrapper<User>().eq("phone", phone));
		if (user == null){
			log.error("该号码"+phone+"可以注册");
			return  new Response<>();
		}
		log.error("该号码已被注册");
		return new Response<>(ExceptionEnum.USERNAME_IS_BE_REGISTER.getStatus(), "该号码已被注册");

	}



	@PostMapping("checkEmail")
	@ApiOperation("验证email是否被注册")
	@ResponseBody
	public Response<User> checkEmail(String email){
		if (ObjectUtils.isEmpty(email)){
			log.debug("参数为空");
			return new Response<>(ExceptionEnum.USER_PARAM_IS_EMPTY.getStatus(), "email为空");
		}
		User user = userService.getOne(new QueryWrapper<User>().eq("email", email));
		if (user == null){
			log.error("该email"+email+"可以注册");
			return  new Response<>();
		}
		log.error("该email已被注册");
		return new Response<>(ExceptionEnum.USERNAME_IS_BE_REGISTER.getStatus(), "该email已被注册");
	}


	@PostMapping("isLogin")
	@ApiOperation("用户是否登录")
	@ResponseBody
	public Response<User> isLogin(HttpSession session){

		Object user = session.getAttribute("user");
		if (user == null){
			log.debug("用户未登录");
			return new Response<>(ExceptionEnum.USER_IS_LOGIN_OUT.getStatus(),"用户未登录");
		}else{
			log.debug("用户已经登录");
			return new Response<>();
		}
	}

	//发送短信验证码
	@ResponseBody
	@ApiOperation("发送短信验证码")
	@PostMapping("sendCode")
	public Response sendCode(String phone){
		if (ObjectUtils.isEmpty(phone)){
			log.debug("参数为空");
			return new Response<>(ExceptionEnum.USER_PARAM_IS_EMPTY.getStatus(), "发送短信验证码,手机号码为空");
		}
		//这里写你自己的签名认证
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FqVeHguhdhmyVAertze", "1F7UdqMoZDiW5jlPH0gFJGbFluOllC");
		//获取随机生成的 四位数验证码
		Integer code = NumUtils.createPhoneCode(4);

		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", "shopping商城");
		request.putQueryParameter("TemplateCode", "SMS_176538568");
		//验证码 自己生成动态验证码
		request.putQueryParameter("TemplateParam", "{\"code\":"+code+"}");
		//end
		try {
			CommonResponse response = client.getCommonResponse(request);
			System.out.println(response.getData());
			log.debug("获取手机的验证码成功");
			return new Response(code);
		} catch (Exception e) {
			log.debug("获取手机的验证码失败",e.getMessage());
			e.printStackTrace();
			return new Response(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"获取手机的验证码失败");
		}

	}


	/* ========================== 用户验证=========================== */




	/**
	 * 用户注册
	 *
	 * @author while
	 * @data 下午7:11:28
	 * @param @param user
	 * @param @return
	 * @description TODO
	 */
	@ResponseBody
	@PostMapping("register")
	@ApiOperation("用户注册")
	public  Response<User> register(@RequestBody User user) {
		try {
			// 查询输入的用户名称  和 数据库里边的比较
			User userDB = userService.getOne(new QueryWrapper<User>().eq("userName", user.getUserName()));
			if (userDB == null) {// 若数据库里边没有 则可以注册

				//注册用户信息
				user.setPassword(PasswordMD5.passwordMD5(user.getPassword()));
				user.setCreateTime(DateUtils.formatDate(new Date()));
				boolean flagUser = userService.save(user); // 注册用户信息表
				if(!flagUser) {
					log.debug("创建用户失败");
					return new Response<>(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"系统异常");
				}
				//根据用户名查询用户表
				User newUser = userService.getOne(new QueryWrapper<User>().eq("userName", user.getUserName()));


				//注册角色信息
				UserRole role = new UserRole();
				role.setRoleName("普通用户");
				role.setReferRoleId(1);
				role.setCreateTime(DateUtils.formatDate(new Date()));
				role.setUserId(newUser.getId());
				boolean flagRole = userRoleService.save(role);
				if(!flagRole) {
					log.debug("创建角色异常");
					return new Response<>(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"系统异常");
				}

				//注册用户收货地址
				UserAddress address = new UserAddress();
				address.setCreateTime(DateUtils.formatDate(new Date()));
				address.setPhone(user.getPhone());
				address.setUserName(user.getUserName());
				address.setUserId(newUser.getId());
				boolean flagAddress = userAddressService.save(address);
				if(!flagAddress) {
					log.debug("创建用户地址异常");
					return new Response<>(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"系统异常");
				}

				// 注册用户权限表
				UserPower power = new UserPower();
				power.setUserId(newUser.getId());
				power.setUserName(user.getUserName());
				power.setPowerName("普通用户");
				boolean flagPower = userPowerService.save(power);
				if(!flagPower) {
					log.debug("创建用户权限异常");
					return new Response<>(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"系统异常");
				}


				//注册购物车
				Cart cart = new Cart();
				cart.setUserId(newUser.getId());
				cart.setUserName(user.getUserName());
				cart.setCreateTime(DateUtils.formatDate(new Date()));
				boolean flagCart = cartUser.save(cart);
				if(!flagCart) {
					log.debug("创建用户购物车异常");
					return new Response<>(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"系统异常");
				}


				//注册店铺
				Shop shop = new Shop();
				shop.setShopStatus(1);
				shop.setCreateTime(DateUtils.formatDate(new Date()));
				shop.setShopLevel("一般");
				shop.setShopNum(NumUtils.createShopNum());
				shop.setUserId(newUser.getId());
				shop.setUserName(newUser.getUserName());
				shop.setShopName(newUser.getUserName()+"的店铺");
				boolean flagShop = shopService.save(shop);
				if(!flagShop) {
					log.debug("创建用户店铺异常");
					return new Response<>(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"系统异常");
				}

				//根据userId查询店铺消息 和角色信息
				Shop shop2 = shopService.getOne(new QueryWrapper<Shop>().eq("userId", newUser.getId()));
				UserRole role2 = userRoleService.getOne(new QueryWrapper<UserRole>().eq("userId", newUser.getId()));


				//根据用户id修改 角色id 角色名称 店铺id 店铺名称 进行二次添加
				User user2 = new User();
				user2.setRoleId(role2.getId());
				user2.setRoleName(role2.getRoleName());
				user2.setShopName(shop2.getShopName());
				user2.setShopId(shop2.getId());
				boolean userUpdFlag = userService.update(user2,new UpdateWrapper<User>(new User().setId(newUser.getId())));
				if (!userUpdFlag) {
					log.debug("修改用户异常");
					return new Response<>(ExceptionEnum.USER_REGISTER_FILED.getStatus(),"系统异常");
				}

				//成功
				log.debug(user.getUserName() + "注册成功");
				return new Response<>(user);

			} else {
				log.error("该用户已被注册");
				return new Response<>(ExceptionEnum.USERNAME_IS_BE_REGISTER.getStatus(), "该用户已被注册");
			}
		} catch (Exception e) {
			log.error("添加用户失败", e.getMessage());
			throw new BaseException(ExceptionEnum.UPDATE_USER_FILED, "添加用户失败");
		}
	}
}
