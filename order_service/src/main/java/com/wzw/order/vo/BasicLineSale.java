package com.wzw.order.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BasicLineSale {
    private List<Integer> values;
    private List<String> names;

}
