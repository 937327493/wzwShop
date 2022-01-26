package com.wzw.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class StackedLineSaleVo {
    private List<String> names;
    private List<String> dates;
    private List<StackedLineSaleDatasVo> datas;
}
