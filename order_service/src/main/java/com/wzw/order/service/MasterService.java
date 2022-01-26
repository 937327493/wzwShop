package com.wzw.order.service;

import com.wzw.order.entity.Master;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.order.form.OrderForm;
import com.wzw.order.vo.DetailOrder;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
public interface MasterService extends IService<Master> {
    public String create(OrderForm orderForm);
    public DetailOrder listFind(String orderId,Integer buyerId);
    public boolean cancel(String orderId,Integer buyerId);
    public boolean cancel(String orderId);

    public boolean finish(String orderId);

    public boolean pay(String orderId,Integer buyerId);

}
