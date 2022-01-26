package com.wzw.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.product.Vo.ProductExcelVO;
import com.wzw.product.entity.Category;
import com.wzw.product.entity.Info;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
public interface InfoService extends IService<Info> {
    public BigDecimal findPriceById(Integer price);

    public boolean subStockById(Integer id,Integer quality);

    public boolean updateStatus(String id,boolean status);

    public List<ProductExcelVO> productExcelVOList();

    public List<Info> excleToProductInfoList(InputStream inputStream);

}
