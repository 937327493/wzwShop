package com.wzw.order.service;

import com.wzw.order.entity.Detail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.order.vo.BasicLineSaleVo;
import com.wzw.order.vo.StackedLineSaleDatabase;

import java.util.List;

/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
public interface DetailService extends IService<Detail> {
    public Integer number(Integer productId);
    public List<Integer> productId();
    public  List<BasicLineSaleVo> createTime();
    public List<StackedLineSaleDatabase> stackedLineSale(String name);
    public List<String> listName();
}
