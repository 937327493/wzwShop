package com.wzw.account.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
@Data
public class UserVo {
    private String mobile;
    private String password;
    private Integer userId;
    private String token;
}
