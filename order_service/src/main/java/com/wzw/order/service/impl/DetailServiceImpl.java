package com.wzw.order.service.impl;

import com.wzw.order.entity.Detail;
import com.wzw.order.mapper.DetailMapper;
import com.wzw.order.service.DetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.order.vo.BasicLineSaleVo;
import com.wzw.order.vo.StackedLineSaleDatabase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements DetailService {
    @Resource
    DetailMapper detailMapper;

    @Override
    public Integer number(Integer productId) {
        return detailMapper.number(productId);
    }

    @Override
    public List<Integer> productId() {
        return detailMapper.productId();
    }

    @Override
    public List<BasicLineSaleVo> createTime() {
        return detailMapper.createTime();
    }

    @Override
    public List<StackedLineSaleDatabase> stackedLineSale(String name) {
        return detailMapper.stackedLineSale(name);
    }

    @Override
    public List<String> listName() {
        return detailMapper.listName();

    }
}
