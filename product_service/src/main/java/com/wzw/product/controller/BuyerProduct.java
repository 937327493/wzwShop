package com.wzw.product.controller;


import com.wzw.product.Vo.Good;
import com.wzw.product.Vo.Rpro;
import com.wzw.product.entity.Info;
import com.wzw.product.mapper.InfoMapper;
import com.wzw.product.service.CategoryService;
import com.wzw.product.service.InfoService;
import com.wzw.result.ResponseEnum;
import com.wzw.templates.R;
import com.wzw.util.RUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类目表 前端控制器
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
@RestController
@RequestMapping(value = "/buyer/product",method = RequestMethod.GET)
public class BuyerProduct {
    @Autowired
    InfoService infoService;

    @Autowired
    CategoryService categoryService;
    @RequestMapping("list")
    //Rpro包含了商品及其分类   商品分类和商品的关系是  一对多 ，一对多可以利用
    public R categoryList(){
        List<Rpro> allRpro = categoryService.findAllRpro();
        R success = RUtil.success(allRpro);
        return success;
    }

    @RequestMapping("findPriceById/{id}")
    public BigDecimal findPriceById(@PathVariable("id")Integer idd){
       return infoService.findPriceById(idd);
    }

    @RequestMapping("findById/{id}")
    public Info findById(@PathVariable("id") Integer id){
        return infoService.getById(id);
    }

    @RequestMapping(value = "subStockById/{id}/{quantity}", method = RequestMethod.PUT)
    public boolean subStockById(@PathVariable("id")Integer id, @PathVariable("quantity")Integer quantity){
        boolean b = infoService.subStockById(id, quantity);
        if (b) {
            return b;
        }
        return false;

    }
}

