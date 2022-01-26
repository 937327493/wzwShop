package com.wzw.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class BarSaleVo {
    private List<String> names;
    private List<BarSaleValuesVo> values;
}
