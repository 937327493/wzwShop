package com.wzw.order.mapper;

import com.wzw.order.entity.Master;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
public interface MasterMapper extends BaseMapper<Master> {
    public boolean cancel(String orderId,Integer buyerId);
    public boolean cancell(String orderId);
    public boolean finish(String orderId);
    public boolean pay(String orderId,Integer buyerId);
}
