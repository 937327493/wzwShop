package com.wzw.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("product_info")
@ApiModel(value="Info对象", description="商品表")
public class Info implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "product_id", type = IdType.AUTO)
      private Integer productId;

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

      @ApiModelProperty(value = "创建时间")
      private LocalDateTime createTime;

      @ApiModelProperty(value = "修改时间")
      private LocalDateTime updateTime;

      @ApiModelProperty(value = "商品状态，1正常0下架")
      private Integer productStatus;


}
