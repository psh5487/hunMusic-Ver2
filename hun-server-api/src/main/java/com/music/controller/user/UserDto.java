package com.music.controller.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private String profileImgUrl;

    private List<String> roles;

}
