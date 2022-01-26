package com.wzw.product.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
public class InfoUpdateForm {
    @JsonProperty("status")
    private boolean productStatus;
    @JsonProperty("id")
    @NotNull
    private Integer productId;
    @JsonProperty("name")
    @NotEmpty
    private String  productName;
    @JsonProperty("price")
    @NotNull
    private BigDecimal productPrice;
    @JsonProperty("stock")
    @NotNull
    private Integer productStock;

    @JsonProperty("description")
    @NotEmpty
    private String productDescription;

    @JsonProperty("icon")
    @NotEmpty
    private String productIcon;

    private CategoryForm category;
}
