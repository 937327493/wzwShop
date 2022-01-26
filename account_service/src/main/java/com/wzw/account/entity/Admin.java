package com.wzw.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-19
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="Admin对象", description="")
public class Admin implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "admin_id", type = IdType.AUTO)
      private Integer adminId;

      @ApiModelProperty(value = "账号")
      private String username;

      @ApiModelProperty(value = "密码")
      private String password;

      @ApiModelProperty(value = "用户头像")
      private String imgUrl;

      @ApiModelProperty(value = "用户名称")
      private String name;


}
