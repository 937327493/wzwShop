package com.wzw.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.wzw.order.entity.Detail;
import com.wzw.order.entity.Info;
import com.wzw.order.entity.Master;
import com.wzw.order.feigns.ProductFeign;
import com.wzw.order.form.OrderForm;
import com.wzw.order.form.ProductForm;
import com.wzw.order.mapper.DetailMapper;
import com.wzw.order.mapper.MasterMapper;
import com.wzw.order.service.MasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.order.vo.DetailOrder;
import com.wzw.order.vo.DetailOrderSm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
@Service
public class MasterServiceImpl extends ServiceImpl<MasterMapper, Master> implements MasterService {
    @Resource
    MasterMapper masterMapper;

    @Resource
    ProductFeign productFeign;


    @Resource
    DetailMapper detailMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public String create(OrderForm orderForm) {
        Master master = new Master();
        master.setBuyerName(orderForm.getName());
        master.setBuyerAddress(orderForm.getAddress());
        master.setBuyerOpenid(orderForm.getId());
        master.setBuyerPhone(orderForm.getPhone());
        master.setOrderStatus(0);
        master.setPayStatus(0);
        //创建订单的过程中我需要计算订单的总金额
        //我们需要用feign来向product_service模块发送请求
        List<ProductForm> items = orderForm.getItems();
        //用来接收总价
        BigDecimal allPrice = new BigDecimal(0);
        for (ProductForm item : items) {
            //拿到该商品的单价
            BigDecimal priceById = productFeign.findPriceById(item.getProductId());
            //单价*数量
            BigDecimal multiply = priceById.multiply(new BigDecimal(item.getProductQuantity()));
            allPrice = allPrice.add(multiply);
        }
        master.setOrderAmount(allPrice);
        masterMapper.insert(master);
        //订单和订单详情是一对多
        for (ProductForm item : items) {
            //将订单号输入到订单详情、商品id也输入到订单详情、订单的商品数量也输入
            Detail detail1 = new Detail();
            detail1.setOrderId(master.getOrderId());
            detail1.setProductId(item.getProductId());
            detail1.setProductQuantity(item.getProductQuantity());
            //根据商品id查到商品详情
            Info byId = productFeign.findById(item.getProductId());
            detail1.setProductIcon(byId.getProductIcon());
            detail1.setProductName(byId.getProductName());
            detail1.setProductPrice(byId.getProductPrice());
            //然后放到订单详情表里
            int insert = detailMapper.insert(detail1);
        }
        //将订单信息放到mq里面
        //存入MQ
        this.rocketMQTemplate.convertAndSend("myTop","有新的订单");
        return master.getOrderId();
    }

    @Override
    public DetailOrder listFind(String orderId, Integer buyerId) {
        Master master = masterMapper.selectById(orderId);
        DetailOrder detailOrder = new DetailOrder();
        BeanUtils.copyProperties(master,detailOrder);
        QueryWrapper<Detail> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("order_id",orderId);
        List<Detail> details = detailMapper.selectList(objectQueryWrapper);
        ArrayList<DetailOrderSm> objects = new ArrayList<>();
        for (Detail detail : details) {
            DetailOrderSm detailOrderSm = new DetailOrderSm();
            BeanUtils.copyProperties(detail,detailOrderSm);
            objects.add(detailOrderSm);
        }
        detailOrder.setOrderDetailList(objects);
        return detailOrder;
    }

    @Override
    public boolean cancel(String orderId,Integer buyerId) {
        return masterMapper.cancel(orderId,buyerId);
    }

    @Override
    public boolean cancel(String orderId) {
        return masterMapper.cancell(orderId);
    }

    @Override
    public boolean finish(String orderId) {
        return masterMapper.finish(orderId);
    }

    @Override
    public boolean pay(String orderId, Integer buyerId) {

        return masterMapper.pay(orderId,buyerId);
    }

}
