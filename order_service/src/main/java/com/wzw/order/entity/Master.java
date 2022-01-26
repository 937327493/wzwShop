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
 * 订单表
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-18
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("order_master")
@ApiModel(value="Master对象", description="订单表")
public class Master implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "order_id", type = IdType.ASSIGN_UUID)
      private String orderId;

      @ApiModelProperty(value = "买家名字")
      private String buyerName;

      @ApiModelProperty(value = "买家电话")
      private String buyerPhone;

      @ApiModelProperty(value = "买家地址")
      private String buyerAddress;

      @ApiModelProperty(value = "买家id")
      private Integer buyerOpenid;

      @ApiModelProperty(value = "订单总金额")
      private BigDecimal orderAmount;

      @ApiModelProperty(value = "订单状态，默认0新下单,1完成，2取消")
      private Integer orderStatus;

      @ApiModelProperty(value = "支付状态，默认0未支付，1已支付")
      private Integer payStatus;

      @TableField(fill = FieldFill.INSERT)
      @ApiModelProperty(value = "创建时间")
      private LocalDateTime createTime;

      @TableField(fill = FieldFill.INSERT_UPDATE)
      @ApiModelProperty(value = "修改时间")
      private LocalDateTime updateTime;


}
