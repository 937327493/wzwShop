package com.wzw.product.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类目表
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("product_category")
@ApiModel(value="Category对象", description="类目表")
public class Category implements Serializable {
      @TableId(value = "category_id", type = IdType.AUTO)
      private Integer categoryId;

      @ApiModelProperty(value = "类目名称")
      private String categoryName;

      @ApiModelProperty(value = "类目编号")
      private Integer categoryType;

      @ApiModelProperty(value = "创建时间")
      @TableField(value = "create_time",fill = FieldFill.INSERT)
      private LocalDateTime createTime;

      @ApiModelProperty(value = "修改时间")
      @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime updateTime;
}
