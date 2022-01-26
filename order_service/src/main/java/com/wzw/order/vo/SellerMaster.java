package com.wzw.order.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SellerMaster {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress   ;
    private Integer buyerOpenid;
    private Integer orderAmount;
    private Integer orderStatus;
    private Integer payStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
