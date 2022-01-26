package com.wzw.product.mapper;

import com.wzw.product.entity.Info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
public interface InfoMapper extends BaseMapper<Info> {
    public BigDecimal findPriceById(Integer id);

    //根据id查询有多少库存
    public Integer subStockById(Integer id);

    //更新库存
    public Integer subStock(Integer id,Integer quality);

    //按照商品id更新状态
    public boolean updateStatus(String id,boolean status);
}
