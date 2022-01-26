package com.wzw.product.Vo;

import lombok.Data;

import java.util.List;

@Data
public class Rpro {
    private String name;
    private Integer type;
    private List<Good> goods;
}
