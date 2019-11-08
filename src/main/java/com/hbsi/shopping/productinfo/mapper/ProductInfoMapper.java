package com.hbsi.shopping.productinfo.mapper;

import com.hbsi.shopping.productinfo.entity.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    /**
     * 从数据库里边随机查询指定数据
     */
    @Select("SELECT * FROM product_info  ORDER BY  RAND() LIMIT #{count}")
    List<ProductInfo> randomDataByCount(@Param("count") Integer count);

}
