package com.wzw.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.exceptions.WzwRunException;
import com.wzw.order.entity.Master;
import com.wzw.order.form.OrderForm;
import com.wzw.order.service.MasterService;
import com.wzw.order.vo.DetailOrder;
import com.wzw.result.ResponseEnum;
import com.wzw.templates.R;
import com.wzw.util.RUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
@RestController
@RequestMapping("/buyer/order")
public class MasterController {
    @Autowired
    MasterService masterService;
    @RequestMapping(value = "create",method = RequestMethod.POST)
    public R create(@RequestBody @Valid OrderForm orderForm, BindingResult bindingResult){
        //如果绑定过程发生异常，抛出异常
        if (bindingResult.hasErrors()){
            throw new WzwRunException(ResponseEnum.ORDER_CREATE_ERROR.getMsg());
        }
        return RUtil.success(masterService.create(orderForm));
    }

    @GetMapping("list/{buyerId}/{page}/{size}")
    public R list(@PathVariable("buyerId")Integer buyerId,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        //利用分页查询
        Page<Master> objectPage = new Page<>(page,size);
        QueryWrapper<Master> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("buyer_openid",buyerId);
        Page<Master> page1 = masterService.page(objectPage, objectQueryWrapper);
        return RUtil.success(page1.getRecords());
    }

    @GetMapping("detail/{buyerId}/{orderId}")
    public R detail(@PathVariable("orderId")String orderId,@PathVariable("buyerId")Integer buyerId)
    {
        DetailOrder detailOrder = masterService.listFind(orderId, buyerId);
        return  RUtil.success(detailOrder);
    }

    @PutMapping("cancel/{buyerId}/{orderId}")
    public R cancel(@PathVariable("orderId")String orderId,@PathVariable("buyerId")Integer buyerId){
        masterService.cancel(orderId,buyerId);
        return RUtil.success(null);
    }

    @PutMapping("finish/{orderId}")
    public R finish(@PathVariable("orderId")String orderId){
        masterService.finish(orderId);
        return RUtil.success(null);
    }

    @PutMapping("pay/{buyerId}/{orderId}")
    public R finish(@PathVariable("orderId")String orderId,@PathVariable("buyerId")Integer buyerId){
        masterService.pay(orderId,buyerId);
        return RUtil.success(null);
    }
}

