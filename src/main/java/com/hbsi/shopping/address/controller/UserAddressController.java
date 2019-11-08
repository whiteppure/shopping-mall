package com.hbsi.shopping.address.controller;

import java.util.Date;
import java.util.List;

import com.hbsi.shopping.address.service.impl.UserAddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.shopping.address.entity.UserAddress;
import com.hbsi.shopping.address.service.IUserAddressService;
import com.hbsi.shopping.exception.BaseException;
import com.hbsi.shopping.exception.ExceptionEnum;
import com.hbsi.shopping.utils.DateUtils;
import com.hbsi.shopping.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Slf4j
@Controller
@RequestMapping("/address/")
@Api(tags = "用户收货地址模块")
@CacheConfig(cacheNames = "address")
public class UserAddressController {

	private final IUserAddressService userAddressService;

	private final UserAddressServiceImpl userAddressServiceImpl;

	public UserAddressController(IUserAddressService userAddressService, UserAddressServiceImpl userAddressServiceImpl) {
		this.userAddressService = userAddressService;
		this.userAddressServiceImpl = userAddressServiceImpl;
	}

	/**
	 * 根据用户id分页查询全部的收货地址
	 * 
	 * @author while
	 * @data 上午11:26:29
	 * @param @param current
	 * @param @param size
	 * @param @param userId
	 * @param @return
	 * @description TODO
	 */
	@Caching(cacheable = {@Cacheable},put = {@CachePut})
	@GetMapping("getAllAddressByUserIdByPage")
	@ApiOperation("根据用户id分页查询全部的收货地址")
	@ResponseBody
	public Response<IPage<UserAddress>> getAllAddressByUserIdByPage(@ApiParam("当前页") @RequestParam Integer current,
			@ApiParam("每页数量") @RequestParam Integer size, @ApiParam("用户id") @RequestParam Integer userId) {
		try {
			if (ObjectUtils.isEmpty(current)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.ADDRESS_GET_FILED, "current参数错误");
			}
			if (ObjectUtils.isEmpty(size)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.ADDRESS_GET_FILED, "size参数错误");
			}
			if (ObjectUtils.isEmpty(userId)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.ADDRESS_GET_FILED, "userId参数错误");
			}
			Page<UserAddress> page = new Page<UserAddress>(current,size);
			IPage<UserAddress> addressList = userAddressService.page(page,
					new QueryWrapper<UserAddress>().orderByDesc("createTime").eq("userId", userId));
			log.debug("查询成功");
			return new Response<>(addressList);
		} catch (Exception e) {
			log.debug("查询用户的收货地址失败", e.getMessage());
			throw new BaseException(ExceptionEnum.ADDRESS_GET_FILED, "分页查询用户的地址失败");
		}
	}


	/**
	 * 不分页查询用户收货地址信息
	 * @param userId
	 * @return
	 */
	@Caching(cacheable = {@Cacheable},put = {@CachePut})
	@GetMapping("getAllAddressByUserId")
	@ApiOperation("根据用户id不分页查询全部的收货地址")
	@ResponseBody
	public Response<List<UserAddress>> getAllAddressByUserId(
			@ApiParam("用户id") @RequestParam Integer userId) {
		try {
			if (ObjectUtils.isEmpty(userId)) {
				log.debug("查询失败");
				return new Response<>(ExceptionEnum.ADDRESS_GET_FILED.getStatus(), "userId参数错误");
			}
			List<UserAddress> addressList = userAddressService.list(new QueryWrapper<UserAddress>().orderByDesc("id").eq("userId", userId));
			log.debug("查询成功");
			return new Response<>(addressList);
		} catch (Exception e) {
			log.debug("不分页查询用户的收货地址失败", e.getMessage());
			throw new BaseException(ExceptionEnum.ADDRESS_GET_FILED, "不分页查询用户的地址失败");
		}
	}






	/**
	 * 根据地址id查询地址
	 * 
	 * @author while
	 * @data 上午11:34:19
	 * @param @param id
	 * @param @return
	 * @description TODO
	 */
	@Caching(cacheable = {@Cacheable},put = {@CachePut})
	@GetMapping("getAddressById")
	@ApiOperation("根据地址id查询地址信息")
	@ResponseBody
	public Response<UserAddress> getAddressById(@ApiParam("地址id") @RequestParam Integer id) {
		try {
			if (ObjectUtils.isEmpty(id)) {
				log.debug("查询失败");
				throw new BaseException(ExceptionEnum.ADDRESS_GET_FILED, "id参数错误");
			}
			UserAddress address = userAddressService.getOne(new QueryWrapper<UserAddress>().eq("id", id));
			if (ObjectUtils.isEmpty(address)){
			    log.debug("暂无该地址信息");
			    return new Response<>(ExceptionEnum.ADDRESS_IS_EMPTY.getStatus(),"该地址信息为空");
            }
			log.debug("查询成功");
			return new Response<>(address);
		} catch (Exception e) {
			log.debug("查询用户的收货地址失败", e.getMessage());
			throw new BaseException(ExceptionEnum.ADDRESS_GET_FILED, "根据地址id查询地址");
		}
	}

	/**
	 * 根据地址id删除收货地址
	 * 
	 * @author while
	 * @data 下午2:09:13
	 * @param @param id
	 * @param @return
	 * @description TODO
	 */
	@CachePut
	@ApiOperation("根据地址id删除收货地址")
	@GetMapping("deleteAddressById")
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
	@Transactional
	@ResponseBody
	public Response<UserAddress> deleteAddressById(@ApiParam("地址id") @RequestParam Integer id) {
		try {
			if (ObjectUtils.isEmpty(id)) {
				log.debug("删除失败");
				throw new BaseException(ExceptionEnum.ADDRESS_DELETE_FILED, "id参数错误");
			}
            boolean flag = userAddressServiceImpl.deleteAddressById(id);
			if (flag) {
				log.debug("删除成功");
				return new Response<>();
			} else {
				log.debug("删除失败");
				throw new BaseException(ExceptionEnum.ADDRESS_DELETE_FILED, "删除收货地址失败");
			}
		} catch (Exception e) {
			log.debug("删除收货地址失败", e.getMessage());
			throw new BaseException(ExceptionEnum.ADDRESS_DELETE_FILED, "删除收货地址失败");
		}
	}

	/**
	 * 添加收货地址
	 * 
	 * @author while
	 * @data 下午2:18:06
	 * @param @param userAddress
	 * @param @return
	 * @description TODO
	 */
	@Transactional
	@CachePut
	@PostMapping("addAddress")
	@ApiOperation("添加收货地址")
	public String addAddress(UserAddress userAddress, Model model) {
		try {
			//根据userId查询用户的收货地址  如果该用户的收货地址数量超过4个 不让添加
			List<UserAddress> userAddresses = userAddressService.list(new QueryWrapper<UserAddress>().eq("userId", userAddress.getUserId()));
			if (userAddresses.size() > 3){
				model.addAttribute("msgFiled","收货地址上限为4个");
				return "shop/my-address";
			}
			userAddress.setCreateTime(DateUtils.formatDate(new Date()));
			if (userAddressService.save(userAddress)) {
				log.debug("添加成功");
				model.addAttribute("message","添加收货地址成功");
				return "shop/my-address";
			} else {
				log.debug("添加失败");
				model.addAttribute("msgFiled","添加收货地址失败");
				return "shop/my-address";
			}
		} catch (Exception e) {
			log.debug("添加失败", e.getMessage());
			throw new BaseException(ExceptionEnum.ADDRESS_ADD_FILED, "添加用户收货地址失败");
		}
	}

	/**
	 * 修改用户的收货地址
	 * 
	 * @author while
	 * @data 下午2:24:41
	 * @param @param userAddress
	 * @param @return
	 * @description TODO
	 */
	@Transactional
	@CachePut
	@PostMapping("updateAddressById")
	@ApiOperation("根据收货地址id修改用户的收货地址")
	@ResponseBody
	public Response<UserAddress> updateAddressById(@RequestBody UserAddress userAddress) {
		try {
			UserAddress addresss = new UserAddress();
			addresss.setAddress(userAddress.getAddress());
			addresss.setPhone(userAddress.getPhone());
			addresss.setComment(userAddress.getComment());
			addresss.setUserName(userAddress.getUserName());
			addresss.setAddressTag(userAddress.getAddressTag());

			boolean flag = userAddressService.update(addresss,
					new UpdateWrapper<>(new UserAddress().setId(userAddress.getId())));
			if (flag) {
				log.debug("修改成功");
				return new Response<>();
			} else {
				log.debug("修改失败");
				throw new BaseException(ExceptionEnum.ADDRESS_UPDATE_FILED, "修改用户地址失败");
			}
		} catch (Exception e) {
			log.debug("修改失败", e.getMessage());
			throw new BaseException(ExceptionEnum.ADDRESS_UPDATE_FILED, "修改用户地址失败");
		}
	}


}
