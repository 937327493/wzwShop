package com.wzw.product.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.product.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzw.product.entity.Info;

import java.util.List;

/**
 * <p>
 * 类目表 Mapper 接口
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> selectList(QueryWrapper<Info> objectQueryWrapper);
}
