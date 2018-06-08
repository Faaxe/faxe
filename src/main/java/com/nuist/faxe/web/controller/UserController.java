package com.nuist.faxe.web.controller;

import com.nuist.faxe.common.controller.message.ResponseMessage;
import com.nuist.faxe.common.entity.Msg;
import com.nuist.faxe.common.utils.ParamConfig;
import com.nuist.faxe.web.domain.LoginInfo;
import com.nuist.faxe.web.domain.User;
import com.nuist.faxe.web.service.UserService;
import com.nuist.faxe.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户Controller
 *
 * @author ZhouXiang
 **/
@RestController
@RequestMapping("/audio/user")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * 验证userName可用
     * userName不能重复
     * @param userName
     * @return boolean
     */
    @CrossOrigin
    @GetMapping("/check/{userName}")
    public ResponseMessage<Boolean> checkUserName(@PathVariable String userName){
        boolean result = service.checkUserName(userName);
        return ResponseMessage.ok(result);
    }
    /**
     * 登录
     * @param info
     * @return 登录结果
     */
    @CrossOrigin
    @PostMapping("/login")
    public ResponseMessage<UserVO> login(@RequestBody LoginInfo info){
        User result = service.checkLogin(info.getUserName(), info.getPassWord());
        if(null != result){
            return ResponseMessage.ok(entity2Model(result));
        }else {
            return ResponseMessage.error("Login Fail");
        }
    }
    /**
     * 注册
     * @param user
     * @return id
     */
    @CrossOrigin
    @PostMapping("/register")
    public ResponseMessage<String> register(@RequestBody User user){
        String id = service.register(user.getUserName(), user.getPassWord());
        return ResponseMessage.ok(id);
    }

    /**
     * 仅限管理员调用
     * 管理员userId根据id删除一条记录
     * @param userId
     * @return Boolean
     */
    @CrossOrigin
    @DeleteMapping("/{userId}/{id}")
    public ResponseMessage<Boolean> deleteUserById(@PathVariable(value = "userId") String userId,
                                                        @PathVariable(value = "id") String id){
        User user = service.getUserById(userId);
        if(user.getRole().equals(ParamConfig.ROLE_ADMIN)){
            //若是管理员
            boolean result = service.deleteUserById(id);
            return ResponseMessage.ok(result);
        }else{
            return ResponseMessage.error("You Can Not Delete It");
        }
    }
    @CrossOrigin
    @DeleteMapping("/{userId}")
    public ResponseMessage<Boolean> deleteUserByIds(@PathVariable String userId,
                                                        @RequestParam("userIds") List<String> ids){
        User user = service.getUserById(userId);
        if(user.getRole().equals(ParamConfig.ROLE_ADMIN)){
            //若是管理员
            boolean result = service.deleteUserByIds(ids);
            return ResponseMessage.ok(result);
        }else {
            return ResponseMessage.error("You Can Not Delete Them");
        }
    }

    /**
     * 根据id查询一条记录
     * @param userId
     * @return UserVO
     */
    @CrossOrigin
    @GetMapping("/{userId}")
    public ResponseMessage<UserVO> getUserById(@PathVariable String userId){
        User result = service.getUserById(userId);
        return ResponseMessage.ok(entity2Model(result));
    }

    @CrossOrigin
    @GetMapping("/list/{userId}")
    public Msg<List<User>> list(@PathVariable String userId,
                                @RequestParam("page")Integer page,
                                @RequestParam("limit")Integer limit){
        User user = service.getUserById(userId);
        if(user.getRole().equals(ParamConfig.ROLE_ADMIN)){
            //若是管理员
            long count = service.countUser();
            List<User> data = service.list(page, limit);
            return Msg.ok(data, (int)count);
        }else {
            return Msg.error("You Can Not List It");
        }
    }

    /**
     * 普通用户密码修改
     * @param userId
     * @param originalPassWord
     * @param newPassWord
     * @return
     */
    @CrossOrigin
    @PatchMapping("/{userId}")
    public ResponseMessage<Integer> update(@PathVariable String userId,
                                                @RequestParam(value = "originalPassWord") String originalPassWord,
                                                    @RequestParam(value = "newPassWord") String newPassWord){
        User user = service.getUserById(userId);
        if(user.getPassWord().equals(originalPassWord)){
            //若输入的原始密码正确，则进行密码修改
            return ResponseMessage.ok(service.updatePassWord(userId, newPassWord));
        }else{
            return ResponseMessage.error("Your password is error！");
        }
    }

    /**
     * id为userId的管理员修改用户id的密码为newPassWord
     * @param userId
     * @param id
     * @param newPassWord
     * @return
     */
    @CrossOrigin
    @PatchMapping("/{userId}/{id}")
    public ResponseMessage<Integer> updateByManager(@PathVariable(value = "userId") String userId,
                                                        @PathVariable(value = "id") String id,
                                                            @RequestParam String newPassWord){
        User user = service.getUserById(userId);
        if(user.getRole().equals(ParamConfig.ROLE_ADMIN)){
            //若当前角色为管理员
            return ResponseMessage.ok(service.updatePassWord(id, newPassWord));
        }else{
            return ResponseMessage.error("Your can not update it！");
        }
    }

    private UserVO entity2Model(User entity){
        UserVO model = new UserVO();
        model.setId(entity.getId());
        model.setUserName(entity.getUserName());
        model.setRole(entity.getRole());
        return model;
    }
}
