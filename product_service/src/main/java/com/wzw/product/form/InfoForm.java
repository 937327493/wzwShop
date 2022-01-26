package com.wzw.product.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InfoForm {

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品单价")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "库存")
    private Integer productStock;

    @ApiModelProperty(value = "描述")
    private String productDescription;

    @ApiModelProperty(value = "小图")
    private String productIcon;

    @ApiModelProperty(value = "类目编号")
    private Integer categoryType;



    @ApiModelProperty(value = "商品状态，1正常0下架")
    private Integer productStatus;
}
