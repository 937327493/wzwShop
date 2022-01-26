package com.wzw.order.feigns;

import com.wzw.order.entity.Info;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@FeignClient("product-service")
public interface ProductFeign {
    //根据商品id得到商品价格
    @GetMapping("/buyer/product/findPriceById/{id}")
    public  BigDecimal findPriceById(@PathVariable("id")Integer idd);

    //根据商品id得到商品详情
    @GetMapping("/buyer/product/findById/{id}")
    public Info findById(@PathVariable("id") Integer id);
}

