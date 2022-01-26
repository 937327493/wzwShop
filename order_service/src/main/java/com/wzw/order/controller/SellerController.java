package com.wzw.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.order.entity.Detail;
import com.wzw.order.entity.Master;
import com.wzw.order.service.DetailService;
import com.wzw.order.service.MasterService;
import com.wzw.order.vo.*;

import com.wzw.templates.R;
import com.wzw.util.RUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seller/order")
public class SellerController {
    @Autowired
    MasterService masterService;
    @Autowired
    DetailService detailService;

    //分页查询所有订单
    @GetMapping("list/{page}/{size}")
    public R list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Master> objectPage = new Page<>(page, size);
        Page<Master> page1 = masterService.page(objectPage, null);
        List<Master> records = page1.getRecords();
        ArrayList<SellerMaster> objects = new ArrayList<>();
        for (Master record : records) {
            SellerMaster sellerMasterList = new SellerMaster();
            BeanUtils.copyProperties(record, sellerMasterList);
            objects.add(sellerMasterList);
        }
        SellerMasterFVo sellerMasterFVo = new SellerMasterFVo();
        sellerMasterFVo.setContent(objects);
        sellerMasterFVo.setSize((int) page1.getSize());
        sellerMasterFVo.setTotal((int) page1.getTotal());
        return RUtil.success(sellerMasterFVo);
    }

    //根据id取消订单
    @PutMapping("cancel/{orderId}")
    public R cancel(@PathVariable("orderId") String orderId) {
        boolean cancel = masterService.cancel(orderId);
        if (cancel) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }

    //根据id完结订单
    @PutMapping("finish/{orderId}")
    public R finish(@PathVariable("orderId") String orderId) {
        boolean cancel = masterService.finish(orderId);
        if (cancel) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }

    //柱状图信息
    @PutMapping("barSale")
    public R barSale() {
        //准备BarSaleVo
        BarSaleVo barSaleVo = new BarSaleVo();
        List<String> objects = new ArrayList<>();
        barSaleVo.setNames(objects);
        ArrayList<BarSaleValuesVo> objects1 = new ArrayList<>();
        barSaleVo.setValues(objects1);
        //先把productId全部找出来
        List<Integer> productIds = detailService.productId();
        //然后根据productIds来遍历查找所有商品的销量及其信息
        for (Integer productId : productIds) {
            //拿到该商品销量
            Integer number = detailService.number(productId);
            //拿到该商品名称
            QueryWrapper<Detail> objectQueryWrapper = new QueryWrapper<>();
            Page page = new Page(1, 1);
            objectQueryWrapper.eq("product_id", productId);
            Page page1 = detailService.page(page, objectQueryWrapper);
            List records = page1.getRecords();
            Detail detail = (Detail) records.get(0);
            String productName = detail.getProductName();
            //将该商品名称加入Vo
            barSaleVo.getNames().add(productName);
            //将该商品销量及颜色样式加入Vo
            BarSaleValuesVo barSaleValuesVo = new BarSaleValuesVo();
            barSaleValuesVo.setValue(number);
            BarSaleItemstyleVo barSaleItemstyleVo = new BarSaleItemstyleVo();
            barSaleItemstyleVo.setColor("#c4ebad");
            barSaleValuesVo.setItemStyle(barSaleItemstyleVo);
            barSaleVo.getValues().add(barSaleValuesVo);
        }
        return RUtil.success(barSaleVo);
    }


    //基础折线图
    @PutMapping("basicLineSale")
    public R basicLineSale(){
        //需要按照create_time分组得到所有的create_time(格式化后的)
        //再按照product_quantity来查出每天商品的销售量
        List<BasicLineSaleVo> timeAndStack = detailService.createTime();
        //我要创建一个BasicLineSale来把查到的数据都存储起来
        BasicLineSale basicLineSale = new BasicLineSale();
        List<Integer> arrayIntegerList = new ArrayList();
        List<String> arrayDateList = new ArrayList();
        basicLineSale.setNames(arrayDateList);
        basicLineSale.setValues(arrayIntegerList);
        for (BasicLineSaleVo stackedLineSaleVo : timeAndStack) {
            //存储数据
            basicLineSale.getNames().add(stackedLineSaleVo.getDate());
            basicLineSale.getValues().add(stackedLineSaleVo.getStack());
        }
        //返回前端
        return RUtil.success(basicLineSale);
    }

    //堆叠折线图
    @PutMapping("stackedLineSale")
    public R stackedLineSale(){
        //查所有去重的商品名称
        List<String> list = detailService.listName();
        //创建StackedLineSaleVo
        StackedLineSaleVo stackedLineSaleVo = new StackedLineSaleVo();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<StackedLineSaleDatasVo> datas = new ArrayList<>();
        stackedLineSaleVo.setNames(names);
        stackedLineSaleVo.setDates(dates);
        stackedLineSaleVo.setDatas(datas);
        for (String detail : list) {
            //按照去重的商品名称查询StackedLineSaleDatabase
            List<StackedLineSaleDatabase> stackedLineSaleDatabases = detailService.stackedLineSale(detail);
            //加名字
            stackedLineSaleVo.getNames().add(detail);

            StackedLineSaleDatasVo stackedLineSaleDatasVo = new StackedLineSaleDatasVo();
            stackedLineSaleDatasVo.setName(detail);
            stackedLineSaleDatasVo.setStack("销量");
            stackedLineSaleDatasVo.setType("line");
            ArrayList<Integer> data = new ArrayList<>();
            stackedLineSaleDatasVo.setData(data);
            //循环加日期,由于sql是查询所有有销量的日期，所有只需要用第一次的进行日期的写入即可
            if (detail == list.get(0)) {
                //拿到第一个商品
                for (StackedLineSaleDatabase stackedLineSaleDatabase : stackedLineSaleDatabases) {
                    //拿到每一个日期放进StackedLineSaleVo
                    stackedLineSaleVo.getDates().add(stackedLineSaleDatabase.getDate());
                }
            }
            //循环加销量
            for (StackedLineSaleDatabase stackedLineSaleDatabase : stackedLineSaleDatabases) {

                //循环加销量
                data.add(stackedLineSaleDatabase.getCount());
            }
            //放到StackedLineSaleVo.java中去
            stackedLineSaleVo.getDatas().add(stackedLineSaleDatasVo);
        }
        return RUtil.success(stackedLineSaleVo);
    }
}
