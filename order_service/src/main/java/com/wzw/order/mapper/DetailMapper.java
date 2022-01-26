package com.wzw.order.mapper;

import com.wzw.order.entity.Detail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzw.order.vo.BasicLineSaleVo;
import com.wzw.order.vo.StackedLineSaleDatabase;

import java.util.List;

/**
 * <p>
 * 订单详情表 Mapper 接口
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
public interface DetailMapper extends BaseMapper<Detail> {
    public Integer number(Integer productId);
    public List<Integer> productId();
    public List<BasicLineSaleVo> createTime();
    public List<StackedLineSaleDatabase> stackedLineSale(String name);
    public List<String>  listName();

}
