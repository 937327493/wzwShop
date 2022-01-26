package com.wzw.product.service;

import com.wzw.product.Vo.Rpro;
import com.wzw.product.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 类目表 服务类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
public interface CategoryService extends IService<Category> {
    //查询所有数据库中的商品分类
    public List<Rpro> findAllRpro();
    //查询所有数据库中的商品分类不包含商品
    public List<Rpro> findAllProductCategory();
}
