package com.wzw.product.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class CategoryForm {
    @NotNull
    private Integer categoryId;
    @NotEmpty
    private String categoryName;
    @NotNull
    private Integer categoryType;
}
