package com.hbsi.shopping.vo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "用户订单信息模块",description = "保存用户订单信息")
public class OrderAddressProductVo {

    /**
     * 订单信息id
     */
    @TableId(value = "id",type= IdType.AUTO)
    private  Integer id;

    private  Integer orderInfoStatus;

    private Integer addressId;

    private String address;

    private String phone;

    private String userName;

    private Integer productId;

    private String productName;

    private  Integer shopId;


}
