package com.wzw.order.vo;

import lombok.Data;

import java.util.List;

@Data//商品名、类型，销量，数据
public class StackedLineSaleDatasVo {
    private String name;
    private String type;
    private String stack;
    private List<Integer> data;
}
