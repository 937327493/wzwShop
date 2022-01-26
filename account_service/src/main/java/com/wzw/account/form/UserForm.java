package com.wzw.account.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class UserForm {
    @NotNull
    private String code;
    @NotEmpty
    private String mobile;
    @NotEmpty
    private String password;
}
