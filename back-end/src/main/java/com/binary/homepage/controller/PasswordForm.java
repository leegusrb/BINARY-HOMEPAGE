package com.binary.homepage.controller;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class PasswordForm {
    @Length(min = 8, max = 16)
    private String newPassword;
    @Length(min = 8, max = 16)
    private String newPasswordConfirm;
}
