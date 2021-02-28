package com.aiposizi.lab.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class UserDto {

    private Long id;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
}
