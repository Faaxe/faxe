package com.nuist.faxe.web.domain;

import lombok.Data;

/**
 * 封装登录信息的实体
 *
 * @author ZhouXiang
 * @create 2018-04-27 15:36
 **/
@Data
public class LoginInfo {
    private String userName;

    private String passWord;
}
