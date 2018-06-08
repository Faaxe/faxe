package com.nuist.faxe.web.service.impl;

import com.nuist.faxe.common.utils.ParamConfig;
import com.nuist.faxe.common.utils.StringUtil;
import com.nuist.faxe.web.domain.User;
import com.nuist.faxe.web.domain.UserExample;
import com.nuist.faxe.web.mapper.UserMapper;
import com.nuist.faxe.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现类
 *
 * @author ZhouXiang
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    /**
     * 验证登录
     *
     * @param userName
     * @param passWord
     * @return User
     */
    public User checkLogin(String userName, String passWord){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        //like select * from where user_name={0} and pass_word={1}
        criteria.andUserNameEqualTo(userName);
        criteria.andPassWordEqualTo(passWord);
        //计数 (理论上只有0/1)
        List<User> result = mapper.selectByExample(example);
        return result.size()>0?result.get(0):null;
    }
    /**
     * 注册
     *
     * @param userName
     * @param passWord
     * @return id
     */
    public String register(String userName, String passWord){
        String id = StringUtil.randomUUID();
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setPassWord(passWord);
        //默认是User角色
        user.setRole(ParamConfig.ROLE_USER);
        //保存user
        mapper.insert(user);
        return id;
    }
    /**
     * 验证userName唯一性
     *
     * @param userName
     * @return true表示 userName可用
     */
    public boolean checkUserName(String userName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        //like select * from where user_name={0}
        criteria.andUserNameEqualTo(userName);
        //计数 (理论上只有0/1)
        long count = mapper.countByExample(example);
        return 0 == count;
    }

    /**
     * 根据id 获取userName
     *
     * @param userId
     * @return userName
     */
    public String getUserNameById(String userId) {
        User result = mapper.selectByPrimaryKey(userId);
        return result.getUserName();
    }

    /**
     * 根据id 获取user记录
     *
     * @param userId
     * @return User
     */
    public User getUserById(String userId) {
        return mapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据id 删除user记录
     *
     * @param userId
     * @return 删除结果
     */
    public boolean deleteUserById(String userId) {
        long count = mapper.deleteByPrimaryKey(userId);
        return 1 == count;
    }

    /**
     * 根据ids 删除user记录
     *
     * @param userIds
     * @return 删除结果
     */
    public boolean deleteUserByIds(List<String> userIds) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andIdIn(userIds);
        long count = mapper.deleteByExample(example);
        return count == userIds.size();
    }

    /**
     * 统计记录个数
     * @return
     */
    public long countUser() {
        UserExample example = new UserExample();
        return mapper.countByExample(example);
    }

    /**
     * 分页，返回 limit 的第 page 页
     * @param page
     * @param limit
     * @return
     */
    public List<User> list(Integer page, Integer limit) {
        int start = (page - 1) * limit;
        return mapper.selectByPageAndLimit(start, limit);
    }

    /**
     * 根据 id 修改密码
     * @param id
     * @param passWord
     * @return
     */
    public int updatePassWord(String id, String passWord) {
        User user = new User();
        user.setId(id);
        user.setPassWord(passWord);
        return mapper.updateByPrimaryKeySelective(user);
    }

}
