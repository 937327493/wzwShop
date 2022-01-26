package com.wzw.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.wzw.product.Vo.Good;
import com.wzw.product.Vo.Rpro;
import com.wzw.product.entity.Category;
import com.wzw.product.entity.Info;
import com.wzw.product.mapper.CategoryMapper;
import com.wzw.product.mapper.InfoMapper;
import com.wzw.product.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.product.service.InfoService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类目表 服务实现类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    InfoMapper infoMapper;
    @Override
    //要将查出的信息转换成对应的json对象格式,放到新的集合
    public List<Rpro> findAllRpro() {
        List<Category> categories = categoryMapper.selectList((Wrapper<Category>) null);
        ArrayList<Rpro> rpros = new ArrayList<>();
        for (Category category : categories) {
            Rpro rpro = new Rpro();
            rpro.setType(category.getCategoryType());
            rpro.setName(category.getCategoryName());

            QueryWrapper<Info> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("category_type",category.getCategoryType());
            ArrayList<Good> objects = new ArrayList<>();
            List<Info> categories1 = infoMapper.selectList(objectQueryWrapper);
            for (Info info : categories1) {
                Good good = new Good();
                BeanUtils.copyProperties(info,good);
                objects.add(good);
            }
            rpro.setGoods(objects);
            rpros.add(rpro);
        }
        return rpros;
    }

    public List<Rpro> findAllProductCategory(){
        List<Category> categories = categoryMapper.selectList((Wrapper<Category>) null);
        ArrayList<Rpro> rpros = new ArrayList<>();
        for (Category category : categories) {
            Rpro rpro = new Rpro();
            rpro.setType(category.getCategoryType());
            rpro.setName(category.getCategoryName());
            rpro.setGoods(null);
            rpros.add(rpro);
        }
        return rpros;
    }
}
