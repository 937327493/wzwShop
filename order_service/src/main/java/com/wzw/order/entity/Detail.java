package com.wzw.order.entity;

import java.math.BigDecimal;

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
 * 订单详情表
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("order_detail")
@ApiModel(value="Detail对象", description="订单详情表")
public class Detail implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "detail_id", type = IdType.ASSIGN_UUID)
      private String detailId;

    private String orderId;

    private Integer productId;

      @ApiModelProperty(value = "商品名称")
      private String productName;

      @ApiModelProperty(value = "商品单价")
      private BigDecimal productPrice;

      @ApiModelProperty(value = "商品数量")
      private Integer productQuantity;

      @ApiModelProperty(value = "商品小图")
      private String productIcon;

      @TableField(fill = FieldFill.INSERT)
      @ApiModelProperty(value = "创建时间")
      private LocalDateTime createTime;

      @TableField(fill = FieldFill.INSERT_UPDATE)
      @ApiModelProperty(value = "修改时间")
      private LocalDateTime updateTime;


}
