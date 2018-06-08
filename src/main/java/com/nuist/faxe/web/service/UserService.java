package com.nuist.faxe.web.service;

import com.nuist.faxe.web.domain.User;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author ZhouXiang
 **/
public interface UserService {

    User checkLogin(String userName, String passWord);

    String register(String userName, String passWord);

    boolean checkUserName(String userName);

    String getUserNameById(String userId);

    User getUserById(String userId);

    boolean deleteUserById(String userId);

    boolean deleteUserByIds(List<String> userIds);

    long countUser();

    List<User> list(Integer page, Integer limit);

    int updatePassWord(String id, String passWord);
}
