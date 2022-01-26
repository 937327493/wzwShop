package com.wzw.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class SellerMasterFVo {
    private List<SellerMaster> content;
    private Integer size;
    private Integer total;
}
