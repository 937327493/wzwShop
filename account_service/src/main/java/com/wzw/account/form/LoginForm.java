package com.wzw.account.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty
    private String mobile;
    @NotEmpty
    private String password;

}
