package com.wzw.order.form;


import lombok.Data;
import lombok.ToString;


import javax.validation.constraints.NotNull;


@Data
@ToString
public class ProductForm {
    @NotNull(message = "商品id不能为空")
    private Integer productId;
    @NotNull(message = "商品数量不能为空")
    private Integer productQuantity;
}
