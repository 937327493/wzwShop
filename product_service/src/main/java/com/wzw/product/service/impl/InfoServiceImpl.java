package com.wzw.product.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.exceptions.WzwRunException;
import com.wzw.product.Vo.ProductExcelVO;
import com.wzw.product.entity.Category;
import com.wzw.product.entity.Info;
import com.wzw.product.mapper.CategoryMapper;
import com.wzw.product.mapper.InfoMapper;
import com.wzw.product.service.InfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.result.ResponseEnum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

    @Resource
    InfoMapper infoMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Override
    public BigDecimal findPriceById(Integer id) {
        BigDecimal priceById = infoMapper.findPriceById(id);
        return priceById;
    }
    @Override
    public boolean subStockById(Integer id, Integer quality) {
        Integer integer = infoMapper.subStockById(id);
        Integer quality1 = integer - quality;
        if (quality1 < 0) {
            throw new WzwRunException(ResponseEnum.PRODUCT_STOCK_ERROR.getMsg());
        }
        infoMapper.subStock(id,quality);
        return true;
    }

    @Override
    public boolean updateStatus(String id, boolean status) {

        return infoMapper.updateStatus(id,status);
    }

    @Override
    public List<ProductExcelVO> productExcelVOList() {
        List<Info> infos = infoMapper.selectList(null);
        ArrayList<ProductExcelVO> objects = new ArrayList<>();
        for (Info info : infos) {
            ProductExcelVO productExcelVO = new ProductExcelVO();
            BeanUtils.copyProperties(info,productExcelVO);
            QueryWrapper<Category> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("category_type",info.getCategoryType());
            Category category = categoryMapper.selectOne(objectQueryWrapper);
            productExcelVO.setCategoryName(category.getCategoryName());
            productExcelVO.setProductStatus("上架");
            if (info.getProductStatus() == 0) {
                productExcelVO.setProductStatus("下架");
            }
            objects.add(productExcelVO);
        }
        return objects;
    }


    @Override
    public List<Info> excleToProductInfoList(InputStream inputStream) {
        try {
            List<Info> list = new ArrayList<>();
            EasyExcel.read(inputStream)//读取流
                    .head(ProductExcelVO.class)//以ProductExcelVO类为格式解析excel的每一行数据
                    .sheet()//以表的形式解析
                    .registerReadListener(new AnalysisEventListener<ProductExcelVO>() {

                        @Override//一行解析出来的数据会放在一个ProductExcelVO里
                        public void invoke(ProductExcelVO excelData, AnalysisContext analysisContext) {
                            Info productInfo = new Info();
                            BeanUtils.copyProperties(excelData, productInfo);//ProductExcelVO放到Info
                            if(excelData.getProductStatus().equals("正常")){
                                productInfo.setProductStatus(1);
                            }else{
                                productInfo.setProductStatus(0);
                            }
                            list.add(productInfo);//加入到集合
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        }
                    }).doRead();//开始执行
            return list;//返回这个集合
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
