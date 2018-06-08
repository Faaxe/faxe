package com.nuist.faxe.web.domain;

import lombok.Data;

@Data
public class User {
    private String id;

    private String userName;

    private String passWord;

    private String role;
}