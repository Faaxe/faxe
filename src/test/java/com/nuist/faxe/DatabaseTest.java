package com.nuist.faxe;


import com.google.common.collect.Lists;
import com.nuist.faxe.common.utils.ParamConfig;
import com.nuist.faxe.web.domain.UploadInfo;
import com.nuist.faxe.web.domain.User;
import com.nuist.faxe.web.mapper.UploadInfoMapper;
import com.nuist.faxe.web.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ZhouXiang
 * @create 2018-04-23 21:58
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTest {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private UploadInfoMapper uploadInfoMapper;
    @Test
    public void testInsert(){
        User u = new User();
        u.setId("111");
        u.setUserName("111");
        u.setPassWord("111");
        u.setRole("111");
        mapper.insert(u);
    }

    @Test
    public void testUploadQuery(){
        List info = uploadInfoMapper.listWithUserAndAudio();
    }

    @Test
    public void list(){
        List<UploadInfo> list = Lists.newArrayList();
        uploadInfoMapper.listWithUserAndAudio().forEach(item -> {
                //将转化为VO的对象添加到list中
                list.add(item);
        });
    }
}
